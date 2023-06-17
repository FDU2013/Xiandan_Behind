package com.example.behind.repository;

import com.example.behind.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByAccount_UserID(String account_UserID);
}
