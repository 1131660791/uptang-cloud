package com.uptang.cloud.starter.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.Page;
import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@ToString
@NoArgsConstructor
public class ApiOut<T> {
    @ApiModelProperty(notes = "状态编码")
    private Integer status;

    @ApiModelProperty(notes = "状态信息")
    private String message;

    @ApiModelProperty(notes = "调试信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String debugMessage;

    @ApiModelProperty(notes = "分页信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pagination pagination;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 以快捷的方式创建参数校验响应
     *
     * @param message 提示的消息
     * @param <T>     返回的类型
     * @return ApiOut
     */
    public static <T> ApiOut<T> newParameterRequiredResponse(String message) {
        return newResponse(ResponseCodeEnum.PARAMETER_REQUIRED, message);
    }

    /**
     * 以快捷的方式创建成功的响应
     *
     * @param <T> 返回的类型
     * @return ApiOut
     */
    public static <T> ApiOut<T> newSuccessResponse(T data) {
        return null == data
                ? new Builder<T>().code(ResponseCodeEnum.SUCCESS).build()
                : new Builder<T>().code(ResponseCodeEnum.SUCCESS).data(data).build();
    }


    /**
     * 以快捷的方式创建响应
     *
     * @param code    返回码
     * @param message 提示的消息
     * @param <T>     返回的类型
     * @return ApiOut
     */
    public static <T> ApiOut<T> newResponse(ResponseCodeEnum code, String message) {
        if (StringUtils.isBlank(message)) {
            return new Builder<T>().code(code).build();
        }

        if (StringUtils.isBlank(code.getTemplate())) {
            return new Builder<T>().code(code).message(message).build();
        }

        return new Builder<T>().code(code).message(String.format(code.getTemplate(), message)).build();
    }

    /**
     * 以快捷的方式创建响应
     * 响应给客户端提示信息 如 XXX录入成功 xxx操作失败
     *
     * @param message 提示的消息
     * @param <T>     返回的类型
     * @return ApiOut
     */
    public static <T> ApiOut<T> newPrompt(String message) {
        ResponseCodeEnum success = ResponseCodeEnum.SUCCESS;
        if (StringUtils.isBlank(message)) {
            String defaultMessage = success.getDesc();
            return new Builder<T>().code(success).message(defaultMessage).build();
        }

        return new Builder<T>().code(success).message(message).build();
    }

    public static final class Builder<T> {
        private ResponseCodeEnum responseCode = ResponseCodeEnum.SUCCESS;
        private Integer status;
        private String message;
        private String debugMessage;
        private Integer totalRow;
        private Integer pageSize;
        private Integer pageIndex;
        private T data;

        public Builder() {
        }

        public Builder<T> status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder<T> code(ResponseCodeEnum responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> debugMessage(String debugMessage) {
            this.debugMessage = debugMessage;
            return this;
        }

        public Builder<T> totalRow(int totalRow) {
            if (totalRow > 0) {
                this.totalRow = totalRow;
            }
            return this;
        }

        public Builder<T> pageSize(int pageSize) {
            if (pageSize > 0) {
                this.pageSize = pageSize;
            }
            return this;
        }

        public Builder<T> pageIndex(int pageIndex) {
            if (pageIndex > 0) {
                this.pageIndex = pageIndex;
            }
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiOut<T> build() {
            if (data instanceof Page) {
                Page page = (Page) data;
                this.totalRow = (int) page.getTotal();
                this.pageSize = page.getPageSize();
                this.pageIndex = page.getPageNum();
            }
            return new ApiOut<T>(this);
        }
    }

    private ApiOut(Builder<T> builder) {
        this.status = Optional.ofNullable(builder.status).orElse(builder.responseCode.getCode());
        this.message = StringUtils.isBlank(builder.message) ? builder.responseCode.getDesc() : builder.message;
        this.debugMessage = StringUtils.isBlank(builder.debugMessage) ? null : builder.debugMessage;

        if (NumberUtils.isPositive(builder.totalRow)
                || NumberUtils.isPositive(builder.pageSize)
                || NumberUtils.isPositive(builder.pageIndex)) {
            this.pagination = new Pagination(builder.totalRow, builder.pageSize, builder.pageIndex);
        }
        this.data = builder.data;
    }
}

