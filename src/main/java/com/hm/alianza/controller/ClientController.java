package com.hm.alianza.controller;

import com.hm.alianza.application.IClientApp;
import com.hm.alianza.common.Constants;
import com.hm.alianza.dto.ClientDto;
import com.hm.alianza.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import java.util.List;
import java.util.stream.Collectors;

import static com.hm.alianza.common.Constants.SEPARATOR;
import static com.hm.alianza.common.Constants.SHARED_ID_PATH;

@Slf4j
@RestController
@Validated
@RequestMapping(Constants.ROOT)
public class ClientController {

    @Autowired
    private IClientApp iClientApp;

    @PostMapping
    public ResponseEntity<Response<ClientDto>> createClient(
            @Valid @RequestBody ClientDto clientDto
    ) {
        log.info("ClientController::createClient Request: [{}]", clientDto.toString());
        var response = iClientApp.save(clientDto);
        log.info("ClientController::createClient Response: [{}]", response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping(SHARED_ID_PATH)
    public ResponseEntity<Response<ClientDto>> findBySharedId(
            @PathVariable
            @Valid
            @Pattern(regexp = Constants.SHARED_ID_REGEXP, message = Constants.SHARED_ID_REGEXP_MSG) String sharedId
    ) {
        var response = iClientApp.findBySharedId(sharedId);
        log.info("ClientController::findBySharedId Response: [{}]", response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<ClientDto>>> findAll() {
        var response = iClientApp.findAll();
        log.info("ClientController::findBySharedId Response: [{}]",
                response.getData().stream().map(ClientDto::toString).collect(Collectors.joining(SEPARATOR)));
        return ResponseEntity.ok(response);
    }

}
