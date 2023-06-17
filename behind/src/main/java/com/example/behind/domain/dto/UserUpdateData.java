package com.example.behind.domain.dto;

import com.example.behind.common.domain_attributes.CampusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateData {
    String userID;
    String email;
    String phone;
    String name;
    CampusEnum campus;
    String school;
    String major;
    String headImgUrl;
}
