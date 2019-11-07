package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.BaseJunitTest;
import com.uptang.cloud.score.common.vo.AcademicResumeVO;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-07 8:16
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class AcademicResumeControllerTest extends BaseJunitTest {

    static final String Prefix = "/v1/resume";

    private String token;

    @Before
    public void before() {
        this.token = "";
    }

    @Test
    public void detail() throws Exception {
        httpGetRequest(Prefix + "/1", token);
    }

    @Test
    public void update() throws Exception {
        AcademicResumeVO resumeVO = new AcademicResumeVO();
        resumeVO.setClassName("Junit update");
        httpPutRequest(Prefix + "/1", token, resumeVO);
    }
}