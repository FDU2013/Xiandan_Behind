package com.example.behind.repository;

import com.example.behind.domain.CreditRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRecordRepository extends JpaRepository<CreditRecord, Long> {
    CreditRecord findByAppraiserAndTargetUser(String appraiser, String targetUser);
}
