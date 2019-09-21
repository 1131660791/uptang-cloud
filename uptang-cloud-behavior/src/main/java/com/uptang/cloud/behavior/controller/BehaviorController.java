package com.uptang.cloud.behavior.controller;

import com.uptang.cloud.provider.sequence.SequenceProvider;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
@RestController
@RequestMapping("/v1/behaviors")
@Api(value = "BehaviorController", tags = {"收集用户行为"})
public class BehaviorController extends BaseController {
    private final SequenceProvider sequenceProvider;

    @Autowired
    public BehaviorController(SequenceProvider sequenceProvider) {
        this.sequenceProvider = sequenceProvider;
    }

    @GetMapping(path = "/sequence", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiOut<List<String>> generateSequences(@RequestParam(name = "count", required = false, defaultValue = "10") Integer count) {
        long[] ids = sequenceProvider.getSequences(count);
        return ApiOut.newSuccessResponse(Arrays.stream(ids).mapToObj(String::valueOf).collect(Collectors.toList()));
    }
}
