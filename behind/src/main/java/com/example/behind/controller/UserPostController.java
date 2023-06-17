package com.example.behind.controller;


import com.example.behind.common.MyPage;
import com.example.behind.common.Result;
import com.example.behind.common.domain_attributes.PostState;
import com.example.behind.domain.XDanPost;
import com.example.behind.domain.dto.PostCreateData;
import com.example.behind.domain.dto.PostUpdateData;
import com.example.behind.domain.vo.PostCreateVO;
import com.example.behind.domain.vo.PostRetVO;
import com.example.behind.domain.vo.PostUpdateVO;
import com.example.behind.domain.vo.UploadCommentVO;
import com.example.behind.service.CommentService;
import com.example.behind.service.PostService;
import com.example.behind.utils.DSTransTool;
import com.example.behind.utils.EnumTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.behind.utils.DSTransTool.XDanPost2FrontPost;

@RestController
@RequestMapping("/user")
public class UserPostController {
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    @PostMapping("/addPost")
    public Result addPost(HttpServletRequest request, @RequestBody PostCreateVO postCreateVO) {
        XDanPost xDanPost;
        try {
            String userID = request.getHeader("userID");
            PostCreateData postCreateData = new PostCreateData(
                    userID,
                    postCreateVO.getTitle(),
                    postCreateVO.getContent(),
                    postCreateVO.getPrice(),
                    EnumTool.Str2GoodsType(postCreateVO.getGoodsType()),
                    EnumTool.Str2Campus(postCreateVO.getAddress()),
                    postCreateVO.getTags(),
                    EnumTool.Str2TradePostType(postCreateVO.getPostType()),
                    postCreateVO.getImages_url());
            xDanPost = postService.createPost(postCreateData);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(662, e.getMessage());
        }
        return Result.succ(DSTransTool.XDanPost2FrontPost(xDanPost));
    }

    @PostMapping("/updatePost")
    public Result updatePost(HttpServletRequest request, @RequestBody PostUpdateVO postUpdateVO) {
        try {
            PostUpdateData postUpdateData = new PostUpdateData(
                    postUpdateVO.getPostID(),
                    postUpdateVO.getTitle(),
                    postUpdateVO.getContent(),
                    postUpdateVO.getPrice(),
                    EnumTool.Str2GoodsType(postUpdateVO.getGoodsType()),
                    EnumTool.Str2Campus(postUpdateVO.getAddress())
            );
            if (!postService.updatePost(postUpdateData)) {
                return Result.fail(662, "更新失败");
            }
            return Result.succ(postUpdateVO);

        } catch (Exception e) {
            return Result.fail(662, e.getMessage());
        }
    }

    @PostMapping("/showPost")
    public Result showPost(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        MyPage<XDanPost> allPost;
        MyPage<PostRetVO> postRetVOPage = new MyPage<>();
        try {
            String userID = request.getHeader("userID");
            Integer pageSize = (Integer) map.get("pageSize");
            Integer pageNum = (Integer) map.get("pageNum");
            allPost = postService.getAllPostByUser(userID, pageSize, pageNum);
            postRetVOPage.setTotal(allPost.getTotal());
            List<PostRetVO> postRetVOList = new ArrayList<>();
            allPost.getRecords().forEach(recode -> {
                postRetVOList.add(DSTransTool.XDanPost2FrontPost(recode));
            });
            postRetVOPage.setRecords(postRetVOList);
        } catch (Exception e) {
            return Result.fail(662, e.getMessage());
        }
        return Result.succ(postRetVOPage);
    }

    @PostMapping("/showOpenPost")
    public Result showOpenPost(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        MyPage<XDanPost> allPost;
        MyPage<PostRetVO> postRetVOPage = new MyPage<>();
        try {
            String userID = request.getHeader("userID");
            Integer pageSize = (Integer) map.get("pageSize");
            Integer pageNum = (Integer) map.get("pageNum");
            allPost = postService.getPostByUserAndState(userID, PostState.Open, pageSize, pageNum);
            postRetVOPage.setTotal(allPost.getTotal());
            List<PostRetVO> postRetVOList = new ArrayList<>();
            allPost.getRecords().forEach(recode -> {
                postRetVOList.add(DSTransTool.XDanPost2FrontPost(recode));
            });
            postRetVOPage.setRecords(postRetVOList);
        } catch (Exception e) {
            return Result.fail(662, e.getMessage());
        }
        return Result.succ(postRetVOPage);
    }

    @PostMapping("/showClosePost")
    public Result showClosePost(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        MyPage<XDanPost> allPost;
        MyPage<PostRetVO> postRetVOPage = new MyPage<>();
        try {
            String userID = request.getHeader("userID");
            Integer pageSize = (Integer) map.get("pageSize");
            Integer pageNum = (Integer) map.get("pageNum");
            allPost = postService.getPostByUserAndState(userID, PostState.Close, pageSize, pageNum);
            postRetVOPage.setTotal(allPost.getTotal());
            List<PostRetVO> postRetVOList = new ArrayList<>();
            allPost.getRecords().forEach(recode -> {
                postRetVOList.add(DSTransTool.XDanPost2FrontPost(recode));
            });
            postRetVOPage.setRecords(postRetVOList);
        } catch (Exception e) {
            return Result.fail(662, e.getMessage());
        }
        return Result.succ(postRetVOPage);
    }

    @PostMapping("/closePost")
    public Result closePost( @RequestBody Long postID) {
        try {
            postService.closePost(postID);
        } catch (Exception e) {
            return Result.fail(663, e.getMessage());
        }
        return Result.succ(null);
    }

    @PostMapping("/openPost")
    public Result openPost(@RequestBody Long postID) {
        try {
            postService.openPost(postID);
        } catch (Exception e) {
            return Result.fail(663, e.getMessage());
        }
        return Result.succ(null);
    }

    @PostMapping("/uploadComment")
    public Result uploadComment(HttpServletRequest request,@RequestBody UploadCommentVO uploadCommentVO) {

        try {
            String userID = request.getHeader("userID");
            commentService.doComment(uploadCommentVO.getPostID(),userID,uploadCommentVO.getComment());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(663, e.getMessage());
        }
        return Result.succ(null);
    }

//
//    // 查看自己未评论的部分
//    @PostMapping("notComment")
//
//    // 查看自己已经评论的部分
//    @PostMapping("havaComment")
//


}