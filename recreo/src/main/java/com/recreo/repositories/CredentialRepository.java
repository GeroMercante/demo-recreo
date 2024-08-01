package com.recreo.repositories;

import com.recreo.entities.Credential;
import com.recreo.enums.CredentialTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findByUser_Email(String email);
    Optional<Credential> findByUserIdAndTypeCredential(Long id, CredentialTypeEnum typeCredential);
}
