package org.example.reggie.exceptions;
import lombok.Data;

/**
 * @author 唐三
 * description: 业务异常
 */
@Data
public class BusinessException extends RuntimeException {

    //异常状态
    private Integer code;

    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

}
