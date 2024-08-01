package com.recreo.dto;

import com.recreo.enums.DocumentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseDTO {
    private String email;
    private String firstName;
    private String lastName;
    private DocumentTypeEnum documentType;
    private String documentValue;
    private Boolean active;
    private String profile;
    private String position;
    private String userArea;
}
