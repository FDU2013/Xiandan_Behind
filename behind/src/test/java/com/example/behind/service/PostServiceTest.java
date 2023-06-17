package com.example.behind.service;

import com.example.behind.common.domain_attributes.*;
import com.example.behind.common.MyPage;
import com.example.behind.domain.Account;
import com.example.behind.domain.TradeRecord;
import com.example.behind.domain.User;
import com.example.behind.domain.XDanPost;
import com.example.behind.domain.dto.PostCreateData;
import com.example.behind.domain.dto.PostSearchData;
import com.example.behind.domain.dto.PostUpdateData;
import com.example.behind.domain.dto.UserCreateData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    UserAccountService userAccountService;

    @Test
    @Transactional
    public void getPostTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost createdPost = postService.createPost(postCreateData);
            Long postId = createdPost.getId();

            // 根据帖子ID获取帖子
            XDanPost retrievedPost = postService.getPost(postId);

            // 验证获取的帖子是否与创建的帖子一致
            assertNotNull(retrievedPost);
            assertEquals("Open", retrievedPost.getState().toString());
            assertEquals(account.getUserID(), retrievedPost.getUser().getAccount().getUserID());
            assertEquals("testTitle", retrievedPost.getTitle());
            assertEquals("testContent", retrievedPost.getContent());
            assertEquals(100, retrievedPost.getPrice());
            assertEquals(GoodsType.Study, retrievedPost.getGoodsType());
            assertEquals(CampusEnum.Handan, retrievedPost.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    @Transactional
    public void getAllPostByUserTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 创建两个帖子
            PostCreateData post1CreateData = new PostCreateData(account.getUserID(), "testTitle1", "testContent1", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            PostCreateData post2CreateData = new PostCreateData(account.getUserID(), "testTitle2", "testContent2", 200, GoodsType.Live, CampusEnum.Fenglin, new ArrayList<>(), TradePostType.Sell, new ArrayList<>());

            XDanPost post1 = postService.createPost(post1CreateData);
            XDanPost post2 = postService.createPost(post2CreateData);

            // 获取用户的所有帖子
            MyPage<XDanPost> userPosts = postService.getAllPostByUser(account.getUserID(), 10, 1);

            // 验证返回的帖子列表是否包含创建的两个帖子
            assertNotNull(userPosts);
            assertEquals(2, userPosts.getTotal());
            assertTrue(userPosts.getRecords().contains(post1));
            assertTrue(userPosts.getRecords().contains(post2));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void getPostByUserAndStateTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 创建两个帖子
            PostCreateData post1CreateData = new PostCreateData(account.getUserID(), "testTitle1", "testContent1", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            PostCreateData post2CreateData = new PostCreateData(account.getUserID(), "testTitle2", "testContent2", 200, GoodsType.Live, CampusEnum.Fenglin, new ArrayList<>(), TradePostType.Sell, new ArrayList<>());

            XDanPost post1 = postService.createPost(post1CreateData);
            XDanPost post2 = postService.createPost(post2CreateData);

            // 获取用户的Open状态的帖子
            MyPage<XDanPost> userOpenPosts = postService.getPostByUserAndState(account.getUserID(), PostState.Open, 10, 1);

            // 验证返回的帖子列表是否包含Open状态的帖子
            assertNotNull(userOpenPosts);
            assertEquals(2, userOpenPosts.getTotal());
            assertTrue(userOpenPosts.getRecords().contains(post1));
            assertTrue(userOpenPosts.getRecords().contains(post2));

            // 将帖子1关闭
            postService.closePost(post1.getId());

            // 获取用户的Closed状态的帖子
            MyPage<XDanPost> userClosedPosts = postService.getPostByUserAndState(account.getUserID(), PostState.Close, 10, 1);

            // 验证返回的帖子列表是否包含Closed状态的帖子
            assertNotNull(userClosedPosts);
            assertEquals(1, userClosedPosts.getTotal());
            assertTrue(userClosedPosts.getRecords().contains(post1));
            assertFalse(userClosedPosts.getRecords().contains(post2));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void createPostTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost createdPost = postService.createPost(postCreateData);

            // 验证创建的帖子
            assertNotNull(createdPost);
            assertEquals("Open", createdPost.getState().toString());
            assertEquals(account.getUserID(), createdPost.getUser().getAccount().getUserID());
            assertEquals("testTitle", createdPost.getTitle());
            assertEquals("testContent", createdPost.getContent());
            assertEquals(100, createdPost.getPrice());
            assertEquals(GoodsType.Study, createdPost.getGoodsType());
            assertEquals(CampusEnum.Handan, createdPost.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void closePostTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);

            // 将帖子关闭
            postService.closePost(post.getId());

            // 获取关闭状态的帖子
            XDanPost closedPost = postService.getPost(post.getId());

            // 验证帖子的状态是否为 Closed
            assertNotNull(closedPost);
            assertEquals(PostState.Close, closedPost.getState());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void openPostTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);

            // 将帖子关闭
            postService.closePost(post.getId());

            // 将帖子重新开启
            postService.openPost(post.getId());

            // 获取重新开启后的帖子
            XDanPost openedPost = postService.getPost(post.getId());

            // 验证帖子的状态是否为 Open
            assertNotNull(openedPost);
            assertEquals(PostState.Open, openedPost.getState());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void searchPostTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 创建帖子1
            PostCreateData postCreateData1 = new PostCreateData(account.getUserID(), "testTitle1", "testContent1", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            postService.createPost(postCreateData1);

            // 创建帖子2
            PostCreateData postCreateData2 = new PostCreateData(account.getUserID(), "testTitle2", "testContent2", 200, GoodsType.Live, CampusEnum.Fenglin, new ArrayList<>(), TradePostType.Sell, new ArrayList<>());
            postService.createPost(postCreateData2);

            // 设置搜索条件
            PostSearchData searchData = new PostSearchData(
                    "testTitle",
                    null,
                    0,
                    -1,
                    GoodsType.Study,
                    null,
                    CampusEnum.All
            );

            // 执行搜索
            MyPage<XDanPost> posts = postService.searchPost(searchData, 10, 1);

            // 验证搜索结果是否符合预期
            assertNotNull(posts);
            assertEquals(1, posts.getTotal());
            assertEquals(1, posts.getRecords().size());
            XDanPost post = posts.getRecords().get(0);
            assertEquals("testTitle1", post.getTitle());
            assertEquals("testContent1", post.getContent());
            assertEquals(100, post.getPrice());
            assertEquals(GoodsType.Study, post.getGoodsType());
            assertEquals(CampusEnum.Handan, post.getAddress());
            assertEquals(TradePostType.Buy, post.getType());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void updatePostTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);

            // 更新帖子信息
            PostUpdateData postUpdateData = new PostUpdateData();
            postUpdateData.setId(post.getId());
            postUpdateData.setTitle("UpdatedTitle");
            postUpdateData.setContent("UpdatedContent");
            postUpdateData.setPrice(200);
            postUpdateData.setGoodsType(GoodsType.Live);
            postUpdateData.setAddress(CampusEnum.Fenglin);

            boolean result = postService.updatePost(postUpdateData);

            // 验证更新结果是否为true
            assertTrue(result);

            // 获取更新后的帖子信息
            XDanPost updatedPost = postService.getPost(post.getId());

            // 验证帖子的信息是否已更新
            assertNotNull(updatedPost);
            assertEquals("UpdatedTitle", updatedPost.getTitle());
            assertEquals("UpdatedContent", updatedPost.getContent());
            assertEquals(200, updatedPost.getPrice());
            assertEquals(GoodsType.Live, updatedPost.getGoodsType());
            assertEquals(CampusEnum.Fenglin, updatedPost.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void getRecordTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        UserCreateData userCreateData2 = new UserCreateData("applicantUserID", "applicantPassword", "StuNum", "applicantEmail", "applicantPhone", "applicantName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            userAccountService.createUser(userCreateData2);

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);

            // 申请交易记录
            String applicantID = "applicantUserID";
            Long postId = post.getId();
            String message = "Test message";
            TradeRecord tradeRecord = postService.applyRecord(applicantID, postId, message);

            // 获取交易记录
            Long recordId = tradeRecord.getId();
            TradeRecord retrievedRecord = postService.getRecord(recordId);

            // 验证返回的交易记录是否与申请时的交易记录一致
            assertNotNull(retrievedRecord);
            assertEquals(tradeRecord.getId(), retrievedRecord.getId());
            assertEquals(tradeRecord.getApplicant(), retrievedRecord.getApplicant());
            assertEquals(tradeRecord.getPost().getId(), retrievedRecord.getPost().getId());
            assertEquals(tradeRecord.getPostMsg(), retrievedRecord.getPostMsg());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    @Transactional
    public void applyRecordTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        UserCreateData userCreateData2 = new UserCreateData("applicantUserID", "applicantPassword", "StuNum", "applicantEmail", "applicantPhone", "applicantName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            userAccountService.createUser(userCreateData2);

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);

            // 申请交易记录
            String applicantID = "applicantUserID";
            Long postId = post.getId();
            String message = "Test message";
            TradeRecord tradeRecord = postService.applyRecord(applicantID, postId, message);

            // 验证交易记录的信息是否符合预期
            assertNotNull(tradeRecord);
            assertEquals(applicantID, tradeRecord.getApplicant());
            assertEquals(postId, tradeRecord.getPost().getId());
            assertEquals(message, tradeRecord.getPostMsg());
            assertEquals(RecordState.Posted, tradeRecord.getState());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void agreeRecordTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        UserCreateData userCreateData2 = new UserCreateData("applicantUserID", "applicantPassword", "StuNum", "applicantEmail", "applicantPhone", "applicantName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            userAccountService.createUser(userCreateData2);

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);

            // 申请交易记录
            String applicantID = "applicantUserID";
            Long postId = post.getId();
            String message = "Test message";
            TradeRecord tradeRecord = postService.applyRecord(applicantID, postId, message);

            // 同意交易记录
            postService.agreeRecord(tradeRecord.getId());

            // 获取更新后的交易记录信息
            TradeRecord updatedRecord = postService.getRecord(tradeRecord.getId());

            // 验证交易记录的状态是否已更新为Agreed
            assertNotNull(updatedRecord);
            assertEquals(RecordState.Passed, updatedRecord.getState());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void refuseRecordTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        UserCreateData userCreateData2 = new UserCreateData("applicantUserID", "applicantPassword", "StuNum", "applicantEmail", "applicantPhone", "applicantName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();

            userAccountService.createUser(userCreateData2);

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);

            // 申请交易记录
            String applicantID = "applicantUserID";
            Long postId = post.getId();
            String message = "Test message";
            TradeRecord tradeRecord = postService.applyRecord(applicantID, postId, message);

            // 拒绝交易记录
            postService.refuseRecord(tradeRecord.getId());

            // 获取更新后的交易记录信息
            TradeRecord updatedRecord = postService.getRecord(tradeRecord.getId());

            // 验证交易记录的状态是否已更新为Refused
            assertNotNull(updatedRecord);
            assertEquals(RecordState.Refused, updatedRecord.getState());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void cancelRecordApplicationTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        UserCreateData userCreateData2 = new UserCreateData("applicantUserID", "applicantPassword", "StuNum", "applicantEmail", "applicantPhone", "applicantName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();
            userAccountService.createUser(userCreateData2);

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);

            // 申请交易记录
            String applicantID = "applicantUserID";
            Long postId = post.getId();
            String message = "Test message";
            TradeRecord tradeRecord = postService.applyRecord(applicantID, postId, message);

            // 取消交易记录申请
            postService.cancelRecordApplication(tradeRecord.getId());

            // 获取更新后的交易记录信息
            TradeRecord updatedRecord = postService.getRecord(tradeRecord.getId());

            // 验证交易记录的状态是否已更新为Canceled
            assertNotNull(updatedRecord);
            assertEquals(RecordState.Cancelled, updatedRecord.getState());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void getIssuedRecordApplicationByUserAndStateTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        UserCreateData userCreateData2 = new UserCreateData("applicantUserID", "applicantPassword", "StuNum", "applicantEmail", "applicantPhone", "applicantName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();
            userAccountService.createUser(userCreateData2);

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);
            PostCreateData postCreateData2 = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Live, CampusEnum.Fenglin, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post2 = postService.createPost(postCreateData);


            // 申请交易记录
            String applicantID = "applicantUserID";
            Long postId = post.getId();
            Long postId2 = post2.getId();
            String message = "Test message";
            TradeRecord tradeRecord1 = postService.applyRecord(applicantID, postId, message);
            TradeRecord tradeRecord2 = postService.applyRecord(applicantID, postId2, message);

            // 获取用户已发出的所有交易记录申请（状态为Posted）
            List<TradeRecord> issuedRecords = postService.getIssuedRecordApplicationByUserAndState(applicantID, RecordState.Posted);

            // 验证返回的交易记录列表是否包含已发出的交易记录申请
            assertNotNull(issuedRecords);
            assertTrue(issuedRecords.contains(tradeRecord1));
            assertTrue(issuedRecords.contains(tradeRecord2));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Transactional
    public void getReceivedRecordApplicationByUserAndStateTest() {
        // 创建用户
        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
        UserCreateData userCreateData2 = new UserCreateData("applicantUserID", "applicantPassword", "StuNum", "applicantEmail", "applicantPhone", "applicantName");
        try {
            User user = userAccountService.createUser(userCreateData);
            Account account = user.getAccount();
            userAccountService.createUser(userCreateData2);

            // 创建帖子
            PostCreateData postCreateData = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Study, CampusEnum.Handan, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post = postService.createPost(postCreateData);
            PostCreateData postCreateData2 = new PostCreateData(account.getUserID(), "testTitle", "testContent", 100, GoodsType.Live, CampusEnum.Fenglin, new ArrayList<>(), TradePostType.Buy, new ArrayList<>());
            XDanPost post2 = postService.createPost(postCreateData);

            // 申请交易记录
            String applicantID = "applicantID";
            Long postId = post.getId();
            Long postId2 = post2.getId();
            String message = "Test message";
            TradeRecord tradeRecord1 = postService.applyRecord(applicantID, postId, message);
            TradeRecord tradeRecord2 = postService.applyRecord(applicantID, postId2, message);

            // 获取用户已收到的所有交易记录申请（状态为Posted）
            List<TradeRecord> receivedRecords = postService.getReceivedRecordApplicationByUserAndState(account.getUserID(), RecordState.Posted);

            // 验证返回的交易记录列表是否包含已收到的交易记录申请
            assertNotNull(receivedRecords);
            assertTrue(receivedRecords.contains(tradeRecord1));
            assertTrue(receivedRecords.contains(tradeRecord2));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}


