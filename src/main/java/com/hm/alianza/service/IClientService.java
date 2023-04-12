package com.hm.alianza.service;

import com.hm.alianza.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    Optional<ClientEntity> findBySharedId(String sharedId);

    ClientEntity save(ClientEntity clientEntity);

    List<ClientEntity> findAll();
}
