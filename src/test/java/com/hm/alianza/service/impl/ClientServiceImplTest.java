package com.hm.alianza.service.impl;

import com.hm.alianza.entity.ClientEntity;
import com.hm.alianza.exceptions.DatabaseException;
import com.hm.alianza.repository.ClientRepository;
import com.hm.alianza.service.IClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    private ClientRepository clientRepository;

    private IClientService iClientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        iClientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    void findBySharedId() {
        var sharedId = "pperez";
        when(clientRepository.findBySharedIdIgnoreCase(sharedId))
                .thenReturn(Optional.of(new ClientEntity()));

        var result = iClientService.findBySharedId(sharedId);

        Assertions.assertTrue(result.isPresent());
        verify(clientRepository).findBySharedIdIgnoreCase(sharedId);
    }

    @Test
    void findBySharedId_throw() {
        var sharedId = "pperez";
        when(clientRepository.findBySharedIdIgnoreCase(sharedId))
                .thenThrow(DatabaseException.class);

        var exception =
                Assertions.assertThrows(DatabaseException.class, () -> iClientService.findBySharedId(sharedId));

        Assertions.assertEquals("Error en banco de datos", exception.getMessage());
        verify(clientRepository).findBySharedIdIgnoreCase(sharedId);
    }

    @Test
    void save() {
        var entity =
                new ClientEntity(1L, "pperez", "pedro perez", "pperez@gmail.com", "123345123");
        when(clientRepository.save(entity)).thenReturn(entity);

        var result = iClientService.save(entity);

        Assertions.assertTrue(Objects.nonNull(result));
        verify(clientRepository).save(entity);
    }

    @Test
    void save_throw() {
        var entity =
                new ClientEntity(1L, "pperez", "pedro perez", "pperez@gmail.com", "123345123");
        when(clientRepository.save(entity)).thenThrow(DatabaseException.class);

        var exception =
                Assertions.assertThrows(DatabaseException.class, () -> iClientService.save(entity));

        Assertions.assertEquals("Error en banco de datos", exception.getMessage());
        verify(clientRepository).save(entity);
    }

    @Test
    void findAll() {
        when(iClientService.findAll())
                .thenReturn(Arrays.asList(new ClientEntity(), new ClientEntity()));

        var result = iClientService.findAll();

        Assertions.assertEquals(2, result.size());
        verify(clientRepository).findAll();
    }

    @Test
    void findAll_throw() {
        when(iClientService.findAll())
                .thenThrow(DatabaseException.class);

        var exception  =
                Assertions.assertThrows(DatabaseException.class, () -> iClientService.findAll());

        Assertions.assertEquals("Error en banco de datos", exception.getMessage());
        verify(clientRepository).findAll();
    }
}