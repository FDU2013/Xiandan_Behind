package com.example.behind.service;

import com.example.behind.common.MyPage;
import com.example.behind.common.domain_attributes.PostState;
import com.example.behind.common.domain_attributes.RecordState;
import com.example.behind.domain.TradeRecord;
import com.example.behind.domain.XDanPost;
import com.example.behind.domain.dto.PostCreateData;
import com.example.behind.domain.dto.PostSearchData;
import com.example.behind.domain.dto.PostUpdateData;

import java.util.List;

public interface PostService{
    XDanPost getPost(Long id);
    //禁用
    MyPage<XDanPost> getParticipatePostByUser(String userID, Integer pageSize, Integer pageNum);
    MyPage<XDanPost> getAllPostByUser(String userID, Integer pageSize, Integer pageNum) throws Exception;
    MyPage<XDanPost> getPostByUserAndState(String userID, PostState state, Integer pageSize, Integer pageNum) throws Exception;
    XDanPost createPost(PostCreateData postData) throws Exception;
    void closePost(Long postID) throws Exception;
    void openPost(Long postID) throws Exception;
    MyPage<XDanPost> searchPost(PostSearchData searchData, Integer pageSize, Integer pageNum);
    boolean updatePost(PostUpdateData postData);

    TradeRecord getRecord(Long id);

    TradeRecord applyRecord(String  applicantID, Long postId, String message) throws Exception;
    void agreeRecord(Long recordId) throws Exception;
    void refuseRecord(Long recordId) throws Exception;
    void cancelRecordApplication(Long recordId) throws Exception;
    List<TradeRecord> getIssuedRecordApplicationByUserAndState(String userID, RecordState state) throws Exception;
    List<TradeRecord> getReceivedRecordApplicationByUserAndState(String userID, RecordState state) throws Exception;
}
