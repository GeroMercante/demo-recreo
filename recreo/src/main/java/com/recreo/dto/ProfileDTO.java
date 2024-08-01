package com.recreo.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO extends BaseDTO {
    private String name;
    private List<String> permissions;
}
