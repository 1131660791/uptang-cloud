package com.uptang.cloud.base.feign;

import com.uptang.cloud.base.common.model.Area;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 城市地区内部接口
 * <pre>
 *   使用方式
 *   1. 类似于 Repository 或 Service 的方式注入
 *   2. 直接通过注入的对象进行方法调用
 * </pre>
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-15
 */
@FeignClient(name = Constants.SERVICE_NAME, path = Constants.CONTEXT_PATH + "/inner/areas")
public interface AreaProvider {
    /**
     * 获取所有省份列表
     *
     * @return 省份列表
     */
    @GetMapping(path = "/provinces", produces = Constants.PRODUCE_MEDIA_TYPE, consumes = Constants.CONSUME_MEDIA_TYPE)
    List<Area> getProvinces();

    /**
     * 根据父城市ID,查询下级城市列表
     *
     * @param parentId 父城市ID
     * @return 查询到的下级城市列表
     */
    @GetMapping(path = "/parent/{parentId}", produces = Constants.PRODUCE_MEDIA_TYPE, consumes = Constants.CONSUME_MEDIA_TYPE)
    List<Area> getAreasByParent(@PathVariable("parentId") Integer parentId);

    /**
     * 根据城市ID获取城市详情
     *
     * @param areaId 城市ID
     * @return 查询到的城市详情
     */
    @GetMapping(path = "/{areaId}", produces = Constants.PRODUCE_MEDIA_TYPE, consumes = Constants.CONSUME_MEDIA_TYPE)
    Area load(@PathVariable("areaId") Integer areaId);

    /**
     * 查询指定城市的上级
     *
     * @param areaId 城市ID
     * @return 查询到的城市数据
     */
    @GetMapping(path = "/{areaId}/chain", produces = Constants.PRODUCE_MEDIA_TYPE, consumes = Constants.CONSUME_MEDIA_TYPE)
    List<Area> getChainAreas(@PathVariable("areaId") Integer areaId);

    /**
     * 根据传入的城市ID获取详情
     *
     * @param ids 城市IDs
     * @return 查询到的城市详情
     */
    @GetMapping(produces = Constants.PRODUCE_MEDIA_TYPE, consumes = Constants.CONSUME_MEDIA_TYPE)
    Map<Integer, Area> findByIds(@RequestParam("ids") Integer[] ids);
}
