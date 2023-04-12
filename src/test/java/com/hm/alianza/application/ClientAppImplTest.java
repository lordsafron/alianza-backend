package com.hm.alianza.application;

import com.hm.alianza.dto.ClientDto;
import com.hm.alianza.entity.ClientEntity;
import com.hm.alianza.exceptions.BusinessException;
import com.hm.alianza.exceptions.DatabaseException;
import com.hm.alianza.exceptions.NotFoundException;
import com.hm.alianza.service.IClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientAppImplTest {

    private IClientService iClientService;

    private IClientApp iClientApp;

    @BeforeEach
    void setUp() {
        iClientService = Mockito.mock(IClientService.class);
        iClientApp = new ClientAppImpl(iClientService);
    }

    @Test
    void findBySharedId() {
        var sharedId = "pperez";
        when(iClientService.findBySharedId(sharedId))
                .thenReturn(Optional.of(new ClientEntity()));

        var result = iClientApp.findBySharedId(sharedId);

        Assertions.assertTrue(Objects.nonNull(result));
        verify(iClientService).findBySharedId(sharedId);
    }

    @Test
    void findBySharedId_throw() {
        var sharedId = "pperez";
        when(iClientService.findBySharedId(sharedId))
                .thenReturn(Optional.empty());

        var exception =
                Assertions.assertThrows(NotFoundException.class, () -> iClientApp.findBySharedId(sharedId));

        Assertions.assertTrue(exception.getMessage().startsWith("record not found "));
        verify(iClientService).findBySharedId(sharedId);
    }

    @Test
    void save() {
        var dto =
                new ClientDto(1L, "pperez", "pedro perez", "pperez@gmail.com", "123345123", null);
        var entity =
                new ClientEntity(null, "pperez", "pedro perez", "pperez@gmail.com", "123345123");
        when(iClientService.save(any(ClientEntity.class))).thenReturn(entity);

        var result = iClientApp.save(dto);

        Assertions.assertTrue(Objects.nonNull(result));
        verify(iClientService).save(entity);
    }

    @Test
    void save_throw() {
        var dto =
                new ClientDto(1L, "pperez", "pedro perez", "pperez@gmail.com", "123345123", null);
        var entity =
                new ClientEntity(null, "pperez", "pedro perez", "pperez@gmail.com", "123345123");
        when(iClientService.findBySharedId(dto.getSharedId()))
                .thenReturn(Optional.of(entity));

        var exception =
                Assertions.assertThrows(BusinessException.class, () -> iClientApp.save(dto));

        Assertions.assertEquals("record client already exists", exception.getMessage());
        verify(iClientService, never()).save(any(ClientEntity.class));
    }

    @Test
    void findAll() {
        when(iClientService.findAll())
                .thenReturn(Arrays.asList(new ClientEntity(), new ClientEntity()));

        var result = iClientApp.findAll();

        Assertions.assertEquals(2, result.getData().size());
        verify(iClientService).findAll();
    }
}