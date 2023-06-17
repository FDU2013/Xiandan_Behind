package com.example.behind.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchVO {
    List<String> tags;
    Integer pageNum;
    Integer pageSize;
    String search;
    Integer priceMin;
    Integer priceMax;
    String goodsType;
    String postType;
    String address;
}
