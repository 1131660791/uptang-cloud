package com.uptang.cloud.sequence;

import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

/**
 * 基础测试类
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-26
 */
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SequenceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseJunitTest {
    @Autowired
    protected MockMvc mockMvc;

    /**
     * 构建测试请求
     *
     * @param apiUrl   请求接口地址
     * @param params   请求的参数
     * @param matchers 验证规则
     * @return MockHttpServletResponse
     * @throws Exception 请求接口时抛出的异常
     */
    MockHttpServletResponse httpRequestResponse(
            String apiUrl, Map<String, Object> params, ResultMatcher... matchers) throws Exception {

        // 构建返回信息
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(generateUrl(apiUrl, params)))
                .andDo(MockMvcResultHandlers.print());

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
}
