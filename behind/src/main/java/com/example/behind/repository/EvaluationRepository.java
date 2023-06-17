package com.example.behind.repository;

import com.example.behind.common.domain_attributes.EvaluationState;
import com.example.behind.domain.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByUserIDAndState(String userID, EvaluationState state);
    List<Evaluation> findByTrade_Post_User_Account_UserIDOrTrade_Applicant(String userID, String applicant);

}
