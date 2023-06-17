package com.example.behind.common;

import com.example.behind.common.domain_attributes.RoleType;
import com.example.behind.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserData {

    String number;
    String role;

    public static JwtUserData accountToJwt(User userAccount){
        return new JwtUserData(userAccount.getAccount().getUserID(), userAccount.getAccount().getRole()== RoleType.User?"User":"Admin");
    }
}
