package com.example.behind.controller;


import com.example.behind.common.JwtUserData;
import com.example.behind.common.Result;
import com.example.behind.domain.User;
import com.example.behind.domain.dto.LoginData;
import com.example.behind.domain.dto.UserCreateData;
import com.example.behind.service.UserAccountService;
import com.example.behind.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserAccountService userAccountService;

    @PostMapping("/register")
    public Result register(@RequestBody UserCreateData userCreateData){
        try {
            userAccountService.createUser(userCreateData);
            return Result.succ(userCreateData);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(622, e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginData loginData){
        Map<String,Object> map = new HashMap<>();
        try {
            if (!userAccountService.checkLogin(loginData.getUserID(), loginData.getPassword())) {
                return Result.fail(622, "登录失败");
            }
            else {
                User user = userAccountService.getUser(loginData.getUserID());
                JwtUserData jwtUserData = JwtUserData.accountToJwt(user);
                String token = JwtUtil.createToken(jwtUserData);
                map.put("userID", loginData.getUserID());
                map.put("role", user.getAccount().getRole().toString());
                map.put("token", token);
                return Result.succ(map);
            }
        }
        catch (Exception e) {
            return Result.fail(622, e.getMessage());
        }
    }
}
