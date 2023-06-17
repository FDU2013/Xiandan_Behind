package com.example.behind.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private int code;//200表示正常，非200表示异常
    private String msg;
    private Object data;


    public static Result succ(Object data,String msg){
        return new Result(200,msg,data);
    }
    public static Result succ(Object data){
        return new Result(200,"succ",data);
    }
    public static Result fail(int code,String msg){
        return new Result(code,msg,null);
    }
}
