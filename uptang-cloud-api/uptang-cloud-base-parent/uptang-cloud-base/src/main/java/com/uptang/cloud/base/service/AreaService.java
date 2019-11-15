package com.uptang.cloud.base.service;

import com.uptang.cloud.base.common.model.Area;
import com.uptang.cloud.core.exception.DataNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-15
 */
public interface AreaService {
    /**
     * 查询区域详情
     *
     * @param id PK
     * @return 区域详情
     * @throws DataNotFoundException 如果没有找到数据，将会抛出异常
     */
    Area load(int id) throws DataNotFoundException;

    /**
     * 查询区域详情
     *
     * @param id PK
     * @return 区域详情
     */
    Area loadSafely(int id);


    /**
     * 查询所有省，直辖市数据
     *
     * @return 查询所有省份数据
     */
    List<Area> findProvinces();

    /**
     * 根据区域IDs查询数据
     *
     * @param ids 区域IDs
     * @return 满足条件的区域数据
     */
    Map<Integer, Area> findByIds(Integer... ids);

    /**
     * 查询下级区域
     *
     * @param parentId 上级ID
     * @param level    返回第几级数据
     * @return 满足条件的区域数据
     */
    List<Area> findByParent(Integer parentId, Integer level);

    /**
     * 根据区号查询数据
     *
     * @param cityCode 区号
     * @param level    返回第几级数据
     * @return 满足条件的区域数据
     */
    List<Area> findByCityCode(String cityCode, Integer level);

    /**
     * 通过ID持续向上查找，直到最父级
     *
     * @param areaId 区域ID
     * @return 从当前节点出发，直接到无父级为止
     */
    List<Area> findChain(Integer areaId);
}