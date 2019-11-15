package com.uptang.cloud.base.controller;

import com.uptang.cloud.base.common.model.Area;
import com.uptang.cloud.base.feign.AreaProvider;
import com.uptang.cloud.base.service.AreaService;
import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.starter.web.annotation.JsonResult;
import com.uptang.cloud.starter.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chuan.jiang <jiang.chuan@yanwei365.com>
 * @version 2.0.0
 * @date 2018-11-01
 */
@Slf4j
@ApiIgnore
@RestController
@RequestMapping("/inner/areas")
public class AreaInnerController extends BaseController implements AreaProvider {
    private static final String EXCLUDE_FIELDS = "createdTime, modifiedTime";
    private final AreaService areaService;


    @Autowired
    public AreaInnerController(AreaService areaService) {
        this.areaService = areaService;
    }

    @Override
    @JsonResult(type = Area.class, exclude = EXCLUDE_FIELDS)
    public List<Area> getProvinces() {
        return new ArrayList<>(areaService.findProvinces());
    }

    @Override
    @JsonResult(type = Area.class, exclude = EXCLUDE_FIELDS)
    public List<Area> getAreasByParent(Integer parentId) {
        if (NumberUtils.isNotPositive(parentId)) {
            return new ArrayList<>(0);
        }
        return new ArrayList<>(areaService.findByParent(parentId, null));
    }

    @Override
    @JsonResult(type = Area.class, exclude = EXCLUDE_FIELDS)
    public Area load(Integer areaId) {
        return areaService.loadSafely(areaId);
    }

    @Override
    @JsonResult(type = Area.class, exclude = EXCLUDE_FIELDS)
    public List<Area> getChainAreas(Integer areaId) {
        return new ArrayList<>(areaService.findChain(areaId));
    }

    @Override
    @JsonResult(type = Area.class, exclude = EXCLUDE_FIELDS)
    public Map<Integer, Area> findByIds(Integer[] ids) {
        try {
            if (ArrayUtils.isEmpty(ids)) {
                return new HashMap<>(0);
            }

            // 检查ID正确性，将去重
            Integer[] areaIds = Arrays.stream(ids).filter(NumberUtils::isPositive).distinct().toArray(Integer[]::new);
            if (ArrayUtils.isEmpty(areaIds)) {
                return new HashMap<>(0);
            }
            return new HashMap<>(areaService.findByIds(areaIds));
        } catch (Exception ex) {
            log.error("批量查询区域", ex);
            return null;
        }
    }
}
