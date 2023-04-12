package com.hm.alianza.exceptions;

import com.hm.alianza.common.Constants;
import com.hm.alianza.dto.Response;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hm.alianza.common.Constants.SEPARATOR;
import static com.hm.alianza.common.Constants.TRACE_ID;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Response> exceptionHandler(MethodArgumentNotValidException ex) {
        List<String> errors =
                ex.getBindingResult()
                        .getFieldErrors().stream()
                        .map(e -> e.getField().concat(Constants.SEPARATOR_2)
                                .concat(Objects.requireNonNull(e.getDefaultMessage())))
                        .collect(Collectors.toList());
        return ResponseEntity.badRequest()
                .body(
                        Response.builder()
                                .message(String.join(SEPARATOR, errors))
                                .traceId(MDC.get(TRACE_ID))
                                .build()
                );
    }

}
