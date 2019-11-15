package com.uptang.cloud.score.dto;

import com.uptang.cloud.score.common.model.AcademicResume;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-07 15:35
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ResumeJoinArchiveDTO extends AcademicResume implements Serializable {

    /**
     * 归档ID
     */
    private Long archiveId;

    /**
     * 归档数据
     */
    private String archiveDetail;
}
