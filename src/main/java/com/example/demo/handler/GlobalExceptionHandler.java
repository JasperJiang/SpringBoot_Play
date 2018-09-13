package com.example.demo.handler;

import com.example.demo.enums.ErrorCodesEnum;
import com.example.demo.exception.AppException;
import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by jbelligund001 on 2/17/2016.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(DuplicateException.class)
    public void conflict(HttpServletResponse response, DuplicateException ex)
            throws IOException {
        response.sendError(HttpStatus.CONFLICT.value(), getMsg(ex.getMessage(), ex.getArgs()));
    }

    @ExceptionHandler(NotFoundException.class)
    public void notFound(HttpServletResponse response, NotFoundException ex) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), getMsg(ex.getMessage(), ex.getArgs()));
    }


    @ExceptionHandler(AppException.class)
    public void appException(HttpServletRequest request, HttpServletResponse response,
                             AppException ex) throws IOException {

        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), getMsg(ex.getMessage(), ex.getArgs()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void invalidArguments(HttpServletResponse response, MethodArgumentNotValidException ex) throws IOException {
        handleSpringValidationException(response, ex.getBindingResult());
    }

    private void handleSpringValidationException(HttpServletResponse response, BindingResult result) throws IOException {
        String msg = result.getFieldErrors()
                .stream()
                .map(this::error)
                .collect(Collectors.joining("\n"));

        response.sendError(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler(BindException.class)
    public void invalidArguments(HttpServletResponse response, BindException ex) throws IOException {
        handleSpringValidationException(response, ex.getBindingResult());
    }

    private String getMsg(String code, Object[] args) {
        return messageSource.getMessage(code, args, code, null);
    }


    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletResponse response, Exception e) throws IOException {
        log.error("Server error occurred", e);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCodesEnum.ERR_GENERIC.name());
    }

    private String error(FieldError error) {
        return (error != null) ? error.getDefaultMessage() : "";
    }

}