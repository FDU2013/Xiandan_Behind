package com.example.behind.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRetVO {
    String stuNum;
    String phone;
    String name;
    String userID;
    String email;
    Integer totalPosts;
    Integer totalTrades;
    Integer creditScore;
    String avatar;
}
