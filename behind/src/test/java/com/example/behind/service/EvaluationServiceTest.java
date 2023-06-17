//package com.example.behind.service;
//
//import com.example.behind.common.domain_attributes.EvaluationResult;
//import com.example.behind.common.domain_attributes.EvaluationState;
//import com.example.behind.domain.Account;
//import com.example.behind.domain.Evaluation;
//import com.example.behind.domain.User;
//import com.example.behind.domain.dto.UserCreateData;
//import com.example.behind.domain.dto.UserUpdateData;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class EvaluationServiceTest {
//    @Autowired
//    UserAccountService userAccountService;
//
//    @Autowired
//    EvaluationService evaluationService;
//
//    @Test
//    @Transactional
//    public void uploadEvaluationTest() {
//        // 创建用户
//        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
//        try {
//            User user = userAccountService.createUser(userCreateData);
//            Account account = user.getAccount();
//
//            // 创建评价
//            Evaluation evaluation = new Evaluation();
//            evaluation.setId(1L);
//            evaluation.setUserID(account.getUserID());
//            evaluation.setContent("This is a test evaluation.");
//            evaluation.setState(EvaluationState.Filled);
//            evaluation.setResult(EvaluationResult.Good);
//            evaluationService.uploadEvaluation(evaluation.getId(), evaluation.getContent(), evaluation.getResult());
//
//            // 获取用户的评价列表
//            List<Evaluation> evaluations = evaluationService.getUserEvaluation(account.getUserID());
//
//            // 验证评价列表是否不为空
//            assertNotNull(evaluations);
//            assertTrue(evaluations.size() > 0);
//
//            // 验证评价的内容和结果是否与上传时一致
//            Evaluation uploadedEvaluation = evaluations.get(0);
//            assertEquals(evaluation.getContent(), uploadedEvaluation.getContent());
//            assertEquals(evaluation.getResult(), uploadedEvaluation.getResult());
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//
//    @Test
//    @Transactional
//    public void getUserEvaluationTest() {
//        // 创建用户
//        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
//        try {
//            User user = userAccountService.createUser(userCreateData);
//            Account account = user.getAccount();
//
//            // 创建评价1
//            Evaluation evaluation1 = new Evaluation();
//            evaluation1.setUser(user);
//            evaluation1.setContent("Evaluation 1");
//            evaluation1.setState(EvaluationState.Approved);
//            evaluation1.setResult(EvaluationResult.Pass);
//            evaluationService.uploadEvaluation(evaluation1.getId(), evaluation1.getContent(), evaluation1.getResult());
//
//            // 创建评价2
//            Evaluation evaluation2 = new Evaluation();
//            evaluation2.setUser(user);
//            evaluation2.setContent("Evaluation 2");
//            evaluation2.setState(EvaluationState.Pending);
//            evaluation2.setResult(EvaluationResult.Fail);
//            evaluationService.uploadEvaluation(evaluation2.getId(), evaluation2.getContent(), evaluation2.getResult());
//
//            // 获取用户的评价列表
//            List<Evaluation> evaluations = evaluationService.getUserEvaluation(account.getUserID());
//
//            // 验证评价列表是否不为空
//            assertNotNull(evaluations);
//            assertEquals(2, evaluations.size());
//
//            // 验证评价内容和结果是否与上传时一致
//            Evaluation userEvaluation1 = evaluations.get(0);
//            assertEquals(evaluation1.getContent(), userEvaluation1.getContent());
//            assertEquals(evaluation1.getResult(), userEvaluation1.getResult());
//
//            Evaluation userEvaluation2 = evaluations.get(1);
//            assertEquals(evaluation2.getContent(), userEvaluation2.getContent());
//            assertEquals(evaluation2.getResult(), userEvaluation2.getResult());
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//
//    @Test
//    @Transactional
//    public void getEvaluationByUserAndStateTest() {
//        // 创建用户
//        UserCreateData userCreateData = new UserCreateData("testUserID", "testPassword", "testStuNum", "testEmail", "testPhone", "testName");
//        try {
//            User user = userAccountService.createUser(userCreateData);
//            Account account = user.getAccount();
//
//            // 创建评价1
//            Evaluation evaluation1 = new Evaluation();
//            evaluation1.setUser(user);
//            evaluation1.setContent("Evaluation 1");
//            evaluation1.setState(EvaluationState.Approved);
//            evaluation1.setResult(EvaluationResult.Pass);
//            evaluationService.uploadEvaluation(evaluation1.getId(), evaluation1.getContent(), evaluation1.getResult());
//
//            // 创建评价2
//            Evaluation evaluation2 = new Evaluation();
//            evaluation2.setUser(user);
//            evaluation2.setContent("Evaluation 2");
//            evaluation2.setState(EvaluationState.Pending);
//            evaluation2.setResult(EvaluationResult.Fail);
//            evaluationService.uploadEvaluation(evaluation2.getId(), evaluation2.getContent(), evaluation2.getResult());
//
//            // 获取用户的通过状态的评价列表
//            List<Evaluation> approvedEvaluations = evaluationService.getEvaluationByUserAndState(account.getUserID(), EvaluationState.Approved);
//
//            // 验证评价列表是否不为空
//            assertNotNull(approvedEvaluations);
//            assertEquals(1, approvedEvaluations.size());
//
//            // 验证评价内容和结果是否与上传时一致
//            Evaluation userEvaluation1 = approvedEvaluations.get(0);
//            assertEquals(evaluation1.getContent(), userEvaluation1.getContent());
//            assertEquals(evaluation1.getResult(), userEvaluation1.getResult());
//
//            // 获取用户的待处理状态的评价列表
//            List<Evaluation> pendingEvaluations = evaluationService.getEvaluationByUserAndState(account.getUserID(), EvaluationState.Pending);
//
//            // 验证评价列表是否不为空
//            assertNotNull(pendingEvaluations);
//            assertEquals(1, pendingEvaluations.size());
//
//            // 验证评价内容和结果是否与上传时一致
//            Evaluation userEvaluation2 = pendingEvaluations.get(0);
//            assertEquals(evaluation2.getContent(), userEvaluation2.getContent());
//            assertEquals(evaluation2.getResult(), userEvaluation2.getResult());
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//
//}
