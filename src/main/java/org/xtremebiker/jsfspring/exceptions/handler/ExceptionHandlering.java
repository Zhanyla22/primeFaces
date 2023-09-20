package org.xtremebiker.jsfspring.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.xtremebiker.jsfspring.enums.ResultCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.xtremebiker.jsfspring.dto.response.ResponseDto;
import org.xtremebiker.jsfspring.exceptions.BaseException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlering extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException e) {
        return buildBaseResponseMessage(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return buildBaseResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return buildBaseResponseMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildBaseResponseMessage(String message, HttpStatus status) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .status(ResultCode.EXCEPTION)
                        .message(message)
                        .build(),
                status
        );
    }
}
