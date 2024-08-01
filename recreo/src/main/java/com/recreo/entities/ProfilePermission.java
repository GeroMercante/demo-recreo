package com.recreo.entities;

import com.recreo.enums.PermissionsEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "profiles_permissions")
public class ProfilePermission extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "profiles_id")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_code")
    private PermissionsEnum permissionCode;
}