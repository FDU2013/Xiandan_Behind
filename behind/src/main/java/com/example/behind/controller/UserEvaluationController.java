package com.example.behind.controller;

import com.example.behind.common.Result;
import com.example.behind.common.domain_attributes.EvaluationResult;
import com.example.behind.common.domain_attributes.EvaluationState;
import com.example.behind.domain.Evaluation;
import com.example.behind.domain.XDanPost;
import com.example.behind.domain.dto.PostCreateData;
import com.example.behind.domain.vo.EvaluationRetVO;
import com.example.behind.domain.vo.PostCreateVO;
import com.example.behind.domain.vo.RecentEvaluationSearchData;
import com.example.behind.domain.vo.UploadEvaluationVO;
import com.example.behind.service.EvaluationService;
import com.example.behind.service.UserAccountService;
import com.example.behind.utils.DSTransTool;
import com.example.behind.utils.EnumTool;
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
public class UserEvaluationController {
    @Autowired
    EvaluationService evaluationService;
    @Autowired
    UserAccountService userService;

    @PostMapping("/writeEvaluation")
    public Result writeEvaluation(@RequestBody UploadEvaluationVO uploadEvaluation) {
        try {
            evaluationService.uploadEvaluation(
                    uploadEvaluation.getEvaluationID(),
                    uploadEvaluation.getContent(),
                    EnumTool.Str2EvaluationResult(uploadEvaluation.getEvaluation())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(733, e.getMessage());
        }
        return Result.succ(null);
    }

    @PostMapping("/DealedEvaluationList")
    public Result DealedEvaluationList(HttpServletRequest request) {
        try {
            String userID = request.getHeader("userID");
            List<Evaluation> evaluationList = evaluationService.getEvaluationByUserAndState(userID, EvaluationState.Filled);
            List<EvaluationRetVO> ret = new ArrayList<>();
            evaluationList.forEach(evaluation -> {
                EvaluationRetVO tmp  = DSTransTool.ToOthersEvaluation2RetVO(evaluation);
                tmp.setAvatar(userService.getUser(evaluation.getBeEvaluatedUserID()).getHeadImgUrl());
                ret.add(tmp);
            });
            return Result.succ(ret);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(734, e.getMessage());
        }

    }

    @PostMapping("/toDealEvaluationList")
    public Result toDealEvaluationList(HttpServletRequest request) {
        try {
            String userID = request.getHeader("userID");
            List<Evaluation> evaluationList = evaluationService.getEvaluationByUserAndState(userID, EvaluationState.ToBeFilled);
            List<EvaluationRetVO> ret = new ArrayList<>();
            evaluationList.forEach(evaluation -> {
                EvaluationRetVO tmp  = DSTransTool.ToOthersEvaluation2RetVO(evaluation);
                tmp.setAvatar(userService.getUser(evaluation.getBeEvaluatedUserID()).getHeadImgUrl());
                ret.add(tmp);
            });
            return Result.succ(ret);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(735, e.getMessage());
        }
    }

    @PostMapping("/getRecentEvaluationByOthers")
    public Result getRecentEvaluationByOthers(@RequestBody RecentEvaluationSearchData search) {
        try {
            List<Evaluation> evaluationList = evaluationService.getUserEvaluation(search.getUserID());
            List<EvaluationRetVO> ret = new ArrayList<>();
            int limit = Math.min(search.getRecentSize(), evaluationList.size());
            for(int i = 0;i < limit;i++){
                Evaluation evaluation = evaluationList.get(i);
                EvaluationRetVO tmp  = DSTransTool.ByOthersEvaluation2RetVO(evaluation);
                tmp.setAvatar(userService.getUser(evaluation.getUserID()).getHeadImgUrl());
                ret.add(tmp);
            }
            return Result.succ(ret);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(735, e.getMessage());
        }
    }


}
