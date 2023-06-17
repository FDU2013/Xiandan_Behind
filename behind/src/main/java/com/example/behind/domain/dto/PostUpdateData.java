package com.example.behind.domain.dto;

import com.example.behind.common.domain_attributes.CampusEnum;
import com.example.behind.common.domain_attributes.GoodsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateData {
    Long id;
    String title;
    String content;
    Integer price;
    GoodsType goodsType;
    CampusEnum address;
}
