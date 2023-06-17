package com.example.behind.controller;

import com.example.behind.common.Result;
import com.example.behind.domain.User;
import com.example.behind.domain.dto.UserUpdateData;
import com.example.behind.domain.vo.ChangePasswordVO;
import com.example.behind.domain.vo.UserInfoRetVO;
import com.example.behind.domain.vo.UserUpdateVO;
import com.example.behind.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    UserAccountService userAccountService;

    @PostMapping("/info")
    public Result info(HttpServletRequest request) {
        UserInfoRetVO userInfoRetVO = new UserInfoRetVO();
        try {
            String userID = request.getHeader("userID");
            User user = userAccountService.getUser(userID);
            userInfoRetVO.setStuNum(user.getAccount().getStuNum());
            userInfoRetVO.setPhone(user.getPhone());
            userInfoRetVO.setName(user.getName());
            userInfoRetVO.setUserID(user.getAccount().getUserID());
            userInfoRetVO.setEmail(user.getEmail());
            userInfoRetVO.setCreditScore(user.getCredit());
            userInfoRetVO.setTotalPosts(user.getPostNum());
            userInfoRetVO.setTotalTrades(user.getTradeNum());
            userInfoRetVO.setAvatar(user.getHeadImgUrl());
        } catch (Exception e) {
            return Result.fail(662, e.getMessage());
        }
        return Result.succ(userInfoRetVO);
    }


    @PostMapping("/otherUserInfo")
    public Result otherUserInfo(@RequestBody String userID) {
        userID = userID.replaceAll("\"","");
        UserInfoRetVO userInfoRetVO = new UserInfoRetVO();
        try {
            User user = userAccountService.getUser(userID);
            userInfoRetVO.setStuNum("****");
            userInfoRetVO.setName(user.getName());
            userInfoRetVO.setUserID(user.getAccount().getUserID());
            userInfoRetVO.setCreditScore(user.getCredit());
            userInfoRetVO.setTotalPosts(user.getPostNum());
            userInfoRetVO.setTotalTrades(user.getTradeNum());
            userInfoRetVO.setAvatar(user.getHeadImgUrl());
            userInfoRetVO.setPhone("****");
            userInfoRetVO.setEmail("****");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(662, e.getMessage());
        }
        return Result.succ(userInfoRetVO);
    }

    @PostMapping("/changePassword")
    public Result changePassword(HttpServletRequest request, @RequestBody ChangePasswordVO changePasswordVO) {
        Map<String,Object> map = new HashMap<>();
        try {
            String userID = request.getHeader("userID");
            userAccountService.changePassword(userID, changePasswordVO.getOldPassword(), changePasswordVO.getNewPassword());
            map.put("oldPassword", changePasswordVO.getOldPassword());
            map.put("newPassword", changePasswordVO.getNewPassword());
        } catch (Exception e) {
            return Result.fail(662, e.getMessage());
        }
        return Result.succ(map);
    }

    @PostMapping("/updateInfo")
    public Result updateInfo(HttpServletRequest request, @RequestBody UserUpdateVO userUpdateVO) {
        Map<String,Object> map = new HashMap<>();
        try {
            String userID = request.getHeader("userID");
            UserUpdateData userUpdateData = new UserUpdateData(userID, userUpdateVO.getEmail(), userUpdateVO.getPhone(), userUpdateVO.getName(), null, null, null,userUpdateVO.getAvatar());
            if (!userAccountService.updateUser(userUpdateData)) {
                return Result.fail(622, "更新失败");
            }
            else {
                return Result.succ(userUpdateVO);
            }
        } catch (Exception e) {
            return Result.fail(662, e.getMessage());
        }
    }

}
