package com.uptang.cloud.task.mode;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-12
 */
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@TableName("tb_paper_cat")
public class PaperFormat implements Serializable, Cloneable {
    /**
     * 题号
     */
    @TableField(value = "item_num")
    private String itemNum;

    /**
     * 格式
     */
    @TableField(value = "area")
    private String format;

    /**
     * 格式文件顺序
     */
    @TableField(value = "pic_num")
    private Integer rank;

    @Builder
    public PaperFormat(String itemNum, String format, Integer rank) {
        this.itemNum = itemNum;
        this.format = format;
        this.rank = rank;
    }
}
