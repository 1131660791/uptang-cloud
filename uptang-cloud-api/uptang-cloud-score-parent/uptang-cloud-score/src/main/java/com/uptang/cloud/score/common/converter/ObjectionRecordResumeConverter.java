package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.ObjectionRecordResume;
import com.uptang.cloud.score.common.vo.ObjectionRecordResumeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-15 17:26
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Mapper
public interface ObjectionRecordResumeConverter {


    ObjectionRecordResumeConverter INSTANCE = Mappers.getMapper(ObjectionRecordResumeConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param resume
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "scoreNumber", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(resume.getScoreNumber()))")
    ObjectionRecordResumeVO toVo(ObjectionRecordResume resume);

    /**
     * 将附件VO转换成实体
     *
     * @param resume
     * @return 转换后实体
     */
    @Mapping(target = "scoreNumber", expression = "java(com.uptang.cloud.score.common.util.Calculator.defaultNumberScore(resume.getScoreNumber()))")
    ObjectionRecordResume toModel(ObjectionRecordResumeVO resume);
}
