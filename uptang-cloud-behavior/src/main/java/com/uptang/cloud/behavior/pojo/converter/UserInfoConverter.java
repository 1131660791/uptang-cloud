package com.uptang.cloud.behavior.pojo.converter;

import com.uptang.cloud.behavior.pojo.vo.UserInfoVO;
import com.uptang.cloud.pojo.model.user.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-19
 */
@Mapper
public interface UserInfoConverter {
    UserInfoConverter INSTANCE = Mappers.getMapper(UserInfoConverter.class);

    /**
     * 将用户实体转换成VO
     *
     * @param userInfo 用户信息
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    UserInfoVO toVo(UserInfo userInfo);

    /**
     * 将用户VO转换成实体
     *
     * @param userInfo 用户信息
     * @return 转换后实体
     */
    UserInfo toModel(UserInfoVO userInfo);
}
