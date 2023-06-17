package com.example.behind.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationRetVO {
    Long evaluationID;
    String userID;
    String avatar;
    Long postID;
    String postTitle;
    String evaluation;
    String content;
}
