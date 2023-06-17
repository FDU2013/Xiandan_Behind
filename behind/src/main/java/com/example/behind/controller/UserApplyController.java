package com.example.behind.controller;

import com.example.behind.common.Result;
import com.example.behind.common.domain_attributes.RecordState;
import com.example.behind.domain.TradeRecord;
import com.example.behind.domain.XDanPost;
import com.example.behind.domain.vo.ApplyInfoPosterRetVo;
import com.example.behind.domain.vo.ApplyInfoReceiverRetVo;
import com.example.behind.domain.vo.ApplyRecordVo;
import com.example.behind.domain.vo.PostRetVO;
import com.example.behind.service.PostService;
import com.example.behind.service.UserAccountService;
import com.example.behind.utils.DSTransTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserApplyController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserAccountService userService;
    @PostMapping("/applyRecord")
    public Result applyRecord(HttpServletRequest request,@RequestBody ApplyRecordVo apply){

        try {
            String userID = request.getHeader("userID");
            postService.applyRecord(userID, apply.getPostID(),apply.getApplyMessage());
            return Result.succ(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(701, e.getMessage());
        }
    }

    @PostMapping("/agreeRecord")
    public Result agreeRecord(HttpServletRequest request,@RequestBody Long recordID){

        try {
            String userID = request.getHeader("userID");
            postService.agreeRecord(recordID);
            return Result.succ(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(702, e.getMessage());
        }
    }

    @PostMapping("/refuseRecord")
    public Result refuseRecord(HttpServletRequest request,@RequestBody Long recordID){

        try {
            String userID = request.getHeader("userID");
            postService.refuseRecord(recordID);
            return Result.succ(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(701, e.getMessage());
        }
    }

    @PostMapping("/waitingRecord")
    public Result waitingRecord(HttpServletRequest request){

        try {
            String userID = request.getHeader("userID");
            List<TradeRecord> records = postService.getIssuedRecordApplicationByUserAndState(userID, RecordState.Posted);
            List<ApplyInfoPosterRetVo> posterRetVOList = new ArrayList<>();
            records.forEach(recode -> {
                posterRetVOList.add(DSTransTool.Record2PosterRetVo(recode));
            });
            return Result.succ(posterRetVOList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(701, e.getMessage());
        }
    }

    @PostMapping("/rejectedRecord")
    public Result rejectedRecord(HttpServletRequest request){

        try {
            String userID = request.getHeader("userID");
            List<TradeRecord> records = postService.getIssuedRecordApplicationByUserAndState(userID, RecordState.Refused);
            List<ApplyInfoPosterRetVo> posterRetVOList = new ArrayList<>();
            records.forEach(recode -> {
                posterRetVOList.add(DSTransTool.Record2PosterRetVo(recode));
            });
            return Result.succ(posterRetVOList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(701, e.getMessage());
        }
    }

    @PostMapping("/passedRecord")
    public Result passedRecord(HttpServletRequest request){

        try {
            String userID = request.getHeader("userID");
            List<TradeRecord> records = postService.getIssuedRecordApplicationByUserAndState(userID, RecordState.Passed);
            List<ApplyInfoPosterRetVo> posterRetVOList = new ArrayList<>();
            records.forEach(recode -> {
                posterRetVOList.add(DSTransTool.Record2PosterRetVo(recode));
            });
            return Result.succ(posterRetVOList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(701, e.getMessage());
        }
    }


    @PostMapping("/toDealRecordList")
    public Result toDealRecordList(HttpServletRequest request){

        try {
            String userID = request.getHeader("userID");
            List<TradeRecord> records = postService.getReceivedRecordApplicationByUserAndState(userID, RecordState.Posted);
            List<ApplyInfoReceiverRetVo> receiverRetVOList = new ArrayList<>();
            records.forEach(recode -> {
                ApplyInfoReceiverRetVo tmp = DSTransTool.Record2ReceiverRetVo(recode);
                tmp.setAvatar(userService.getUser(recode.getApplicant()).getHeadImgUrl());
                receiverRetVOList.add(tmp);
            });
            return Result.succ(receiverRetVOList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(704, e.getMessage());
        }
    }

    @PostMapping("/DealedPassRecordList")
    public Result DealedPassRecordList(HttpServletRequest request){

        try {
            String userID = request.getHeader("userID");
            List<TradeRecord> records = postService.getReceivedRecordApplicationByUserAndState(userID, RecordState.Passed);
            List<ApplyInfoReceiverRetVo> receiverRetVOList = new ArrayList<>();
            records.forEach(recode -> {
                receiverRetVOList.add(DSTransTool.Record2ReceiverRetVo(recode));
            });
            return Result.succ(receiverRetVOList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(705, e.getMessage());
        }
    }
    @PostMapping("/DealedRejectedRecordList")
    public Result DealedRejectedRecordList(HttpServletRequest request){

        try {
            String userID = request.getHeader("userID");
            List<TradeRecord> records = postService.getReceivedRecordApplicationByUserAndState(userID, RecordState.Refused);
            List<ApplyInfoReceiverRetVo> receiverRetVOList = new ArrayList<>();
            records.forEach(recode -> {
                receiverRetVOList.add(DSTransTool.Record2ReceiverRetVo(recode));
            });
            return Result.succ(receiverRetVOList);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(706, e.getMessage());
        }
    }

}
