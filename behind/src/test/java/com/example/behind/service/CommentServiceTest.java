package com.example.behind.service;

import com.example.behind.common.domain_attributes.CampusEnum;
import com.example.behind.common.domain_attributes.GoodsType;
import com.example.behind.common.domain_attributes.TradePostType;
import com.example.behind.domain.Account;
import com.example.behind.domain.Comment;
import com.example.behind.domain.User;
import com.example.behind.domain.XDanPost;
import com.example.behind.domain.dto.PostCreateData;
import com.example.behind.domain.dto.UserCreateData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserAccountService userAccountService;

    @Test
    @Transactional
    public void doCommentTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);

            // 进行评论
            Long postId = post.getId();
            String userID = user.getAccount().getUserID();
            String content = "This is a test comment.";
            Comment comment = commentService.doComment(postId, userID, content);

            // 验证评论是否创建成功
            assertNotNull(comment);
            assertEquals(postId, comment.getPost().getId());
            assertEquals(userID, comment.getUser().getAccount().getUserID());
            assertEquals(content, comment.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void getCommentByPostTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);

            // 进行评论
            Long postId = post.getId();
            String userID = user.getAccount().getUserID();
            String content = "This is a test comment.";
            commentService.doComment(postId, userID, content);

            // 获取评论列表
            List<Comment> comments = commentService.getCommentByPost(postId);

            // 验证评论列表是否不为空
            assertNotNull(comments);
            assertTrue(comments.size() > 0);
            assertEquals(content, comments.get(0).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}

