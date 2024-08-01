package com.recreo.repositories;

import com.recreo.entities.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<RevokedToken, Long> {
    boolean existsByToken(String token);
}
