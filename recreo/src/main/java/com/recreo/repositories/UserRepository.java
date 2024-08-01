package com.recreo.repositories;

import com.recreo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u " +
            "WHERE (:searchText IS NULL OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(u.documentValue) LIKE LOWER(CONCAT('%', :searchText, '%'))) " +
            "AND (:profileId IS NULL OR u.profile.id = :profileId)")
    Page<User> search(@Param("searchText") String searchText, @Param("profileId") Long profileId, Pageable pageable);

    Optional<User> findByEmail(String email);
}
