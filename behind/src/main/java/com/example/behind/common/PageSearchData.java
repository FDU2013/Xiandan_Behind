package com.example.behind.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageSearchData {
    Integer pageNum;
    Integer pageSize;
    String search;
}
