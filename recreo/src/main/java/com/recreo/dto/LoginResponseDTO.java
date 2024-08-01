package com.recreo.dto;

import com.recreo.enums.DocumentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String profile;
    private String email;
    private Date birthDate;
    private String documentValue;
    private DocumentTypeEnum documentType;
    private String userArea;
    private String jobPosition;
    private Boolean isCredentialTemporary;
    // TODO: Add Municipality
    private String token;
    private String refreshToken;
    private long expirationTime;
}

