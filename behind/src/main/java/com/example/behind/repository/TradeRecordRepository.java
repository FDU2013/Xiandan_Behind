package com.example.behind.repository;

import com.example.behind.common.domain_attributes.RecordState;
import com.example.behind.domain.TradeRecord;
import com.example.behind.domain.XDanPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRecordRepository extends JpaRepository<TradeRecord, Long> {
    List<TradeRecord> findByPostAndState(XDanPost post, RecordState state);
    List<TradeRecord> findByPostInAndState(List<XDanPost> posts, RecordState state);
    List<TradeRecord> findByApplicantAndState(String applicant, RecordState state);
    //List<TradeRecord>findByApplicantAndStateIgnoreCase(String applicant, RecordState state);
}
