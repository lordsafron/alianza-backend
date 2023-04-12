package com.hm.alianza.application;

import com.hm.alianza.dto.ClientDto;
import com.hm.alianza.dto.Response;
import com.hm.alianza.exceptions.BusinessException;
import com.hm.alianza.exceptions.NotFoundException;
import com.hm.alianza.mapper.ClientMapper;
import com.hm.alianza.service.IClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hm.alianza.common.Constants.SUCCESS;
import static com.hm.alianza.common.Constants.TRACE_ID;

@Slf4j
@Service
@AllArgsConstructor
public class ClientAppImpl implements IClientApp {

    private IClientService iClientService;

    @Override
    @Transactional(readOnly = true)
    public Response<ClientDto> findBySharedId(String sharedId) {
        var clientEntity = iClientService.findBySharedId(sharedId);
        if (clientEntity.isEmpty()) {
            log.info("ClientAppImpl::findBySharedId: record not found --sharedId [{}]", sharedId);
            throw new NotFoundException("record not found ".concat(sharedId));
        }
        var clientDto = ClientMapper.entityToDto(clientEntity.get());
        log.info("ClientAppImpl::save record client found");
        return new Response<>(
                SUCCESS,
                MDC.get(TRACE_ID),
                clientDto);
    }

    @Override
    @Transactional
    public Response<ClientDto> save(ClientDto clientDto) {
        var clientEntity = ClientMapper.dtoToEntity(clientDto);
        var oEntity = iClientService.findBySharedId(clientEntity.getSharedId());
        if (oEntity.isPresent()) {
            log.info("ClientAppImpl::save record client already exists");
            throw new BusinessException("record client already exists");
        }
        var clientDtoResponse = ClientMapper.entityToDto(iClientService.save(clientEntity));
        log.info("ClientAppImpl::save record client created");
        return new Response<>(
                SUCCESS,
                MDC.get(TRACE_ID),
                clientDtoResponse);
    }

    @Override
    public Response<List<ClientDto>> findAll() {
        var entityList = iClientService.findAll();
        var dtoList = ClientMapper.entityListToDtoList(entityList);
        return new Response<>(
                SUCCESS,
                MDC.get(TRACE_ID),
                dtoList);
    }
}
