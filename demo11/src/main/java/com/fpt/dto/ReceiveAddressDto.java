package com.fpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveAddressDto {
    private Long id;
    private Long user_id;
    private String name;
    private String phone;
    private String address;
    private boolean defaultaddress;
}
