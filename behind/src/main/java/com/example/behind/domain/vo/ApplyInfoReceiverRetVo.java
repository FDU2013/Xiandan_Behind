package com.example.behind.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyInfoReceiverRetVo {
    Long applyID;
    String applyTime;
    Long postID;
    String postTitle;
    String applicant;
    String avatar;
    String applyMessage;
    String dealTime;

}
