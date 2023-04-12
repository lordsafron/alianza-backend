package com.hm.alianza.exceptions;

import com.hm.alianza.dto.Response;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.hm.alianza.common.Constants.TRACE_ID;

@ControllerAdvice
public class DatabaseExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Response> exceptionHandler(DatabaseException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    Response.builder()
                            .message(ex.getMessage())
                            .traceId(MDC.get(TRACE_ID))
                            .build()
                );
    }

}
