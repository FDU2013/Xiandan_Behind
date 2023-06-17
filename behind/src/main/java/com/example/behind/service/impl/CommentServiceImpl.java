package com.example.behind.service.impl;

import com.example.behind.domain.Comment;
import com.example.behind.domain.User;
import com.example.behind.domain.XDanPost;
import com.example.behind.repository.*;
import com.example.behind.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    XDanPostRepository xDanPostRepository;
    UserRepository userRepository;
    CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(XDanPostRepository xDanPostRepository,  UserRepository userRepository,
                              CommentRepository commentRepository){
        this.xDanPostRepository = xDanPostRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getCommentByPost(Long postId) throws Exception {
        XDanPost post = xDanPostRepository.findById(postId).orElse(null);
        if(post == null){
            throw new Exception("post不存在");
        }
        return commentRepository.findByPost(post);
    }

    @Override
    public Comment doComment(Long postId, String userID, String content) throws Exception {
        XDanPost post = xDanPostRepository.findById(postId).orElse(null);
        if(post == null){
            throw new Exception("post不存在");
        }
        User user = userRepository.findByAccount_UserID(userID);
        if(user == null){
            throw new Exception("user不存在");
        }
        return commentRepository.save(new Comment(0L, content, post, user, 0, new Date()));
    }
}
