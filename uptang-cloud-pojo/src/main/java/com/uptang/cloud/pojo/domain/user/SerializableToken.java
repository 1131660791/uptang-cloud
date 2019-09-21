package com.uptang.cloud.pojo.domain.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 可传递的授权令牌
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@NoArgsConstructor
public class SerializableToken implements Serializable, Cloneable {
    private static final long serialVersionUID = -3636323066631262080L;

    private Long userId;
    private Long timestamp;
    private Long host;
    private Integer userType;

    /**
     * 让每次生成的 Token样子有很大变化
     */
    private Integer random;

    @Builder
    public SerializableToken(Long userId, Long timestamp, Long host, Integer userType) {
        this.random = ThreadLocalRandom.current().nextInt(100_100_100);

        this.userId = null == userId ? null : userId + random;
        this.timestamp = null == timestamp ? null : timestamp + random;
        this.host = null == host ? null : host + random;
        this.userType = null == userType ? null : userType + random;
    }
}
