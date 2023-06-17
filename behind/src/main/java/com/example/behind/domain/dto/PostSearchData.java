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
public class PostSearchData {
    String search;
    TradePostType postType;
    Integer priceMin;   //非负值有效
    Integer priceMax;   //非null有效, 负值代表无上限, 非必要
    GoodsType goodsType;    //值为GoodsType.null无效
    List<String> tags;  //null或size == 0时无效
    CampusEnum campus;  //必要
}
