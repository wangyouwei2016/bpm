package com.dstz.base.web.controller.advice;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.enums.GlobalApiCodes;
import com.dstz.base.common.exceptions.ApiException;
import com.dstz.base.common.exceptions.BusinessMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * rest rest advice
 *
 * @author wacxhs
 * @since 2022-01-22
 */
@RestControllerAdvice
public class AbRestControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(AbRestControllerAdvice.class);

    /**
     * 参数校验异常处理器
     *
     * @param validationException 校验异常
     * @return 接口响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> validationExceptionHandler(MethodArgumentNotValidException validationException) {
        ApiResponse<Void> apiResponse = new ApiResponse<Void>().withCode(GlobalApiCodes.PARAMETER_INVALID.getCode());
        FieldError fieldError = validationException.getBindingResult().getFieldError();
        if (Objects.nonNull(fieldError)) {
            apiResponse.withMessage(fieldError.getDefaultMessage());
        }
        return apiResponse;
    }

    /**
     * 内部错误处理器
     *
     * @param throwable throwable
     * @return 接口响应
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> internalErrorHandler(Throwable throwable) {
        Throwable rootCause = ExceptionUtil.getCausedBy(throwable, ApiException.class);
        if(rootCause == null){
            rootCause = throwable;
        }
        // 提示性的不做堆栈记录
        if (rootCause instanceof BusinessMessage) {
            return ApiResponse.fail(((BusinessMessage) rootCause).getBaseCode().getCode(), rootCause.getMessage());
        }

        logger.error(throwable.getMessage(), throwable);

        String code;
        String message;
        if(rootCause instanceof ApiException){
            ApiException apiException = (ApiException) rootCause;
            code = apiException.getBaseCode().getCode();
            message = apiException.getBaseCode().getMessage();
        }else{
            code = GlobalApiCodes.INTERNAL_ERROR.getCode();
            message ="PROD".equalsIgnoreCase(SpringUtil.getActiveProfile()) ? GlobalApiCodes.INTERNAL_ERROR.getMessage() : rootCause.toString();
        }
        return ApiResponse.fail(code, message);
    }
}
