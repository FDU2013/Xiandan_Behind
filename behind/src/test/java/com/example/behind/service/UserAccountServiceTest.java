package com.example.behind.service;

import com.example.behind.domain.Account;
import com.example.behind.domain.User;
import com.example.behind.domain.dto.UserCreateData;
import com.example.behind.domain.dto.UserUpdateData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserAccountServiceTest {

    @Autowired
    UserAccountService userAccountService;

    @Test
    @Transactional
    public void createUserTest(){
        UserCreateData userCreateData = new UserCreateData("testUserID","testPasword","testStuNum","testEmail","testPhone","testName");
        try{
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();
            assertNotNull(account);
            assertEquals("User",account.getRole().toString());
            assertEquals("testUserID",account.getUserID());
            assertEquals("testPasword",account.getPassword());
            assertEquals("testStuNum",account.getStuNum());
            assertEquals("testEmail",user.getEmail());
            assertEquals("testPhone",user.getPhone());
            assertEquals("testName",user.getName());
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void checkLoginTest(){
        UserCreateData userCreateData = new UserCreateData("testUserID1","testPasword","testStuNum1","testEmail","testPhone","testName");
        try{
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();
            assertTrue(userAccountService.checkLogin("testUserID1","testPasword"));
            assertFalse(userAccountService.checkLogin("testUserID1","testPaswordError"));
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void updateUserTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 更新用户信息
            UserUpdateData userUpdateData = new UserUpdateData();
            userUpdateData.setUserID(account.getUserID());
            userUpdateData.setEmail("newEmail");
            userUpdateData.setPhone("newPhone");
            userUpdateData.setName("newName");

            // 执行更新操作
            boolean result = userAccountService.updateUser(userUpdateData);
            assertTrue(result);

            // 验证更新后的信息
            User updatedUser = userAccountService.getUser(account.getUserID());
            assertNotNull(updatedUser);
            assertEquals("newEmail", updatedUser.getEmail());
            assertEquals("newPhone", updatedUser.getPhone());
            assertEquals("newName", updatedUser.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void changePasswordTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 更改密码
            String oldPassword = "testPassword";
            String newPassword = "newPassword";

            // 执行密码更改操作
            boolean result = userAccountService.changePassword(account.getUserID(), oldPassword, newPassword);
            assertTrue(result);

            // 验证密码是否更改成功
            assertTrue(userAccountService.checkLogin(account.getUserID(), newPassword));
            assertFalse(userAccountService.checkLogin(account.getUserID(), oldPassword));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void getUser() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 根据用户ID获取用户
            User retrievedUser = userAccountService.getUser(account.getUserID());

            // 验证获取的用户是否与创建的用户一致
            assertNotNull(retrievedUser);
            assertEquals("testEmail", retrievedUser.getEmail());
            assertEquals("testPhone", retrievedUser.getPhone());
            assertEquals("testName", retrievedUser.getName());

            // 根据用户ID获取用户
            User retrievedUser2 = userAccountService.getUser(user.getId());

            // 验证获取的用户是否与创建的用户一致
            assertNotNull(retrievedUser2);
            assertEquals("testEmail", retrievedUser2.getEmail());
            assertEquals("testPhone", retrievedUser2.getPhone());
            assertEquals("testName", retrievedUser2.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


}
