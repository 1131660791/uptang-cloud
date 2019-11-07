package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.model.Student;
import com.uptang.cloud.score.common.vo.StudentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 13:54
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface StudentConverter {


    StudentConverter INSTANCE = Mappers.getMapper(StudentConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param student 简略学生信息
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "semesterCode", source = "semesterCode.code")
    @Mapping(target = "gender", source = "gender.code")
    @Mapping(target = "genderText", source = "semesterCode.desc")
    StudentVO toVo(Student student);

    /**
     * 将附件VO转换成实体
     *
     * @param student 简略学生信息
     * @return 转换后实体
     */
    default Student toModel(StudentVO student) {
        Student student1 = new Student();
        student1.setClassId(student.getClassId());
        student1.setClassName(student.getClassName());
        student1.setCreatedTime(student.getCreatedTime());
        student1.setUpdatedTime(student.getUpdatedTime());
        student1.setGender(GenderEnum.parse(student.getGender()));
        return student1;
    }
}
