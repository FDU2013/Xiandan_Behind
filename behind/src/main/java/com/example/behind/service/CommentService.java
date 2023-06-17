package com.example.behind.service;

import com.example.behind.domain.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentByPost(Long postId) throws Exception;
    Comment doComment(Long postId, String userID, String content) throws Exception;
}
