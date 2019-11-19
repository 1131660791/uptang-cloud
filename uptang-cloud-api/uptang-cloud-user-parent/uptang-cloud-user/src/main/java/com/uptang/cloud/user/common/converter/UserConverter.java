package com.uptang.cloud.user.common.converter;

import com.uptang.cloud.user.common.model.User;
import com.uptang.cloud.user.common.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 用户信息 转换类
 * @author cht
 * @date 2019-11-19
 */
@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * 将用户实体转换成VO
     *
     * @param user 用户
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "gender", source = "gender.code")
    @Mapping(target = "genderDesc", source = "gender.desc")
    UserVO toVo(User user);

    /**
     * 将用户VO转换成实体
     *
     * @param user 用户
     * @return 转换后实体
     */
    @Mappings({
            @Mapping(target = "gender", expression = "java(GenderEnum.parse(user.getGender()))")
    })
    User toModel(UserVO user);
}
