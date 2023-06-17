package com.example.behind.domain;


import com.example.behind.common.domain_attributes.CampusEnum;
import com.example.behind.common.domain_attributes.RoleType;
import com.example.behind.domain.dto.UserCreateData;
import javax.persistence.*;

import com.example.behind.domain.dto.UserUpdateData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 36)
    private String name;

    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "phone", length = 36)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "campus")
    private CampusEnum campus;

    @Column(name = "school")
    private String school;

    @Column(name = "major")
    private String major;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account", nullable = false)
    private Account account;

    @Column(name = "head_img_url")
    private String headImgUrl;

    @Column(name = "post_num", nullable = false)
    private Integer postNum;

    @Column(name = "trade_num", nullable = false)
    private Integer tradeNum;

    /*  信誉分
     *  初始值defaultCredit最大值maxCredit最小值0
     *  规则:
     *  好评+creditChangeRate,若增加后credit>=softUpperLimitCredit,超出部分变为其lowCreditChangeRate/creditChangeRate
     *  差评-creditChangeRate
     *  若评价人已经评价过, 则好差评+-lowCreditChangeRate,
     *  一个用户对另一个用户的信誉分最多影响为+-oneUserChangeMax,
     *  信誉极佳:101-120信誉优秀:81-100信誉一般:71-80信誉较差:61-80信誉极差:0-60
     */
    @Column(name = "credit", nullable = false)
    private Integer credit;

    public static final int defaultCredit = 80;
    public static final int softLowerLimitCredit = 60;
    public static final int softUpperLimitCredit = 100;
    public static final int maxCredit = 120;
    public static final int creditChangeRate = 3;
    public static final int lowCreditChangeRate = 1;
    public static final int oneUserChangeMax = 6;

    public User(UserCreateData userData) {
        name = userData.getName();
        phone = userData.getPhone();
        email = userData.getEmail();
//        campus = userData.getCampus();
//        school = userData.getSchool();
//        major = userData.getMajor();
        Account account1 = new Account();
        account1.setUserID(userData.getUserID());
        account1.setPassword(userData.getPassword());
        account1.setStuNum(userData.getStuNum());
        account1.setRole(RoleType.User);
        account = account1;
        credit = defaultCredit;
        postNum = 0;
        tradeNum = 0;
    }

    public void update(UserUpdateData userData){
        if(userData.getEmail() != null) email = userData.getEmail();
        if(userData.getPhone() != null) phone = userData.getPhone();
        if(userData.getName() != null) name = userData.getName();
        if(userData.getCampus() != null) campus = userData.getCampus();
        if(userData.getSchool() != null) school = userData.getSchool();
        if(userData.getMajor() != null) major = userData.getMajor();
        if(userData.getHeadImgUrl() != null) headImgUrl = userData.getHeadImgUrl();
    }
    public int addCredit(){
        int temp = credit;
        if(credit <= softUpperLimitCredit){
            int upper = credit + creditChangeRate - softUpperLimitCredit;
            if(upper > 0){
                credit = softUpperLimitCredit + upper * lowCreditChangeRate / creditChangeRate;
            } else {
                credit += creditChangeRate;
            }
        } else {
            credit += (credit == maxCredit) ? 0 : lowCreditChangeRate;
        }
        return credit - temp;
    }
    public int addCredit(Integer val){
        int temp = credit;
        if(credit + val > maxCredit){
            credit = maxCredit;
            return maxCredit - temp;
        } else {
            credit += val;
            return val;
        }
    }
    public int subtractCredit(){
        this.credit -= creditChangeRate;
        return creditChangeRate;
    }
    public int subtractCredit(Integer val){
        int temp = credit;
        if(credit - val < 0){
            credit = 0;
            return temp;
        } else {
            credit -= val;
            return val;
        }
    }

    public void addPostNum(){
        postNum += 1;
    }

    public void addTradeNum(){
        tradeNum += 1;
    }
}
