package com.fpt.dto;

import com.fpt.model.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String phone;
    private Date dateOfBirth;
    private String username;
    private String password;
    private String image;
    private String email;
    private String userRole;
    private String address;
}
