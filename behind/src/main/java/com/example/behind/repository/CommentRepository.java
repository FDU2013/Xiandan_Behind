package com.example.behind.repository;

import com.example.behind.domain.Comment;
import com.example.behind.domain.XDanPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(XDanPost post);
}
