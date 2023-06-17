package com.example.behind.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateData {
    String userID;
    String password;
    String stuNum;
    String email;
    String phone;
    String name;
//    CampusEnum campus;
//    String school;
//    String major;
}
