package org.example.reggie.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.exceptions.BusinessException;
import org.example.reggie.exceptions.SystemException;
import org.example.reggie.utils.optimizes.ExceptionOptimize;
import org.example.reggie.utils.ExceptionOptimizeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Api("异常处理页面")
@RestControllerAdvice(annotations = {RestController.class, Service.class, Controller.class})
public class GlobalExceptionHanlderController {

    @ApiOperation("常规异常接口")
    @ExceptionHandler(BusinessException.class)
    public ResultViewEntity<String> routineException(BusinessException business) {
        return ResultViewEntity.error(
                ExceptionOptimizeUtil.returnException(business.getCode(), new ExceptionOptimize() {
                    @Override
                    public String returnException(Integer code) {
                        log.info("异常状态码: {}",code);
                        return business.getMessage();
                    }
                })
        );
    }

    @ApiOperation("系统异常接口")
    @ExceptionHandler(SystemException.class)
    public ResultViewEntity<String> systemException(SystemException system) {
        return ResultViewEntity.error(
                ExceptionOptimizeUtil.returnException(system.getCode(), new ExceptionOptimize() {
                    @Override
                    public String returnException(Integer code) {
                        log.info("异常状态码: {}",code);
                        return system.getMessage();
                    }
                })
        );
    }
}
