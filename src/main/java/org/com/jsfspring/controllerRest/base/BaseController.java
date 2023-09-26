package org.com.jsfspring.controllerRest.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.com.jsfspring.dto.response.ResponseDto;
import org.com.jsfspring.enums.ResultCode;

/**
 * содержит общую структуру ответа запросов
 */
public class BaseController {

    /**
     * метод создает успешный HTTP-ответ с объектом ResponseDto, принимает любой тип данных result
     * @param result
     * @return ResponseDto
     * @param <T>
     */
    protected <T> ResponseEntity<ResponseDto> constructSuccessResponse(T result) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .data(result)
                        .status(ResultCode.SUCCESS)
                        .build(),
                HttpStatus.OK
        );
    }

    /**
     * метод создает успешный HTTP-ответ с объектом ResponseDto(сожержит строку details), принимает details
     * @param details
     * @return ResponseDto
     */
    protected <T> ResponseEntity<ResponseDto> constructSuccessResponse(String details) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .message(details)
                        .status(ResultCode.SUCCESS)
                        .build(),
                HttpStatus.OK
        );
    }
}
