package com.example.behind.service.impl;

import com.example.behind.domain.dto.UserCreateData;
import com.example.behind.domain.Account;
import com.example.behind.domain.User;
import com.example.behind.domain.dto.UserUpdateData;
import com.example.behind.repository.AccountRepository;
import com.example.behind.repository.UserRepository;
import com.example.behind.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    UserRepository userRepository;
    AccountRepository accountRepository;

    @Autowired
    public UserAccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUser(String userID) {
        return userRepository.findByAccount_UserID(userID);
    }

    @Override
    public Page<User> getAllUser() {
        return null;
    }

    @Override
    public boolean checkLogin(String userID, String password) throws Exception {
        Account account = accountRepository.findByUserID(userID);
        if(account == null){
            throw new Exception("用户名不存在");
        }
        return account.getPassword().equals(password);
    }

    @Override
    public User createUser(UserCreateData userData) throws Exception {
        Account account = accountRepository.findByUserID(userData.getUserID());
        if(account != null){
            throw new Exception("用户名已存在");
        }
        account = accountRepository.findByStuNum(userData.getStuNum());
        if(account != null){
            throw new Exception("学号已注册");
        }
        return userRepository.save(new User(userData));
    }

    @Override
    public boolean updateUser(UserUpdateData userData) throws Exception {
        User user = userRepository.findByAccount_UserID(userData.getUserID());
        if(user == null) {
            return false;
        }
        user.update(userData);
        userRepository.save(user);
        return true;
    }


    @Override
    public boolean changePassword(String userID, String oldPassword, String newPassword) throws Exception {
        Account account = accountRepository.findByUserID(userID);
        if(account == null){
            throw new Exception("userID不存在");
        }
        if(account.getPassword().equals(oldPassword)){
            account.setPassword(newPassword);
            accountRepository.save(account);
            return true;
        }
        return false;
    }
}
