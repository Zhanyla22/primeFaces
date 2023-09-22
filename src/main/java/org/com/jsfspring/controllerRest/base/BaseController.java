package org.com.jsfspring.controllerRest.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.com.jsfspring.dto.response.ResponseDto;
import org.com.jsfspring.enums.ResultCode;

public class BaseController {
    protected <T> ResponseEntity<ResponseDto> constructSuccessResponse(T result) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .data(result)
                        .status(ResultCode.SUCCESS)
                        .build(),
                HttpStatus.OK
        );
    }

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
