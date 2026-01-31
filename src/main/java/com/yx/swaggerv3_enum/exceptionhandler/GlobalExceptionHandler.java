package com.yx.swaggerv3_enum.exceptionhandler;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.yx.swaggerv3_enum.config.convert.EnumConvertContext;
import com.yx.swaggerv3_enum.config.convert.EnumConvertErrorGroup;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author
 */
@Hidden
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Integer PAYLOAD_MAX_LENGTH = 1024;

    /**
     * 处理Get请求中 使用@Valid @Validated验证路径中请求实体校验失败后抛出的异常
     *
     * @param e
     * @return 400 http状态码和错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public <U> APIError<U> bindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors()
                .stream()
                .map(error -> {
                    StringBuffer errMsgSb = new StringBuffer();

                    // 数据类型不匹配时，自定义错误信息
                    if (error instanceof FieldError && error.contains(TypeMismatchException.class)) {
                        FieldError fieldError = (FieldError) error;
                        log.warn("字段 {}: 数据类型不匹配！", fieldError.getField());
                        errMsgSb.append(fieldError.getField()).append(": ").append("数据类型不匹配");
                    } else {
                        errMsgSb.append(error.getDefaultMessage());
                    }
                    return errMsgSb.append("; ").toString();
                })
                .collect(Collectors.joining());
        log.warn("BindException => {}", message);

        return APIError.invalidParam(message);
    }


    /**
     * Get请求的对象参数校验失败后抛出的异常
     *
     * @param e ConstraintViolationException 异常对象
     * @return 400 http状态码和错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public <U> APIError<U> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
        log.warn("ConstraintViolationException => {}", message);

        return APIError.invalidParam(message);
    }

    /**
     * 处理错误的JSON格式参数引发的异常
     *
     * @param e HttpMessageConversionException 异常对象
     * @return 400 http状态码和错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    public <U> APIError<U> httpMessageConversionException(HttpMessageConversionException e) {
        String msg = null;
        Throwable cause = e.getCause();
        if (cause instanceof JsonParseException) {
            JsonParseException jpe = (JsonParseException) cause;
            log.warn("JsonParseException 参数格式错误 => {}", jpe.getOriginalMessage());
            msg = "参数格式错误";
        }
        // special case of JsonMappingException below, too much class detail in error messages
        else if (cause instanceof MismatchedInputException) {
            MismatchedInputException mie = (MismatchedInputException) cause;
            if (mie.getPath() != null && !mie.getPath().isEmpty()) {
                msg = mie.getPath().get(0).getFieldName() + ": 参数值不合法";
                log.warn("MismatchedInputException => {}", msg);
            }

            // just in case, haven't seen this condition
            else {
                msg = "参数不合法";
            }
        } else if (cause instanceof JsonMappingException) {
            JsonMappingException jme = (JsonMappingException) cause;
            log.warn("JsonMappingException => {}", jme.getOriginalMessage());
            msg = "参数错误";
            if (jme.getPath() != null && !jme.getPath().isEmpty()) {
                msg = jme.getPath().get(0).getFieldName() + "：值无效";
                log.warn(msg);
            }
        }

        return APIError.invalidParam(msg);
    }

    /**
     * http消息无法解析
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <U> APIError<U> handleHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException e) {
        String originalMsg = getReqContent(req);
        log.error("HttpMessageNotReadableException, request url: {} Http Message Not Readable. {}. {}", req.getRequestURI(), originalMsg, e.getMessage());
        String msg = null;
        Throwable cause = e.getCause();
        // Jackson 严格 JSON 校验失败
        if (cause instanceof MismatchedInputException
                && cause.getMessage() != null
                && cause.getMessage().contains("Trailing token")) {

            return APIError.invalidParam("请求体不是合法 JSON，请检查是否存在多余内容");
        }

        if (cause instanceof JsonMappingException jme) {
            Throwable root = jme.getCause();

            // 处理 枚举反序列化失败的场景
            if (root instanceof InvalidEnumValueException enumEx) {
                String fieldName = null;
                if (jme.getPath() != null && !jme.getPath().isEmpty()) {
                    fieldName = jme.getPath().get(0).getFieldName();
                }
                msg = fieldName == null ? enumEx.getMessage() : fieldName + ":" + enumEx.getMessage();

                return APIError.invalidParam(msg);
            }

            msg = jme.getOriginalMessage();
            List<JsonMappingException.Reference> path = jme.getPath();
            if (path != null && !path.isEmpty()) {
                msg = path.get(0).getFieldName() + ":" + jme.getOriginalMessage();
                log.warn(msg);
            }
        }


        return APIError.invalidParam(msg);
    }

    /**
     * 用于：异常场景获取原始报文
     *
     * @param req
     * @return java.lang.String
     * @author zt19191
     * @date 2022/3/4 15:26
     */
    private String getReqContent(HttpServletRequest req) {
        String originalMsg = "";
        if (req instanceof ContentCachingRequestWrapper) {
            originalMsg = getPayLoad(((ContentCachingRequestWrapper) req).getContentAsByteArray(), req.getCharacterEncoding());
        }
        return originalMsg;
    }

    private String getPayLoad(byte[] buf, String characterEncoding) {
        String payload = "";
        if (buf == null) {
            return payload;
        }
        if (buf.length > 0) {
            int length = Math.min(buf.length, PAYLOAD_MAX_LENGTH);
            try {
                payload = new String(buf, 0, length, characterEncoding);
            } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
            }
        }
        return payload;
    }

    /**
     * Post请求 @RequestBody上 使用@Validated校验失败后抛出的异常是MethodArgumentNotValidException异常
     *
     * @param e
     * @return 400 http状态码和错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <U> APIError<U> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<EnumConvertErrorGroup> invalidValues = EnumConvertContext.getAndClear();

        if (!invalidValues.isEmpty()) {
            // 这里你就可以用 EnumDef.getEnumName() 拼业务文案
            EnumConvertErrorGroup enumConvertErrorGroup = invalidValues.get(0);
            String inputInvalidValue = enumConvertErrorGroup.getInvalidValues().stream()
                    .collect(Collectors.joining(",", "【", "】"));
            String msg = inputInvalidValue + "不是合法的 " + enumConvertErrorGroup.getEnumName();

            return APIError.invalidParam(msg);
        }

        // fallback：走原有校验错误
        Optional<String> optional = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotBlank)
                .findFirst();
        String msg;
        msg = optional.orElseGet(e::getMessage);
        log.warn("MethodArgumentNotValidException => {}", msg);

        return APIError.invalidParam(msg);
    }

    /**
     * 缺少参数抛出的异常
     *
     * @param e
     * @return 400 http状态码和错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public <U> APIError<U> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.warn("缺少参数 => {} MissingServletRequestParameterException", e.getMessage());
        String msg = MessageFormat.format("缺少参数{0}", e.getParameterName());

        return APIError.missRequiredParam(msg);
    }

    /**
     * path参数为空
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingPathVariableException.class)
    public <U> APIError<U> missingPathVariableExceptionHandler(MissingPathVariableException e) {
        log.warn("MissingPathVariableException => {}", e.getMessage());
        String msg = MessageFormat.format("缺少path参数{0}", e.getVariableName());

        return APIError.missRequiredParam(msg);
    }

    /**
     * 参数类型转换异常
     *
     * @param e
     * @return 400 http状态码和错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public <U> APIError<U> mismatchErrorHandler(MethodArgumentTypeMismatchException e) {
        log.warn("参数转换失败，方法 ==> {} ,参数 ==> {} 信息 ==> {}", Objects.requireNonNull(e.getParameter().getMethod()).getName(), e.getName(), e.getLocalizedMessage());
        if (e.getCause() instanceof NumberFormatException) {
            String msg = MessageFormat.format("参数{0}只能传数字", e.getName());
            return APIError.invalidParam(msg);
        }

        return APIError.invalidParam(e.getMessage());
    }


    /**
     * 请求的URL不存在
     *
     * <p>
     * 需要开启如下配置，否则不生效
     * spring.mvc.throw-exception-if-no-handler-found=true
     * spring.resources.add-mappings=false
     * </p>
     *
     * @param e   NoHandlerFoundException
     * @param <U> 泛型
     * @return 404 http状态码和错误信息
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public <U> APIError<U> noHandlerFoundException(NoHandlerFoundException e) {
        log.warn("请求的路径不存在 => {}", e.getMessage());
        return APIError.urlNotFound("请求的URL不存在【" + e.getHttpMethod() + " " + e.getRequestURL() + "】");
    }

    /**
     * 不支持的请求方法
     * 返回405状态码
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public <U> APIError<U> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("HttpRequestMethodNotSupportedException => 不支持的请求方法: {}", e.getMethod());

        return APIError.requestMethodNotSupported();
    }


    /**
     * 其他 未列举出的异常，统一处理
     *
     * @param t
     * @return 500 http状态码和错误信息
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public <U> APIError<U> throwableHandler(Throwable t) {
        log.error("throwable => {}", t.getMessage(), t);
        return APIError.internalServerError(t.getMessage());
    }
}


