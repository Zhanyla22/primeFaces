package org.xtremebiker.jsfspring.controllerJsf.base;

import org.xtremebiker.jsfspring.dto.response.ResponseDto;
import org.xtremebiker.jsfspring.enums.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    protected <T> ResponseEntity<ResponseDto> constructSuccessResponse(T result, String details) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .data(result)
                        .status(ResultCode.SUCCESS)
                        .message(details)
                        .build(),
                HttpStatus.OK
        );
    }

    protected <T> ResponseEntity<ResponseDto> constructFailResponse(String details) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .data(ResultCode.FAIL)
                        .message(details)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    protected <T> ResponseEntity<ResponseDto> constructFailResponse(T errorResult, String details) {
        return new ResponseEntity<>(
                ResponseDto.builder()
                        .data(errorResult)
                        .status(ResultCode.FAIL)
                        .message(details)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}