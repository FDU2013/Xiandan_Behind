package com.example.behind.service;

import com.example.behind.common.domain_attributes.EvaluationResult;
import com.example.behind.common.domain_attributes.EvaluationState;
import com.example.behind.domain.Evaluation;

import java.util.List;

public interface EvaluationService {
    List<Evaluation> getEvaluationByUserAndState(String userID, EvaluationState state);
    List<Evaluation> getUserEvaluation(String userID);
    void uploadEvaluation(Long evalId, String content, EvaluationResult result) throws Exception;
}
