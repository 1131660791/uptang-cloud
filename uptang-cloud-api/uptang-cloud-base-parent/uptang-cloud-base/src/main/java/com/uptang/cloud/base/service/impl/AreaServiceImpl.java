package com.uptang.cloud.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.uptang.cloud.base.common.model.Area;
import com.uptang.cloud.base.repository.AreaRepository;
import com.uptang.cloud.base.service.AreaService;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.core.exception.DataNotFoundException;
import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-15
 */
@Slf4j
@Service
public class AreaServiceImpl extends ServiceImpl<AreaRepository, Area> implements AreaService {
    /**
     * 缓存所有省份数据
     */
    private List<Area> provinces = new ArrayList<>();

    /**
     * 根据城市ID缓存
     */
    private Map<Integer, Area> cacheByIdMap = new HashMap<>();

    /**
     * 根据上级城市ID缓存
     */
    private Map<Integer, List<Area>> cacheByParentMap = new HashMap<>();

    /**
     * 根据城市代码缓存
     */
    private Map<String, List<Area>> cacheByCodeMap = new HashMap<>();

    @Override
    public Area load(int id) throws DataNotFoundException {
        return Optional.ofNullable(loadSafely(id))
                .orElseThrow(() -> new DataNotFoundException(ResponseCodeEnum.DATA_NOT_FOUND.getCode(), "城市ID不能为空"));
    }

    @Override
    public Area loadSafely(int id) {
        return cacheByIdMap.get(id);
    }

    @Override
    public List<Area> findProvinces() {
        return CollectionUtils.isEmpty(provinces) ? ImmutableList.of() : ImmutableList.copyOf(provinces);
    }

    @Override
    public Map<Integer, Area> findByIds(Integer... ids) {
        return ImmutableMap.copyOf(Arrays.stream(ids).map(this::loadSafely)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Area::getId, area -> area)));
    }

    @Override
    public List<Area> findByParent(Integer parentId, Integer level) {
        if (NumberUtils.isNotPositive(parentId)) {
            throw new BusinessException("传入的父区域ID不正确！");
        }

        List<Area> areas = cacheByParentMap.get(parentId);
        if (CollectionUtils.isEmpty(areas)) {
            return new ArrayList<>(0);
        }

        areas = new ArrayList<>(areas);
        areas.add(loadSafely(parentId));
        return filterForLevelAndSort(areas, level);
    }

    @Override
    public List<Area> findByCityCode(String cityCode, Integer level) {
        if (StringUtils.isBlank(cityCode)) {
            throw new BusinessException("传入的城市代码不正确！");
        }
        return filterForLevelAndSort(cacheByCodeMap.get(cityCode.trim()), level);
    }

    @Override
    public List<Area> findChain(Integer areaId) {
        if (NumberUtils.isNotPositive(areaId)) {
            return ImmutableList.of();
        }

        Area area;
        Integer parentId = areaId;
        int level = Integer.MAX_VALUE;
        List<Area> innerAreas = new ArrayList<>();
        while (level > 1) {
            area = loadSafely(parentId);
            if (null != area && NumberUtils.isPositive(area.getParentId())) {
                parentId = area.getParentId();
                level = area.getLevel();
                innerAreas.add(area);
            } else {
                level = 0;
            }
        }

        if (innerAreas.size() > 1) {
            innerAreas.sort(Comparator.comparingInt(Area::getLevel));
        }
        return ImmutableList.copyOf(innerAreas);
    }


    /** ========================================== private method =============================== */

    /**
     * 按城市所在级别进行过滤
     *
     * @param areas 城市列表
     * @param level 城市级别
     * @return 过滤和排序后的城市列表
     */
    private List<Area> filterForLevelAndSort(List<Area> areas, Integer level) {
        if (CollectionUtils.isEmpty(areas)) {
            return new ArrayList<>(0);
        }

        List<Area> innerAreas;

        // 根据城市级别进行过滤
        if (NumberUtils.isPositive(level)) {
            innerAreas = areas.stream().filter(area -> level.equals(area.getLevel())).collect(Collectors.toList());
        } else {
            innerAreas = new ArrayList<>(areas);
        }

        // 按序号排序
        if (CollectionUtils.isNotEmpty(innerAreas)) {
            innerAreas.sort(Comparator.comparingInt(Area::getId));
        }
        return ImmutableList.copyOf(innerAreas);
    }

    @PostConstruct
    private void initialize() {
        long startTime = System.currentTimeMillis();

        // 指定查询字段
        LambdaQueryWrapper<Area> queryWrapper = new LambdaQueryWrapper<Area>()
                .select(Area::getId, Area::getParentId, Area::getName, Area::getMergedName,
                        Area::getInitial, Area::getLevel, Area::getCityCode, Area::getZipCode);

        Optional.ofNullable(getBaseMapper().selectList(queryWrapper)).ifPresent(areas -> {
            cacheByIdMap = areas.stream().collect(Collectors.toMap(Area::getId, area -> area));
            provinces = ImmutableList.copyOf(areas.stream()
                    .filter(area -> null != area.getLevel() && 1 == area.getLevel())
                    .sorted(Comparator.comparing(Area::getId))
                    .collect(Collectors.toList()));

            cacheByParentMap = areas.stream()
                    .filter(area -> NumberUtils.isPositive(area.getParentId()))
                    .collect(Collectors.groupingBy(Area::getParentId));

            cacheByCodeMap = areas.stream()
                    .filter(area -> StringUtils.isNotBlank(area.getCityCode()))
                    .collect(Collectors.groupingBy(Area::getCityCode));
        });
        log.info("获取{}条城市数据，耗时{}毫秒", cacheByIdMap.size(), System.currentTimeMillis() - startTime);
    }
}