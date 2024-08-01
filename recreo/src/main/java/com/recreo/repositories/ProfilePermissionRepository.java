package com.recreo.repositories;

import com.recreo.entities.ProfilePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePermissionRepository extends JpaRepository<ProfilePermission, Long> {
}
