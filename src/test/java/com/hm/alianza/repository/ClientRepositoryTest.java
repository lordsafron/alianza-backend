package com.hm.alianza.repository;

import com.hm.alianza.config.DefaultJpaTestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = DefaultJpaTestConfiguration.class)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @Sql(scripts = "/insert_client.sql")
    void findBySharedIdIgnoreCase() {
        var sharedId = "pperez";

        var result = clientRepository.findBySharedIdIgnoreCase(sharedId);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @Sql(scripts = "/insert_clients.sql")
    void findAll() {

        var result = clientRepository.findAll();

        Assertions.assertEquals(2, result.size());
    }
}