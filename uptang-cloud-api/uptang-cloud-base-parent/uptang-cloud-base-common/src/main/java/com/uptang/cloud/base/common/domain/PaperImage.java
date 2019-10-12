package com.uptang.cloud.base.common.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-08
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PaperImage implements Serializable {
    private static final long serialVersionUID = 65784896986032307L;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 考试代码
     */
    private String examCode;

    /**
     * 科目代码
     */
    private String subjectCode;

    /**
     * 题号
     */
    private String itemNum;

    /**
     * 是否竖拼， 默认 true
     */
    private Boolean vertically;

    /**
     * 图片拼接详情
     */
    @NotNull(message = "图片拼接详情")
    private List<PaperImageSource> sources;

    @Builder
    public PaperImage(Integer studentId, String examCode, String subjectCode, String itemNum,
                      Boolean vertically, List<PaperImageSource> sources) {
        this.studentId = studentId;
        this.examCode = examCode;
        this.subjectCode = subjectCode;
        this.itemNum = itemNum;
        this.vertically = vertically;
        this.sources = sources;
    }
}
