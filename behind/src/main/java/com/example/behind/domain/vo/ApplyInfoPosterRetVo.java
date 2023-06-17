package com.example.behind.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyInfoPosterRetVo {
    Long applyID;
    String applyTime;
    Long postID;
    String poster;
    String avatar;
    String postTitle;
    String applyMessage;
    String rejectTime;
    String passTime;
}
