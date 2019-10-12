package com.uptang.cloud.task.mode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.pojo.model.BaseModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-10
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("xty_scan")
public class PaperScan extends BaseModel {
    private static final long serialVersionUID = -2830203141783860322L;

    /**
     * 试卷格式ID
     */
    @TableId(type = IdType.NONE)
    private Integer id;

    /**
     * 准考证号
     */
    @TableField(value = "zkzh")
    private String ticketNumber;

    /**
     * 科目代码
     */
    @TableField(value = "kmdm")
    private String subjectCode;

    /**
     * 科目代码
     */
    @TableField(value = "path")
    private String imagePath;

    @Builder
    public PaperScan(Integer id, String ticketNumber, String subjectCode, String imagePath) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.subjectCode = subjectCode;
        this.imagePath = imagePath;
    }
}
