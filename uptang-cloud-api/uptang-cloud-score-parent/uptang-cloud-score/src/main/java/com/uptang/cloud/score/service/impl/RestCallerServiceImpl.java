package com.uptang.cloud.score.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.score.common.Api;
import com.uptang.cloud.score.common.enums.PublicityTypeEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.*;
import com.uptang.cloud.score.exceptions.HttpClientException;
import com.uptang.cloud.score.httpclient.HttpRequest;
import com.uptang.cloud.score.httpclient.HttpResponseEvent;
import com.uptang.cloud.score.service.IRestCallerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 11:37
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Slf4j
@Service
public class RestCallerServiceImpl implements IRestCallerService {


    //    static final String DEV = "http://192.168.127:8082";
//    @Value("${spring.uptang.gateway.host:" + DEV + "}")
    private String serverHost = "http://192.168.0.127:8081";

    @Autowired
    private ObjectMapper mapper;

    @Override
    public boolean moduleSwitch(ModuleSwitchDto moduleSwitchDto) {
        String api = Api.getApi("http://192.168.0.127:8083", Api.Manager.MODULE_SWITCH);
        ModuleSwitchResponseDto moduleSwitch = postJson(api, moduleSwitchDto, ModuleSwitchResponseDto.class);
        if (moduleSwitch == null) {
            throw new BusinessException("未设置任务");
        }

        Instant instant = moduleSwitch.getEnd().toInstant();
        return instant.compareTo(Instant.now()) > 0 ? true : false;
    }

    @Override
    public StuListDTO studentList(StudentRequestDTO studentRequest) {
        String api = Api.getApi(serverHost, Api.UserCenter.STUDENT_INFO);
        String payload = postJson(api, studentRequest, String.class);
        try {
            return mapper.readValue(payload, StuListDTO.class);
        } catch (IOException e) {
            return JSON.parseObject(payload, StuListDTO.class);
        }
    }

    @Override
    public boolean promissionCheck(RestRequestDto restRequestDto) {
        Assert.notNull(restRequestDto, "请求参数不能为空");
        String api = Api.getApi(serverHost, Api.Promission.CHECK);
        PromissionDTO promission = postJson(api, restRequestDto, PromissionDTO.class);
        if (promission != null) {
            switch (promission.getUserType()) {
                case TEACHER:
                case MANAGER:
                    return true;
                case STUDENT:
                case PARENTS:
                default:
                    return false;
            }
        }
        return false;
    }

    @Override
    public List<GradeCourseDTO> gradeInfo(StudentRequestDTO studentRequest) {
        String api = Api.getApi(serverHost, Api.Grade.INFO, studentRequest.getGradeId());
        Header[] headers = new Header[]{new BasicHeader("Token", studentRequest.getToken())};

        try {
            String payload = HttpRequest.HttpClient.get(api, headers);
            if (payload == null || "".equals(payload)) {
                return Collections.emptyList();
            }

            Map map = mapper.readValue(payload, Map.class);
            Object status = map.get("status");
            Object data = map.get("data");
            if (map == null || map.size() == 0
                    || status.equals(HttpStatus.SC_OK)
                    || data == null) {
                return Collections.emptyList();
            }

            String json = mapper.writeValueAsString(data);
            if (JSON.isValidArray(json)) {
                return mapper.readValue(json, new TypeReference<List<GradeCourseDTO>>() {
                });
            }

            if (log.isDebugEnabled()) {
                log.debug("api ==> {} response body ==> {}", api, json);
            }

            return Collections.emptyList();
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public List<AcademicResume> exemption(ExemptionDto exemptionDto) {
        String api = Api.getApi(serverHost, Api.UserCenter.EXEMPTION);
        List list = postJson(api, exemptionDto, List.class);

        try {
            String writeValueAsString = mapper.writeValueAsString(list);
            return mapper.readValue(writeValueAsString, new TypeReference<List<AcademicResume>>() {});
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public PublicityDTO publicity(String token, PublicityTypeEnum type) {
        Header[] headers = new Header[]{new BasicHeader("Token", token)};
        String api = Api.getApi(serverHost, Api.Manager.PUBLICITY, type.getCode() + "");
        try {
            HttpResponseEvent response = HttpRequest.HttpClient.post(api, headers);

            if (log.isDebugEnabled()) {
                log.debug("request ==> {} response ==> {}", api, response);
            }

            if (response.getCode() == HttpStatus.SC_OK) {
                return mapper.readValue(response.getPayload(), PublicityDTO.class);
            }

            //StringUtils.isBlank(response.getMessage()) ? response.getPayload() : response.getMessage()
            return null;
        } catch (IOException e) {
            log.error("request ==> {} error ==> {}", api, e.getMessage());
            throw new HttpClientException(e);
        }
    }

    private <T> T postJson(String api, RestRequestDto restRequestDto, Class<T> clazz) {
        try {
            String json = mapper.writeValueAsString(restRequestDto);
            Header[] headers = new Header[]{new BasicHeader("Token", restRequestDto.getToken())};
            HttpResponseEvent response = HttpRequest.HttpClient.postJson(api, json, headers);

            if (log.isDebugEnabled()) {
                log.debug("request ==> {} body ==> {} response ==> {}", api, json, response);
            }

            if (response.getCode() == HttpStatus.SC_OK) {
                Map<String, Object> payload =
                        mapper.readValue(response.getPayload(), Map.class);
                Object status = payload.get("status");

                if (status != null && status.equals(HttpStatus.SC_OK + "")) {
                    String data = mapper.writeValueAsString(payload.get("data"));
                    if (clazz.isAssignableFrom(String.class)) {
                        return (T) data;
                    }

                    return mapper.readValue(data, clazz);
                }
            }

            String message = StringUtils.isBlank(response.getMessage()) ? response.getPayload() : response.getMessage();
            throw new HttpClientException(message);
        } catch (IOException e) {
            log.error("request ==> {} error ==> {}", api, e.getMessage());
            throw new HttpClientException(e);
        }
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
