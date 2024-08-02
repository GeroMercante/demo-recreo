package com.recreo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    private List<ProfilePermission> profilePermissions;

    public Profile() {
        this.profilePermissions = new ArrayList<>();
    }

    public List<String> getPermissionCodes() {
        List<String> list = new ArrayList<>();
        for (ProfilePermission pp : this.getProfilePermissions()) {
            String code = pp.getPermissionCode().name();
            list.add(code);
        }
        return list;
    }
}
