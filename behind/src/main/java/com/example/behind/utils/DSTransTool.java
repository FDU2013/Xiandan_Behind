package com.example.behind.utils;

import com.example.behind.domain.Comment;
import com.example.behind.domain.Evaluation;
import com.example.behind.domain.TradeRecord;
import com.example.behind.domain.XDanPost;
import com.example.behind.domain.vo.*;

import java.util.ArrayList;
import java.util.List;

public class DSTransTool {
    public static PostRetVO XDanPost2FrontPost(XDanPost xDanPost){
        List<String> tags = new ArrayList<>();
        xDanPost.getTags().forEach(tagBind -> tags.add(tagBind.getTag()));
        List<String> images = new ArrayList<>();
        xDanPost.getImages().forEach(imageBind -> images.add(imageBind.getUrl()));
        return new PostRetVO(
                xDanPost.getId(),
                xDanPost.getUser().getAccount().getUserID(),
                xDanPost.getTitle(),
                xDanPost.getContent(),
                xDanPost.getPrice(),
                EnumTool.Campus2Str(xDanPost.getAddress()),
                EnumTool.GoodsType2Str(xDanPost.getGoodsType()),
                tags,
                EnumTool.TradePostType2Str(xDanPost.getType()),
                images);
    }

    public static ApplyInfoPosterRetVo Record2PosterRetVo(TradeRecord record){
        return new ApplyInfoPosterRetVo(
                record.getId(),
                record.getCreateTime().toString(),
                record.getPost().getId(),
                record.getPost().getUser().getAccount().getUserID(),
                record.getPost().getUser().getHeadImgUrl(),
                record.getPost().getTitle(),
                record.getPostMsg(),
                String.valueOf(record.getDealTime()),
                String.valueOf(record.getDealTime()));
    }

    public static ApplyInfoReceiverRetVo Record2ReceiverRetVo(TradeRecord record){
        return new ApplyInfoReceiverRetVo(
                record.getId(),
                record.getCreateTime().toString(),
                record.getPost().getId(),
                record.getPost().getTitle(),
                record.getApplicant(),
                null,
                record.getPostMsg(),
                String.valueOf(record.getDealTime()));
    }

    public static PostCommentRetVO Comment2RetVO(Comment comment){
        return new PostCommentRetVO(
                comment.getUser().getAccount().getUserID(),
                comment.getUser().getCredit(),
                comment.getUser().getHeadImgUrl(),
                comment.getTime().toString(),
                comment.getContent());
    }

    public static EvaluationRetVO ToOthersEvaluation2RetVO(Evaluation evaluation){
        return new EvaluationRetVO(
                evaluation.getId(),
                evaluation.getBeEvaluatedUserID(),
                null,
                evaluation.getTrade().getPost().getId(),
                evaluation.getTrade().getPost().getTitle(),
                EnumTool.EvaluationResult2Str(evaluation.getResult()),
                evaluation.getContent());
    }

    public static EvaluationRetVO ByOthersEvaluation2RetVO(Evaluation evaluation){
        return new EvaluationRetVO(
                evaluation.getId(),
                evaluation.getUserID(),
                null,
                evaluation.getTrade().getPost().getId(),
                evaluation.getTrade().getPost().getTitle(),
                EnumTool.EvaluationResult2Str(evaluation.getResult()),
                evaluation.getContent());
    }

}
