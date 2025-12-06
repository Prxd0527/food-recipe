package com.food.recipe.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 * @author prxd0527
 * @date 2025-12-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private Integer code;
    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
