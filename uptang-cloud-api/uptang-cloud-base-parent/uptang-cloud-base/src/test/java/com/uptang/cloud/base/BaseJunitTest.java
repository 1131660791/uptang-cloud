package com.uptang.cloud.base;

import com.alibaba.fastjson.JSON;
import com.uptang.cloud.pojo.enums.UserTypeEnum;
import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import com.uptang.cloud.starter.web.Constants;
import com.uptang.cloud.starter.web.domain.BaseVO;
import com.uptang.cloud.starter.web.util.UserTokenUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * 基础测试类
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@Rollback
@Transactional
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BaseApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseJunitTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserTokenUtils userTokenUtils;

    /**
     * 测试 GET 请求
     *
     * @param apiUrl   接口地址
     * @param token    登录令牌
     * @param matchers 需要验证匹配的规则
     * @return MockHttpServletResponse
     * @throws Exception Exception
     */
    protected MockHttpServletResponse httpGetRequest(String apiUrl, String token, ResultMatcher... matchers) throws Exception {
        return httpRequestResponse(HttpMethod.GET, apiUrl, token, null, null, matchers);
    }

    /**
     * 测试 GET 请求
     *
     * @param apiUrl   接口地址
     * @param token    登录令牌
     * @param params   请求参数
     * @param matchers 需要验证匹配的规则
     * @return MockHttpServletResponse
     * @throws Exception Exception
     */
    protected MockHttpServletResponse httpGetRequest(String apiUrl, String token, Map<String, Object> params, ResultMatcher... matchers) throws Exception {
        return httpRequestResponse(HttpMethod.GET, apiUrl, token, params, null, matchers);
    }

    /**
     * 测试 POST 请求
     *
     * @param apiUrl   接口地址
     * @param token    登录令牌
     * @param vo       请求对象
     * @param matchers 需要验证匹配的规则
     * @param <VO>     泛化的参数
     * @return MockHttpServletResponse
     * @throws Exception Exception
     */
    protected <VO extends BaseVO> MockHttpServletResponse httpPostRequest(String apiUrl, String token, VO vo, ResultMatcher... matchers) throws Exception {
        return httpRequestResponse(HttpMethod.POST, apiUrl, token, null, vo, matchers);
    }

    /**
     * 测试 PUT 请求
     *
     * @param apiUrl   接口地址
     * @param token    登录令牌
     * @param vo       请求对象
     * @param matchers 需要验证匹配的规则
     * @param <VO>     泛化的参数
     * @return MockHttpServletResponse
     * @throws Exception Exception
     */
    protected <VO extends BaseVO> MockHttpServletResponse httpPutRequest(String apiUrl, String token, VO vo, ResultMatcher... matchers) throws Exception {
        return httpRequestResponse(HttpMethod.PUT, apiUrl, token, null, vo, matchers);
    }


    /**
     * 测试 DELETE 请求
     *
     * @param apiUrl   接口地址
     * @param token    登录令牌
     * @param matchers 需要验证匹配的规则
     * @return MockHttpServletResponse
     * @throws Exception Exception
     */
    protected MockHttpServletResponse httpDelRequest(String apiUrl, String token, ResultMatcher... matchers) throws Exception {
        return httpRequestResponse(HttpMethod.DELETE, apiUrl, token, null, null, matchers);
    }

    /**
     * 测试 DELETE 请求
     *
     * @param apiUrl   接口地址
     * @param token    登录令牌
     * @param params   请求参数
     * @param matchers 需要验证匹配的规则
     * @return MockHttpServletResponse
     * @throws Exception Exception
     */
    protected MockHttpServletResponse httpDelRequest(String apiUrl, String token, Map<String, Object> params, ResultMatcher... matchers) throws Exception {
        return httpRequestResponse(HttpMethod.DELETE, apiUrl, token, params, null, matchers);
    }

    /**
     * 构建测试请求
     *
     * @param method   请求的方法
     * @param apiUrl   请求接口地址
     * @param token    请求的 Token
     * @param params   请求的参数
     * @param vo       请求的内容，当请求参数为 POST || PUT 时有效
     * @param matchers 验证规则
     * @param <VO>     泛化的参数
     * @return MockHttpServletResponse
     * @throws Exception 请求接口时抛出的异常
     */
    private <VO extends BaseVO> MockHttpServletResponse httpRequestResponse(
            HttpMethod method, String apiUrl, String token, Map<String, Object> params,
            VO vo, ResultMatcher... matchers) throws Exception {

        // 验证请求方式
        method = Optional.ofNullable(method).orElse(HttpMethod.GET);
        MockHttpServletRequestBuilder requestBuilder;
        if (HttpMethod.GET.equals(method)) {
            requestBuilder = MockMvcRequestBuilders.get(generateUrl(apiUrl, params));
        } else if (HttpMethod.POST.equals(method)) {
            requestBuilder = MockMvcRequestBuilders.post(generateUrl(apiUrl, params));
        } else if (HttpMethod.PUT.equals(method)) {
            requestBuilder = MockMvcRequestBuilders.put(generateUrl(apiUrl, params));
        } else if (HttpMethod.DELETE.equals(method)) {
            requestBuilder = MockMvcRequestBuilders.delete(generateUrl(apiUrl, params));
        } else {
            requestBuilder = MockMvcRequestBuilders.get(generateUrl(apiUrl, params));
        }

        // 加入头信息
        requestBuilder.contentType(MediaType.APPLICATION_JSON).header(Constants.TOKEN_PARA_NAME, generateToken(token));

        // 如果传了 VO
        if (null != vo && (HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method))) {
            requestBuilder.content(JSON.toJSONString(vo));
        }

        // 构建返回信息
        ResultActions actions = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print());

        // 增加规则验证，默认实现了 http code = 200 验证
        if (ArrayUtils.isNotEmpty(matchers)) {
            for (ResultMatcher matcher : matchers) {
                actions.andExpect(matcher);
            }
        }

        if (StringUtils.containsIgnoreCase(apiUrl, "inner")) {
            return actions
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse();
        }

        return actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(ResponseCodeEnum.SUCCESS.getCode())))
                .andReturn().getResponse();
    }

    private String generateUrl(String apiUrl, Map<String, Object> params) {
        // 如果有参数
        if (MapUtils.isNotEmpty(params)) {
            StringBuilder uri = new StringBuilder();
            uri.append(apiUrl).append("?");
            params.forEach((k, v) -> uri.append(k).append("=").append(v).append("&"));
            uri.deleteCharAt(uri.length() - 1);
            apiUrl = uri.toString();
        }

        return apiUrl;
    }


    private String generateToken(String token) {
        return StringUtils.isBlank(token)
                ? userTokenUtils.generateToken(1L, UserTypeEnum.STUDENT, "192.168.0.210")
                : StringUtils.trimToEmpty(token);
    }
}
