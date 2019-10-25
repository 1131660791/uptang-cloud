package com.uptang.cloud.base.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.base.common.enums.AttachmentEnum;
import com.uptang.cloud.pojo.enums.StateEnum;
import com.uptang.cloud.pojo.model.BaseModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("base_attachment")
public class Attachment extends BaseModel {
    private static final long serialVersionUID = 2030521513068335005L;

    /**
     * 附件ID
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 扩展名
     */
    private String extName;

    /**
     * 源文件名
     */
    private String srcName;

    /**
     * 附件描述
     */
    private String description;

    /**
     * 外网访问相对路径
     */
    private String relativePath;

    /**
     * 外网访问绝对路径
     */
    @TableField(exist = false)
    private String fullPath;

    /**
     * 附件类型
     */
    private AttachmentEnum type;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 状态
     */
    private StateEnum state;


    @Builder
    public Attachment(Long id, String extName, String srcName, String description, String relativePath,
                      String fullPath, AttachmentEnum type, Long size, StateEnum state) {
        this.id = id;
        this.extName = extName;
        this.srcName = srcName;
        this.description = description;
        this.relativePath = relativePath;
        this.fullPath = fullPath;
        this.type = type;
        this.size = size;
        this.state = state;
    }
}
