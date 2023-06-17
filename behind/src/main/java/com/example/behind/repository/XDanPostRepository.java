package com.example.behind.repository;

import com.example.behind.common.domain_attributes.GoodsType;
import com.example.behind.common.domain_attributes.PostState;
import com.example.behind.common.domain_attributes.TradePostType;
import com.example.behind.domain.User;
import com.example.behind.domain.XDanPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XDanPostRepository extends JpaRepository<XDanPost, Long> {
    List<XDanPost> findByStateAndGoodsType(PostState state, GoodsType goodsType);
    List<XDanPost> findByState(PostState state);
    List<XDanPost> findByTypeAndStateAndGoodsType(TradePostType type, PostState state, GoodsType goodsType);
    List<XDanPost> findByTypeAndState(TradePostType type, PostState state);
    List<XDanPost> findByUser(User user);
    List<XDanPost> findByUserAndState(User user, PostState state);
}
