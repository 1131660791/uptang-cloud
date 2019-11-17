package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.vo.SubjectVO;
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
     * model => VO
     *
     * @param subject model
     * @return VO
     */
    @Mapping(target = "subjectId", source = "id")
    @Mapping(target = "scoreNumber", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(subject.getScoreNumber()))")
    SubjectVO toVO(Subject subject);

    /**
     * 将附件VO转换成实体
     *
     * @param subject 履历表
     * @return 转换后实体
     */
    @Mapping(target = "scoreNumber", expression = "java(com.uptang.cloud.score.common.util.Calculator.defaultNumberScore(subject.getScoreNumber()))")
    @Mapping(source = "subjectId", target = "id")
    Subject toModel(SubjectVO subject);
}
