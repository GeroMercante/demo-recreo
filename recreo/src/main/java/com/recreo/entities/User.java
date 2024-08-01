package com.recreo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.recreo.enums.DocumentTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "profiles_id")
    @JsonManagedReference
    private Profile profile;

    private String email;
    private Long municipalityId;
    private Boolean active;
    private String firstName;
    private String lastName;
    private String position;
    private String userArea;

    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private DocumentTypeEnum documentType;

    private String documentValue;

    private Date birthDate;
    private Boolean canParticipateInDrawsAndTrivia;

}
