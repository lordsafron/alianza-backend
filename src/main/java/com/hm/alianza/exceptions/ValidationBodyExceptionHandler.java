package com.hm.alianza.exceptions;

import com.hm.alianza.dto.Response;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

import static com.hm.alianza.common.Constants.TRACE_ID;

@ControllerAdvice
public class ValidationBodyExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Response> exceptionHandler(ValidationBodyException ex) {
        return ResponseEntity.badRequest().body(
                Response.builder()
                        .message(ex.getMessage())
                        .traceId(MDC.get(TRACE_ID))
                        .build()
        );
    }

}
