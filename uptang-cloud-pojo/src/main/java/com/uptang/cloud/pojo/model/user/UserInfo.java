package com.uptang.cloud.pojo.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@TableName("user_info")
public class UserInfo implements Serializable, Cloneable {
    private static final long serialVersionUID = -3147337235762480618L;

    @TableId(value = "ID", type = IdType.NONE)
    private String id;

    /**
     * 用户名称
     */
    @TableField(value = "YHMC")
    private String userName;

    /**
     * 用户编号
     */
    @TableField(value = "FK_YH_ID")
    private String userCode;

    /**
     * 手机号
     */
    @TableField(value = "SJH")
    private String mobile;

    /**
     * 电子邮箱
     */
    @TableField(value = "YX")
    private String email;

    /**
     * 单位名称
     */
    @TableField(value = "DWMC")
    private String schoolName;

    /**
     * 创建时间
     */
    @TableField(value = "CJSJ")
    private Date createdTime;

    /**
     * 最后修改时间
     */
    @TableField(value = "XGSJ")
    private Date updatedTime;
}
