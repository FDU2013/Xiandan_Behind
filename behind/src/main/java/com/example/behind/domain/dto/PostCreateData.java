package com.example.behind.domain.dto;

import com.example.behind.common.domain_attributes.CampusEnum;
import com.example.behind.common.domain_attributes.GoodsType;
import com.example.behind.common.domain_attributes.TradePostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateData {
    String userID;
    String title;
    String content;
    Integer price;
    GoodsType goodsType;
    CampusEnum address;
    List<String> tags;
    TradePostType postType;
    List<String> images_url;
}
