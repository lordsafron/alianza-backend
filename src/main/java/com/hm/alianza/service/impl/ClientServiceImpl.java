package com.hm.alianza.service.impl;

import com.hm.alianza.entity.ClientEntity;
import com.hm.alianza.exceptions.DatabaseException;
import com.hm.alianza.repository.ClientRepository;
import com.hm.alianza.service.IClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientEntity> findBySharedId(String sharedId) {
        try {
            return clientRepository.findBySharedIdIgnoreCase(sharedId);
        } catch (Exception ex) {
            log.error("ClientServiceImpl::findBySharedId --sharedId [{}] --exCause [{}] --exMessage [{}]",
                    sharedId, ex.getCause(), ex.getMessage());
            throw new DatabaseException("Error en banco de datos");
        }

    }

    @Override
    @Transactional
    public ClientEntity save(ClientEntity clientEntity) {
        try {
            return clientRepository.save(clientEntity);
        } catch (Exception ex) {
            log.error("ClientServiceImpl::save --exCause [{}] --exMessage [{}]", ex.getCause(), ex.getMessage());
            throw new DatabaseException("Error en banco de datos");
        }
    }

    @Override
    public List<ClientEntity> findAll() {
        try {
            return clientRepository.findAll();
        } catch (Exception ex) {
            log.error("ClientServiceImpl::save --exCause [{}] --exMessage [{}]", ex.getCause(), ex.getMessage());
            throw new DatabaseException("Error en banco de datos");
        }
    }
}
