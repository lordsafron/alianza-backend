package com.hm.alianza.exceptions;

import com.hm.alianza.dto.Response;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

import static com.hm.alianza.common.Constants.ONE;
import static com.hm.alianza.common.Constants.SEPARATOR_2;
import static com.hm.alianza.common.Constants.SEPARATOR_3;
import static com.hm.alianza.common.Constants.TRACE_ID;
import static com.hm.alianza.common.Constants.ZERO;

@ControllerAdvice
public class ConstraintViolationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Response> exceptionHandler(ConstraintViolationException ex) {
        var field = ex.getMessage().split(SEPARATOR_2)[ZERO];
        return ResponseEntity.badRequest()
                .body(
                        Response.builder()
                                .message(field.split(SEPARATOR_3)[field.split(SEPARATOR_3).length-ONE]
                                        .concat(SEPARATOR_2)
                                        .concat(ex.getMessage().split(SEPARATOR_2)[ONE]))
                                .traceId(MDC.get(TRACE_ID))
                                .build()
                );
    }

}
