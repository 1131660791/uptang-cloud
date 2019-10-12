package com.uptang.cloud.sequence.health;

import com.uptang.cloud.sequence.util.GlobalSequenceGenerator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 可自定义健康状态
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-19
 */
@Component
public class SequenceHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        long id = GlobalSequenceGenerator.getInstance().generateSequenceId();
        return Health.up()
                .withDetail("生成序号", id)
                .build();
    }
}
