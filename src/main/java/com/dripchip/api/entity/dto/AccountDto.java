package com.dripchip.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
