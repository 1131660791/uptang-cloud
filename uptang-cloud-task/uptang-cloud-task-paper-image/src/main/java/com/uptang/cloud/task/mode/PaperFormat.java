package com.uptang.cloud.task.mode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("xty_paper_cat")
public class PaperFormat implements Serializable, Cloneable {
    /**
     * PK
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 题号
     */
    @TableField(value = "item_num")
    private String itemNum;

    /**
     * 格式ID
     */
    @TableField(value = "format_id")
    private Integer formatId;

    /**
     * 格式内容
     */
    @TableField(value = "area")
    private String formatContent;

    @Builder
    public PaperFormat(Integer id, String itemNum, Integer formatId, String formatContent) {
        this.id = id;
        this.itemNum = itemNum;
        this.formatId = formatId;
        this.formatContent = formatContent;
    }
}
