package com.recreo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTemporaryPasswordDTO extends LoginRequestDTO {
    private String newPassword;
}
