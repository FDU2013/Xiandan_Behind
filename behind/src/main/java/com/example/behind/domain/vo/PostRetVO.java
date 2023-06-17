package com.example.behind.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRetVO {
    Long id;
    String userID;
    String title;
    String content;
    Integer price;
    String address;
    String goodsType;
    List<String> tags;
    String postType;
    List<String> images;
}
