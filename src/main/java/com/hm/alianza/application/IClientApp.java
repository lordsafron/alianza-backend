package com.hm.alianza.application;

import com.hm.alianza.dto.ClientDto;
import com.hm.alianza.dto.Response;

import java.util.List;

public interface IClientApp {

    Response<ClientDto> findBySharedId(String sharedId);

    Response<ClientDto> save(ClientDto clientDto);

    Response<List<ClientDto>> findAll();
}
