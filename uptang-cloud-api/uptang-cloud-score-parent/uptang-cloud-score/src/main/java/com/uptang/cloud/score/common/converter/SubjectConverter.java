package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.vo.SubjectVO;
import com.uptang.cloud.score.dto.SubjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 17:17
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Mapper
public interface SubjectConverter {

    SubjectConverter INSTANCE = Mappers.getMapper(SubjectConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param subject 履历表
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "scoreNumber",expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(subject.getScoreNumber()))")
    SubjectVO toVo(SubjectDTO subject);

    /**
     * 将附件VO转换成实体
     *
     * @param subject 履历表
     * @return 转换后实体
     */
    @Mapping(target = "scoreNumber",expression = "java(com.uptang.cloud.score.common.util.Calculator.defaultNumberScore(subject.getScoreNumber()))")
    SubjectDTO toDTO(SubjectVO subject);

    /**
     * 将附件VO转换成实体
     *
     * @param subject 履历表
     * @return 转换后实体
     */
    @Mapping(target = "scoreNumber",expression = "java(com.uptang.cloud.score.common.util.Calculator.defaultNumberScore(subject.getScoreNumber()))")
    Subject toModel(SubjectVO subject);
}
