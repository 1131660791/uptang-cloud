package com.uptang.cloud.score.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.pojo.domain.context.UserContextThreadLocal;
import com.uptang.cloud.pojo.enums.UserTypeEnum;
import com.uptang.cloud.score.common.Api;
import com.uptang.cloud.score.common.enums.PublicityTypeEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.*;
import com.uptang.cloud.score.handler.PrimitiveResolver;
import com.uptang.cloud.score.service.IRestCallerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 11:37
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Slf4j
@Service
public class RestCallerServiceImpl implements IRestCallerService {

    /**
     * FIXME 记得改
     */
    @Value("${gate.pj.host:http://192.168.0.127:8081}")
    private String serverHost;

    @Autowired
    private ObjectMapper mapper;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * FIXME 记得修改
     * Local: "http://192.168.0.127:8083"
     *
     * @param moduleSwitchDto 请求参数
     * @return
     */
    @Override
    public boolean moduleSwitch(ModuleSwitchDTO moduleSwitchDto) {
        String api = Api.getApi(serverHost, Api.Manager.MODULE_SWITCH);
        ModuleSwitchResponseDTO moduleSwitch =
                postJson(api, moduleSwitchDto, ModuleSwitchResponseDTO.class);
        if (moduleSwitch == null) {
            throw new BusinessException("未设置任务");
        }

        // 用当前时间比较任务结束时间
        Instant endTime = moduleSwitch.getEnd().toInstant();
        return endTime.compareTo(Instant.now()) > 0 ? true : false;
    }

    @Override
    public StuListDTO studentList(StudentRequestDTO studentRequest) {
        String api = Api.getApi(serverHost, Api.UserCenter.STUDENT_INFO);
        String payload = postJson(api, studentRequest, String.class);
        try {
            payload = payload == null ? "" : payload;
            return mapper.readValue(payload, StuListDTO.class);
        } catch (IOException e) {
            log.error("获取用户信息异常 url ==> {} ,Ex. ==> {}", api, e.getMessage());
            return JSON.parseObject(payload, StuListDTO.class);
        }
    }


    @Override
    public boolean permissionCheck() {
        Integer userType = UserContextThreadLocal.get().getUserType();
        userType = userType == null ? 0 : userType;
        UserTypeEnum type = UserTypeEnum.parse(userType);
        if (type != null) {
            switch (type) {
                case TEACHER:
                case MANAGER:
                    return true;
                case STUDENT:
                case PARENT:
                default:
                    return false;
            }
        }

        if (log.isWarnEnabled()) {
            log.warn("用户鉴权失败 userType ==> {}", userType);
        }

        return false;
    }

    @Override
    public List<GradeCourseDTO> gradeInfo(StudentRequestDTO studentRequest) {
        String api = Api.getApi(serverHost, Api.Grade.INFO, studentRequest.getGradeId());
        try {
            // JSONObject ext Map
            ResponseEntity<JSONObject> exchange =
                    restTemplate.exchange(api, GET, buildEntity(studentRequest), JSONObject.class);
            if (exchange.getStatusCode().compareTo(OK) == 0) {
                JSONObject body = exchange.getBody();
                if (body != null && body.size() > 0) {

                    Object status = body.getOrDefault("status", SC_INTERNAL_SERVER_ERROR);
                    status = PrimitiveResolver.String_.convert(status);
                    if (status.equals(String.valueOf(HttpStatus.SC_OK))) {

                        Object data = body.getOrDefault("data", Strings.EMPTY);
                        String json = mapper.writeValueAsString(data);
                        if (JSON.isValidArray(json)) {
                            return mapper.readValue(json, new TypeReference<List<GradeCourseDTO>>() {
                            });
                        }
                    }
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("api ==> {} response body ==> {}", api, studentRequest.toString());
            }
            return Collections.emptyList();
        } catch (IOException e) {
            log.error("获取年级信息异常 url ==> {} Ex. ==> {}", api, e.getMessage());
            throw new BusinessException(e);
        }
    }


    @Override
    public List<AcademicResume> exemption(ExemptionDTO exemptionDto) {
        String api = Api.getApi(serverHost, Api.UserCenter.EXEMPTION);
        List list = postJson(api, exemptionDto, List.class);

        try {
            String writeValueAsString = mapper.writeValueAsString(list);
            return mapper.readValue(writeValueAsString, new TypeReference<List<AcademicResume>>() {
            });
        } catch (IOException e) {
            log.error("获取免测学生列表异常 url ==> {} Ex. ==> {}", api, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public PublicityDTO publicity(String token, PublicityTypeEnum type) {
        String api = Api.getApi(serverHost, Api.Manager.PUBLICITY, type.getCode());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Token", token);
        httpHeaders.set("token", token);

        ResponseEntity<JSONObject> response =
                restTemplate.exchange(api, GET, new HttpEntity<>(httpHeaders), JSONObject.class);
        if (log.isDebugEnabled()) {
            log.debug("request ==> {} response ==> {}", api, response);
        }

        try {
            if (response.getStatusCode().compareTo(OK) == 0) {
                JSONObject body = response.getBody();
                if (body != null && body.size() > 0) {

                    Object status = body.getOrDefault("status", SC_INTERNAL_SERVER_ERROR);
                    status = PrimitiveResolver.String_.convert(status);
                    if (status.equals(String.valueOf(HttpStatus.SC_OK))) {
                        Object data = body.getOrDefault("data", Strings.EMPTY);
                        return mapper.readValue(mapper.writeValueAsString(data), PublicityDTO.class);
                    }
                }
            }
            return null;
        } catch (IOException e) {
            log.error("request ==> {} error ==> {}", api, e.getMessage());
            throw new RestClientException(e.getMessage());
        }
    }


    /**
     * Post
     *
     * @param api         请求地址
     * @param restRequest 请求参数
     * @param clazz       返回结果接收模型Class
     * @param <T>         返回结果接收模型
     * @return 返回结果
     */
    private <T> T postJson(String api, RestRequestDTO restRequest, Class<T> clazz) {
        ResponseEntity<JSONObject> response =
                restTemplate.postForEntity(api, buildEntity(restRequest), JSONObject.class);

        if (log.isDebugEnabled()) {
            log.debug("request ==> {} body ==> {} response ==> {}", api, restRequest, response);
        }

        try {
            if (response.getStatusCode().compareTo(OK) == 0) {
                JSONObject body = response.getBody();
                if (body != null && body.size() > 0) {

                    Object status = body.getOrDefault("status", SC_INTERNAL_SERVER_ERROR);
                    status = PrimitiveResolver.String_.convert(status);
                    if (status.equals(String.valueOf(HttpStatus.SC_OK))) {

                        // FIXME  可以将try catch 范围缩小到这里
                        Object data = body.getOrDefault("data", Strings.EMPTY);
                        String json = mapper.writeValueAsString(data);
                        if (clazz.isAssignableFrom(String.class)) {
                            return (T) json;
                        }

                        return mapper.readValue(json, clazz);
                    }
                }
            }
            return null;
        } catch (IOException e) {
            log.error("request ==> {} error ==> {}", api, e.getMessage());
            throw new RestClientException(e.getMessage());
        }
    }


    /**
     * 构建HTTP请求参数及Header
     *
     * @param studentRequest
     * @return
     */
    private HttpEntity<RestRequestDTO> buildEntity(RestRequestDTO studentRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Token", studentRequest.getToken());
        httpHeaders.set("token", studentRequest.getToken());
        return new HttpEntity<>(studentRequest, httpHeaders);
    }
}
