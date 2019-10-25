package com.uptang.cloud.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseModel implements Serializable, Cloneable {
    private static final long serialVersionUID = 8173923769422639108L;

    /**
     * 创建时间
     */
    protected Date createdTime;

    /**
     * 最后修改时间
     */
    protected Date updatedTime;
}
