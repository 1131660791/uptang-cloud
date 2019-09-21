package com.uptang.cloud.pojo.condition;

import com.uptang.cloud.pojo.enums.StateEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@NoArgsConstructor
public class QueryCondition {
    public String keyword;
    public Integer[] states;

    public void setStates(StateEnum... states) {
        if (ArrayUtils.isNotEmpty(states)) {
            this.states = Arrays.stream(states)
                    .filter(Objects::nonNull)
                    .map(StateEnum::getCode)
                    .distinct()
                    .toArray(Integer[]::new);
        }
    }

    public void setState(StateEnum state) {
        if (Objects.nonNull(state)) {
            states = new Integer[]{state.getCode()};
        }
    }
}
