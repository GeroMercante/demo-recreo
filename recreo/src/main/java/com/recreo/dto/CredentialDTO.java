package com.recreo.dto;

import com.recreo.enums.CredentialTypeEnum;
import lombok.*;

@Getter
@Setter
public class CredentialDTO extends BaseDTO {
    private String password;
    private CredentialTypeEnum typeCredential;
    private String externalReference;
    private Boolean isVerified;
    private Boolean isTemporary;
}
