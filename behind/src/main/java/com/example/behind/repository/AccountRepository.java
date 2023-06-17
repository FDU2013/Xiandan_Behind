package com.example.behind.repository;

import com.example.behind.domain.Account;
import com.example.behind.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUserID(String userID);
    Account findByStuNum(String stuNum);
}
