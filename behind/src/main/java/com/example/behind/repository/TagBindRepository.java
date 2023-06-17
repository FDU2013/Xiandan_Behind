package com.example.behind.repository;

import com.example.behind.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagBindRepository extends JpaRepository<Tag, Long> {
}
