package com.uptang.cloud.score.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uptang.cloud.score.common.API;
import com.uptang.cloud.score.common.enums.PublicityTypeEnum;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    static final String DEV = "http://192.168.127:8082";
    @Value("${spring.uptang.gateway.host:" + DEV + "}")
    private String serverHost;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public ModuleSwitchResponseDto moduleSwitch(ModuleSwitchDto moduleSwitchDto) {
        String api = API.getApi(serverHost, API.Manager.MODULE_SWITCH);
        return postJson(api, moduleSwitchDto, ModuleSwitchResponseDto.class);
    }

    @Override
    public List<StudentDTO> studentList(StudentRequestDTO studentRequest) {
        String api = API.getApi(serverHost, API.UserCenter.STUDENT_INFO);
        String payload = postJson(api, studentRequest, String.class);
        try {
            return mapper.readValue(payload, new TypeReference<List<StudentDTO>>() {});
        } catch (IOException e) {
            return JSON.parseArray(payload, StudentDTO.class);
        }
    }

    @Override
    public PromissionDTO promissionCheck(RestRequestDto restRequestDto) {
        String api = API.getApi(serverHost, API.Promission.CHECK);
        return postJson(api, restRequestDto, PromissionDTO.class);
    }

    @Override
    public List<GradeCourseDTO> gradeInfo(StudentRequestDTO studentRequest) {
        String api = API.getApi(serverHost, API.Grade.INFO);
        String payload = postJson(api, studentRequest, String.class);
        try {
            return mapper.readValue(payload, new TypeReference<List<GradeCourseDTO>>() {});
        } catch (IOException e) {
            return JSON.parseArray(payload, GradeCourseDTO.class);
        }
    }

    @Override
    public List<String> exemption(ExemptionDto exemptionDto) {
        String api = API.getApi(serverHost, API.UserCenter.EXEMPTION);
        return postJson(api, exemptionDto, List.class);
    }

    @Override
    public PublicityDTO publicity(String token, PublicityTypeEnum type) {
        Header[] headers = new Header[]{new BasicHeader("Token", token)};
        String api = API.getApi(serverHost, API.Manager.PUBLICITY, type.getCode() + "");
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
                log.debug("request ==> {} response ==> {}", api, response);
            }

            if (response.getCode() == HttpStatus.SC_OK) {
                Map<String, Object> payload = mapper.readValue(response.getPayload(), Map.class);
                Object status = payload.get("status");

                if (status != null && status.equals(HttpStatus.SC_OK)) {
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
}
