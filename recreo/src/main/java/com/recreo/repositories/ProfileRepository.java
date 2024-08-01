package com.recreo.repositories;

import com.recreo.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    @Query("SELECT p.id FROM Profile p WHERE LOWER(p.name) = LOWER(:profileName)")
    Long findProfileIdByName(@Param("profileName") String profileName);
    Optional<Profile> findByName(String name);
}
