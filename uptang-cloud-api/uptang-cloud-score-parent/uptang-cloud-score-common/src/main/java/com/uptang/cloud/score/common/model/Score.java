package com.uptang.cloud.score.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.pojo.model.BaseModel;
import com.uptang.cloud.score.common.enums.LevelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@TableName("table_name")
public class Score extends BaseModel {

    /**
     * 成绩ID
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 等级划分
     */
    private LevelEnum level;
}
