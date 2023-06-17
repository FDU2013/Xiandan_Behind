package com.example.behind.utils;

import com.example.behind.common.MyPage;

import java.util.ArrayList;
import java.util.List;

public class MyPageTool {
    public static <T>MyPage<T> getPage(List<T> allData, Integer pageSize, Integer pageNum){
        MyPage<T> ans = new MyPage<>();
        ans.setTotal(allData.size());
        ans.setRecords(new ArrayList<>());
        for(int i=(pageNum-1)*pageSize; i<pageNum*pageSize && i<ans.getTotal() && i>=0 ; i++){
            ans.getRecords().add((allData.get(i)));
        }
        return ans;
    }
}
