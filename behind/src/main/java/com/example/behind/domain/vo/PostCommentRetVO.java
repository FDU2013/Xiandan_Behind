package com.example.behind.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentRetVO {
    String userID;
    Integer creditScore;
    String avatar;
    String commentTime;
    String comment;
}
