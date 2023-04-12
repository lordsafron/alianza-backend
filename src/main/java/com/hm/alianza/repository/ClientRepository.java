package com.hm.alianza.repository;

import com.hm.alianza.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findBySharedIdIgnoreCase(String sharedId);

}
