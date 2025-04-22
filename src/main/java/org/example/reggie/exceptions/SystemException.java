package org.example.reggie.exceptions;

import lombok.Data;

/**
 * @author 唐三
 * description: 系统异常
 */
@Data
public class SystemException extends RuntimeException {

    //状态
    private Integer code;

    public SystemException(String message,Integer code) {
        super(message);
        this.code = code;
    }
}
