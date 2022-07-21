package com.example.firmaplatform.DTO;

import lombok.Data;

@Data
public class StaffDTO {
    private Integer staffId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Integer typeRole;
}
