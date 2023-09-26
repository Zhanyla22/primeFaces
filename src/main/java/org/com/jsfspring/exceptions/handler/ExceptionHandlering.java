package org.com.jsfspring.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.com.jsfspring.dto.response.ResponseDto;
import org.com.jsfspring.enums.ResultCode;
import org.com.jsfspring.exceptions.BaseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * обрабатывает исключения и приводит в структурированные HTTP ответ,
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlering extends ResponseEntityExceptionHandler {

    /**
     * обработка исключений BaseException
     *
     * @param e
     * @return сообщение об ошибке и HTTP статус
     */
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException e) {
        return buildBaseResponseMessage(e.getMessage(), e.getStatus());
    }

    /**
     * обработка исключений RuntimeException
     *
     * @param e
     * @return HTTP ответ со статусом 500
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return buildBaseResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * обработка исключений MethodArgumentNotValidException,
     * исключения связанные с  невалидными аргументами в методе при валидации входных данных контроллера
     *
     * @param ex      - обьект исключения
     * @param headers - заголовка HTTP-ответа
     * @param status  - статус HTTP-ответа.
     * @param request - текущий HTTP запрос
     * @return -  возвращает HTTP-ответ с сообщением об ошибке и статусом "400 Bad Request".
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return buildBaseResponseMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * структурированный HTTP-ответ на основе параметров
     * @param message - сообщение об ошибке/исключение
     * @param status  - http статус
     * @return структурированный HTTP ответ
     */
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
