package com.example.behind.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateVO {
    String title;
    String content;
    Integer price;
    String goodsType;
    String address;
    List<String> tags;
    String postType;
    List<String> images_url;
}
