package com.example.behind.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.behind.common.JwtUserData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtUtil {
    private static final Long passTime = 1000L*60*60*24;//一小时后过期
    private static final String signature = "husivhjsdkvnksdjvnsdhvwehe88*&^%";

    @Autowired
    RedisUtil redisUtil;
    /*
     * 生成token
     * */
    public static String createToken(JwtUserData jwtUserData){
        JWTCreator.Builder jwtBuilder = JWT.create();
        jwtBuilder.withClaim("number",jwtUserData.getNumber());
        jwtBuilder.withClaim("role",jwtUserData.getRole());

        Calendar calendarInstance = Calendar.getInstance();
        jwtBuilder.withExpiresAt(new Date(calendarInstance.getTimeInMillis()+passTime));
        String token = jwtBuilder.sign(Algorithm.HMAC256(signature)).toString();
        RedisUtil.saveValue(token,jwtUserData.getNumber(), Math.toIntExact(passTime), TimeUnit.SECONDS);
        return token;
    }

    public static void verify(String token) throws Exception {

        JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
        if(!RedisUtil.hasKey(token)){
            throw new Exception("非法的token");
        }
    }

    public static JwtUserData getToken(String token){
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(signature)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
        }
        JwtUserData userData=new JwtUserData(jwt.getClaims().get("number").toString(),
                jwt.getClaims().get("role").toString());

        return userData;
    }
}
