package com.example.behind.service.impl;

import com.example.behind.common.domain_attributes.EvaluationResult;
import com.example.behind.common.domain_attributes.EvaluationState;
import com.example.behind.domain.*;
import com.example.behind.repository.*;
import com.example.behind.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    TradeRecordRepository tradeRecordRepository;
    UserRepository userRepository;
    EvaluationRepository evaluationRepository;
    CreditRecordRepository creditRecordRepository;

    @Autowired
    public EvaluationServiceImpl(TradeRecordRepository tradeRecordRepository, UserRepository userRepository,
                                 EvaluationRepository evaluationRepository, CreditRecordRepository creditRecordRepository){
        this.tradeRecordRepository = tradeRecordRepository;
        this.userRepository = userRepository;
        this.evaluationRepository = evaluationRepository;
        this.creditRecordRepository = creditRecordRepository;
    }
    @Override
    public List<Evaluation> getEvaluationByUserAndState(String userID, EvaluationState state) {
        return evaluationRepository.findByUserIDAndState(userID, state);
    }

    @Override
    public List<Evaluation> getUserEvaluation(String userID) {
        List<Evaluation> evaluations =
                evaluationRepository.findByTrade_Post_User_Account_UserIDOrTrade_Applicant(userID, userID);
        List<Evaluation> res = new ArrayList<>();
        for(Evaluation eval : evaluations){
            if(userID.equals(eval.getBeEvaluatedUserID()) &&
                    eval.getState() == EvaluationState.Filled){
                res.add(eval);
            }
        }
        return res;
    }

    @Override
    public void uploadEvaluation(Long evalId, String content, EvaluationResult result) throws Exception {
        Evaluation evaluation = evaluationRepository.findById(evalId).orElse(null);
        if(evaluation == null){
            throw new Exception("evaluation不存在");
        }
        if(evaluation.getState() == EvaluationState.Filled){
            throw new Exception("已评价");
        }
        evaluation.setResult(result);
        evaluation.setContent(content);
        evaluation.setState(EvaluationState.Filled);
        finishEvaluationEvent(evaluation);
    }

    private void finishEvaluationEvent(Evaluation evaluation){
        TradeRecord record = evaluation.getTrade();
        String appraiserID = record.getApplicant();
        String targetUserID =  evaluation.getBeEvaluatedUserID();
        User targetUser = userRepository.findByAccount_UserID(targetUserID);
        CreditRecord creditRecord = creditRecordRepository.findByAppraiserAndTargetUser(appraiserID, targetUserID);
        if(creditRecord == null){
            creditRecord = creditRecordRepository.save(new CreditRecord(0L, appraiserID, targetUserID, 0 , 0));
        }
        int change;
        if(evaluation.getResult() == EvaluationResult.Good){
            if(creditRecord.getAdd() < User.creditChangeRate){
                change = targetUser.addCredit();
            } else {
                change = targetUser.addCredit(User.lowCreditChangeRate);
            }
            int now = creditRecord.getAdd() + change;
            int above = now - User.oneUserChangeMax;
            if(above > 0){
                targetUser.subtractCredit(above);
                creditRecord.setAdd(User.oneUserChangeMax);
            } else {
                creditRecord.setAdd(now);
            }
        } else {
            if(creditRecord.getSub() < User.creditChangeRate){
                change = targetUser.subtractCredit();
            } else {
                change = targetUser.subtractCredit(User.lowCreditChangeRate);
            }
            int now = creditRecord.getSub() + change;
            int above = now - User.oneUserChangeMax;
            if(above > 0){
                targetUser.addCredit(above);
                creditRecord.setSub(User.oneUserChangeMax);
            } else {
                creditRecord.setSub(now);
            }
        }
        creditRecordRepository.save(creditRecord);
        userRepository.save(targetUser);
        evaluationRepository.save(evaluation);
    }
}
