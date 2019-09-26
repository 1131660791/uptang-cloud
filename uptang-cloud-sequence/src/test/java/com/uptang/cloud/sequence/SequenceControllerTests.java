package com.uptang.cloud.sequence;

import com.google.common.base.Splitter;
import com.uptang.cloud.starter.common.util.NumberUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.stream.LongStream;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-26
 */
public class SequenceControllerTests extends BaseJunitTest {
    /**
     * 按逗号把字符串拆分成列
     */
    private static final Splitter SPLITTER = Splitter.on(',').trimResults();

    private static final String BASE_API_URL = "/v1/inner/sequence";

    @Test
    public void testGetSequence() throws Exception {
        MockHttpServletResponse response = httpGetRequest(BASE_API_URL, "");
        Assert.assertNotNull("生成的唯一序号", response.getContentAsString());
    }


    @Test
    public void testGetSequences() throws Exception {
        int count = 10;
        MockHttpServletResponse response = httpGetRequest(BASE_API_URL + "/" + count, "");
        String responseContent = response.getContentAsString();
        Assert.assertNotNull("生成的唯一序号", responseContent);

        // 解析生成出来的序号
        responseContent = responseContent.replace("[", "").replace("]", "");
        long[] ids = SPLITTER.splitToList(responseContent).stream()
                .mapToLong(id -> NumberUtils.toLong(id, 0)).toArray();

        Assert.assertEquals("生成的序列号数量", count, ids.length);
        Assert.assertArrayEquals("生成的序列号", ids,
                LongStream.iterate(ids[0], i -> i + 1).limit(count).toArray());
    }
}
