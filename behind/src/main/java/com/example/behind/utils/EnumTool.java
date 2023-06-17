package com.example.behind.utils;

import com.example.behind.common.domain_attributes.CampusEnum;
import com.example.behind.common.domain_attributes.EvaluationResult;
import com.example.behind.common.domain_attributes.GoodsType;
import com.example.behind.common.domain_attributes.TradePostType;

public class EnumTool {
    public static String Campus2Str(CampusEnum address){
        if(address == null) return null;
        switch (address){
            case Handan: return "邯郸";
            case Fenglin: return "枫林";
            case Jiangwan:return "江湾";
            case OutSchool:return "校外";
            case Zhangjiang:return "张江";
            case OutShanghai:return "外省";
        }
        System.out.println("please check error!!!!");
        return null;
    }

    public static CampusEnum Str2Campus(String address){
        if(address == null) return null;
        switch (address){
            case "邯郸校区":
            case "邯郸": return CampusEnum.Handan;
            case "枫林校区":
            case "枫林": return CampusEnum.Fenglin;
            case "江湾校区":
            case "江湾":return CampusEnum.Jiangwan;
            case "校外":return CampusEnum.OutSchool;
            case "张江校区":
            case "张江":return CampusEnum.Zhangjiang;
            case "外省":return CampusEnum.OutShanghai;
        }
        System.out.println("please check error!!!!");
        return null;
    }

    public static String GoodsType2Str(GoodsType goodsType){
        if(goodsType == null) return null;
        switch (goodsType){
            case Study: return "学习用品";
            case Live: return "生活用品";
            case Recreation:return "娱乐";
            case Null:return "其他";
        }
        System.out.println("please check error!!!!");
        return null;
    }

    public static GoodsType Str2GoodsType(String goodsType){
        if(goodsType == null) return null;
        switch (goodsType){
            case "学习用品": return GoodsType.Study;
            case "生活用品": return GoodsType.Live;
            case "娱乐":return GoodsType.Recreation;
            case "其他":return GoodsType.Null;
        }
        System.out.println("please check error!!!!");
        return null;
    }

    public static String TradePostType2Str(TradePostType tradePostType){
        if(tradePostType == null) return null;
        switch (tradePostType){
            case Sell: return "出售";
            case Buy: return "购买";
        }
        System.out.println("please check error!!!!");
        return null;
    }

    public static TradePostType Str2TradePostType(String tradePostType){
        if(tradePostType == null) return null;
        switch (tradePostType){
            case "出售": return TradePostType.Sell;
            case "购买": return TradePostType.Buy;
        }
        System.out.println("please check error!!!!");
        return null;
    }

    public static String EvaluationResult2Str(EvaluationResult evaluation){
        if(evaluation == null) return null;
        switch (evaluation){
            case Good: return "好评";
            case Bad: return "差评";
        }
        System.out.println("please check error!!!!");
        return null;
    }

    public static EvaluationResult Str2EvaluationResult(String evaluation){
        if(evaluation == null) return null;
        switch (evaluation){
            case "好评": return EvaluationResult.Good;
            case "差评": return EvaluationResult.Bad;
        }
        System.out.println("please check error!!!!");
        return null;
    }
}
