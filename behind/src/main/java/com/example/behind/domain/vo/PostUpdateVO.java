package com.example.behind.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateVO {
    Long postID;
    String title;
    String content;
    Integer price;
    String goodsType;
    String address;
}
