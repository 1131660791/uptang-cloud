package com.uptang.cloud.behavior.controller;

import com.uptang.cloud.behavior.BaseJunitTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-26
 */
public class UserControllerTests extends BaseJunitTest {
    private static final String BASE_API_URL = "/v1/users";

    @Test
    public void testGetUsers() throws Exception {
        MockHttpServletResponse response = httpGetRequest(BASE_API_URL, "", new HashMap<String, Object>() {{
                    put("pageIndex", "1");
                    put("pageSize", "10");
                    put("q", "雅安");
                }},
                MockMvcResultMatchers.jsonPath("$.pagination.pageSize", Matchers.is(10)),
                MockMvcResultMatchers.jsonPath("$.data[0].userName", Matchers.is("雅安市"))
        );
        Assert.assertNotNull("查询到的用户", response.getContentAsString());
    }
}
