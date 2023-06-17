package com.example.behind.service.impl;

import com.example.behind.common.*;
import com.example.behind.common.domain_attributes.*;
import com.example.behind.domain.*;
import com.example.behind.domain.dto.PostCreateData;
import com.example.behind.domain.dto.PostSearchData;
import com.example.behind.domain.dto.PostUpdateData;
import com.example.behind.repository.*;
import com.example.behind.service.PostService;
import com.example.behind.utils.MyPageTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    XDanPostRepository xDanPostRepository;
    TradeRecordRepository tradeRecordRepository;
    TagRepository tagRepository;
    TagBindRepository tagBindRepository;
    UserRepository userRepository;
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public PostServiceImpl(XDanPostRepository xDanPostRepository, TradeRecordRepository tradeRecordRepository,
                           TagRepository tagRepository, TagBindRepository tagBindRepository, UserRepository userRepository,
                           EvaluationRepository evaluationRepository){
        this.xDanPostRepository = xDanPostRepository;
        this.tradeRecordRepository = tradeRecordRepository;
        this.tagRepository = tagRepository;
        this.tagBindRepository = tagBindRepository;
        this.userRepository = userRepository;
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public XDanPost getPost(Long id) {
        return xDanPostRepository.findById(id).orElse(null);
    }

    @Override
    public MyPage<XDanPost> getParticipatePostByUser(String userID, Integer pageSize, Integer pageNum) {
//        List<TradeRecord> records = tradeRecordRepository.findByApplicantAndState(userID, R);
        return null;
    }

    @Override
    public MyPage<XDanPost> getAllPostByUser(String userID, Integer pageSize, Integer pageNum) throws Exception {
        User user = userRepository.findByAccount_UserID(userID);
        if(user == null){
            throw new Exception("userID不存在");
        }
        List<XDanPost> target = xDanPostRepository.findByUser(user);
        return MyPageTool.getPage(target, pageSize, pageNum);
    }

    @Override
    public MyPage<XDanPost> getPostByUserAndState(String userID, PostState state, Integer pageSize, Integer pageNum) throws Exception {
        User user = userRepository.findByAccount_UserID(userID);
        if(user == null){
            throw new Exception("userID不存在");
        }
        List<XDanPost> target = xDanPostRepository.findByUserAndState(user, state);
        return MyPageTool.getPage(target, pageSize, pageNum);
    }

    @Override
    public XDanPost createPost(PostCreateData postData) throws Exception {
        User user = userRepository.findByAccount_UserID(postData.getUserID());
        if(user == null){
            throw new Exception("用户不存在");
        }
        List<TagBind> tags = new ArrayList<>();
        for(String tag : postData.getTags()){
            if(tagRepository.findByName(tag) == null) {
                tagRepository.save(new Tag(0L, tag));
            }
            tags.add(new TagBind(0L, tag));
        }
        List<Image> images_url = new ArrayList<>();
        for(String image_url : postData.getImages_url()){
            images_url.add(new Image(0L, image_url));
        }

        XDanPost post = new XDanPost(0L, user, postData.getTitle(), postData.getContent(), null, postData.getPrice(),
                postData.getAddress(), postData.getGoodsType(), tags, PostState.Open, postData.getPostType(), images_url, new ArrayList<>());
        return createdPostEvent(post);
    }

    @Override
    public void closePost(Long postID) throws Exception {
        XDanPost post = getPost(postID);
        if(post == null){
            throw new Exception("帖子不存在");
        }
        PostState state = post.getState();
        if(state != PostState.Open){
            throw new Exception("close操作不合法");
        }
        post.setState(PostState.Close);
        xDanPostRepository.save(post);
    }

    @Override
    public void openPost(Long postID) throws Exception {
        XDanPost post = getPost(postID);
        if(post == null){
            throw new Exception("帖子不存在");
        }
        PostState state = post.getState();
        if(state != PostState.Close){
            throw new Exception("open操作不合法");
        }
        post.setState(PostState.Open);
        xDanPostRepository.save(post);
    }

    @Override
    public MyPage<XDanPost> searchPost(PostSearchData searchData, Integer pageSize, Integer pageNum) {
        List<XDanPost> posts;
        GoodsType goodsType = searchData.getGoodsType();
        TradePostType postType = searchData.getPostType();
        if(goodsType == null || goodsType.equals(GoodsType.Null)) {
            if(postType == null){
                posts = xDanPostRepository.findByState(PostState.Open);
            } else {
                posts = xDanPostRepository.findByTypeAndState(postType, PostState.Open);
            }
        } else {
            if (postType == null) {
                posts = xDanPostRepository.findByStateAndGoodsType(PostState.Open, goodsType);
            } else {
                posts = xDanPostRepository.findByTypeAndStateAndGoodsType(
                        postType, PostState.Open, goodsType);
            }
        }
        List<XDanPost> target = new ArrayList<>();

        if(searchData.getTags() == null || searchData.getTags().size() == 0){
            for(XDanPost post : posts){
                if (!judgeSearchAndPriceAndCampus(searchData, post)) continue;
                target.add(post);
            }
            //System.out.println(target.get(0).getTitle());
            Collections.reverse(target);
            return MyPageTool.getPage(target, pageSize, pageNum);
        }

        for(XDanPost post : posts){
            if (!judgeSearchAndPriceAndCampus(searchData, post)) continue;
            for(TagBind tagBind : post.getTags()){
                if(searchData.getTags().contains(tagBind.getTag())){
                    target.add(post);
                    break;
                }
            }
        }
        Collections.reverse(target);
        return MyPageTool.getPage(target, pageSize, pageNum);
    }

    private static boolean judgeSearchAndPriceAndCampus(PostSearchData searchData, XDanPost post) {
//        System.out.println(searchData.getSearch());
//        System.out.println(post.getTitle());
        if(searchData.getCampus() != null &&
                searchData.getCampus() != CampusEnum.All &&
                !searchData.getCampus().equals(post.getAddress())) {
            return false;
        }
        if(searchData.getSearch() != null &&
                !post.getTitle().contains(searchData.getSearch()) &&
                !post.getContent().contains(searchData.getSearch())) {
            return false;
        }

        int min = searchData.getPriceMin(), max = searchData.getPriceMax();
        if(min >= 0 && post.getPrice() < min) return false;
        if(max >= 0 && post.getPrice() > max) return false;

        return true;
    }

    @Override
    public boolean updatePost(PostUpdateData postData) {
        XDanPost post = xDanPostRepository.findById(postData.getId()).orElse(null);
        if(post == null){
            return false;
        }
        post.update(postData);
        xDanPostRepository.save(post);
        return true;
    }

    @Override
    public TradeRecord getRecord(Long id) {
        return tradeRecordRepository.findById(id).orElse(null);
    }

    @Override
    public TradeRecord applyRecord(String applicantID, Long postId, String message) throws Exception {
        XDanPost post = getPost(postId);
        if(post == null){
            throw new Exception("帖子不存在");
        }
        if(applicantID.equals(post.getUser().getAccount().getUserID())){
            throw new Exception("无法对自己的帖子提出申请");
        }
        List<TradeRecord> records = tradeRecordRepository.findByPostAndState(post, RecordState.Passed);
        if(records != null && records.size() > 0){
            throw new Exception("该帖子已有通过记录");
        }
        records = tradeRecordRepository.findByPostAndState(post, RecordState.Posted);
        for(TradeRecord record : records){
            if(record.getApplicant().equals(applicantID)){
                throw new Exception("已提交过申请");
            }
        }
//        TradeRecord record = new TradeRecord(null, post, applicantID, message, RecordState.Posted,
//                null, null, new Date(), null);
        TradeRecord record = new TradeRecord(null, post, applicantID, message, RecordState.Posted,
                  new Date(), null);
        return tradeRecordRepository.save(record);
    }

    @Override
    public void agreeRecord(Long recordId) throws Exception {
        TradeRecord record = tradeRecordRepository.findById(recordId).orElse(null);
        if(record == null){
            throw new Exception("记录不存在");
        }
        RecordState state = record.getState();
        if(state == RecordState.Passed){
            throw new Exception("已通过");
        }
        if(state == RecordState.Cancelled || state == RecordState.Refused){
            throw new Exception("error! 无法通过已取消/拒绝的申请");
        }
        record.setState(RecordState.Passed);
        record.setDealTime(new Date());
        Evaluation applicantEvaluation = new Evaluation(null, null, record.getApplicant(),
                null, EvaluationState.ToBeFilled, record);
        Evaluation posterEvaluation = new Evaluation(null, null,
                record.getPost().getUser().getAccount().getUserID(), null,
                EvaluationState.ToBeFilled, record);
        evaluationRepository.save(applicantEvaluation);
        evaluationRepository.save(posterEvaluation);
        //record.setApplicantEvaluation(applicantEvaluation);
        //record.setPosterEvaluation(posterEvaluation);
//        applicantEvaluation.setTrade(record);
//        posterEvaluation.setTrade(record);
        record.setDealTime(new Date());
        finishedTradeEvent(tradeRecordRepository.save(record));
    }

    @Override
    public void refuseRecord(Long recordId) throws Exception {
        TradeRecord record = tradeRecordRepository.findById(recordId).orElse(null);
        if(record == null){
            throw new Exception("记录不存在");
        }
        RecordState state = record.getState();
        if(state == RecordState.Refused){
            throw new Exception("已拒绝");
        }
        if(state == RecordState.Cancelled || state == RecordState.Passed){
            throw new Exception("error! 无法拒绝已取消/通过的申请");
        }
        record.setState(RecordState.Refused);
        record.setDealTime(new Date());
        tradeRecordRepository.save(record);
    }

    @Override
    public void cancelRecordApplication(Long recordId) throws Exception {
        TradeRecord record = tradeRecordRepository.findById(recordId).orElse(null);
        if(record == null){
            throw new Exception("记录不存在");
        }
        RecordState state = record.getState();
        if(state == RecordState.Cancelled){
            throw new Exception("已取消");
        }
        if(state == RecordState.Refused || state == RecordState.Passed){
            throw new Exception("error! 无法取消已拒绝/通过的申请");
        }
        record.setState(RecordState.Cancelled);
        record.setDealTime(new Date());
        tradeRecordRepository.save(record);
    }

    @Override
    public List<TradeRecord> getReceivedRecordApplicationByUserAndState(String userID, RecordState state) throws Exception {
        User user = userRepository.findByAccount_UserID(userID);
        if(user == null){
            throw new Exception("user不存在");
        }
        // assert(state != null);
        return tradeRecordRepository.findByPostInAndState(xDanPostRepository.findByUser(user), state);
    }

    @Override
    public List<TradeRecord> getIssuedRecordApplicationByUserAndState(String userID, RecordState state) throws Exception {
        User user = userRepository.findByAccount_UserID(userID);
        if(user == null){
            throw new Exception("user不存在");
        }
        // assert(state != null);
        return tradeRecordRepository.findByApplicantAndState(userID, state);
    }

    private XDanPost createdPostEvent(XDanPost post){
        User user = post.getUser();
        assert (user != null);
        user.addPostNum();
        userRepository.save(user);
        return xDanPostRepository.save(post);
    }

    private void finishedTradeEvent(TradeRecord record){
        User applicant = userRepository.findByAccount_UserID(record.getApplicant());
        User poster = record.getPost().getUser();
        assert (applicant != null && poster != null);
        applicant.addTradeNum();
        poster.addTradeNum();
        userRepository.save(applicant);
        userRepository.save(poster);
        tradeRecordRepository.save(record);
    }
}
