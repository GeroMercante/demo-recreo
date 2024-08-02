package com.recreo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTemporaryPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
