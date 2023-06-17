package com.example.behind.controller;

import com.example.behind.common.MyPage;
import com.example.behind.common.Result;
import com.example.behind.domain.Comment;
import com.example.behind.domain.XDanPost;
import com.example.behind.domain.dto.PostSearchData;
import com.example.behind.domain.vo.PostCommentRetVO;
import com.example.behind.domain.vo.PostCommentSearchVO;
import com.example.behind.domain.vo.PostRetVO;
import com.example.behind.domain.vo.PostSearchVO;
import com.example.behind.service.CommentService;
import com.example.behind.service.PostService;
import com.example.behind.utils.DSTransTool;
import com.example.behind.utils.EnumTool;
import com.example.behind.utils.MyPageTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostInfoController {
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    @PostMapping("/searchForOpenPost")
    public Result searchForOpenPost(@RequestBody PostSearchVO postSearchVO){
        try {
            PostSearchData postSearchData = new PostSearchData(postSearchVO.getSearch(), EnumTool.Str2TradePostType(postSearchVO.getPostType()), postSearchVO.getPriceMin(), postSearchVO.getPriceMax(), EnumTool.Str2GoodsType(postSearchVO.getGoodsType()), postSearchVO.getTags(), EnumTool.Str2Campus(postSearchVO.getAddress()));
            MyPage<XDanPost> myPage = postService.searchPost(postSearchData, postSearchVO.getPageSize(), postSearchVO.getPageNum());
            MyPage<PostRetVO> pageRet = new MyPage<>();
            pageRet.setTotal(myPage.getTotal());
            List<PostRetVO> pages = new ArrayList<>();
            myPage.getRecords().forEach(recode->{
                pages.add(DSTransTool.XDanPost2FrontPost(recode));
            });
            pageRet.setRecords(pages);
            return Result.succ(pageRet);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(622, e.getMessage());
        }
    }

    // 查看一个帖子的具体信息
    @PostMapping("/info")
    public Result info(@RequestBody Long postID){
        try {
            XDanPost xDanPost = postService.getPost(postID);
            return Result.succ(DSTransTool.XDanPost2FrontPost(xDanPost));
        }
        catch (Exception e) {
            return Result.fail(622, e.getMessage());
        }
    }

    // 查看一个帖子的评论
    @PostMapping("/getComment")
    public Result comment(@RequestBody PostCommentSearchVO search){
        try {
            List<Comment> comments = commentService.getCommentByPost(search.getPostID());
            MyPage<Comment> commentMyPage = MyPageTool.getPage(comments,search.getPageSize(),search.getPageNum());
            MyPage<PostCommentRetVO> voMyPage = new MyPage<>(commentMyPage.getTotal(),new ArrayList<>());
            commentMyPage.getRecords().forEach(comment -> {
                voMyPage.getRecords().add(DSTransTool.Comment2RetVO(comment));
            });
            return Result.succ(voMyPage);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail(721, e.getMessage());
        }
    }

}
