package com.hm.alianza.mapper;

import com.hm.alianza.utils.GenerateUtil;
import com.hm.alianza.dto.ClientDto;
import com.hm.alianza.entity.ClientEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {

    private ClientMapper() {}

    public static ClientDto entityToDto(ClientEntity clientEntity) {
        return ClientDto.builder()
                .businessId(clientEntity.getBusinessId())
                .sharedId(clientEntity.getSharedId())
                .email(clientEntity.getEmail())
                .id(clientEntity.getId())
                .phone(clientEntity.getPhone())
                .createAt(clientEntity.getCreatedAt())
                .build();
    }

    public static ClientEntity dtoToEntity(ClientDto clientDto) {
        return ClientEntity.builder()
                .sharedId(GenerateUtil.generateSharedId(clientDto.getBusinessId()))
                .businessId(clientDto.getBusinessId())
                .email(clientDto.getEmail())
                .phone(clientDto.getPhone())
                .build();
    }

    public static List<ClientDto> entityListToDtoList(List<ClientEntity> entityList) {
        return entityList.stream().map(ClientMapper::entityToDto).collect(Collectors.toList());
    }
}
