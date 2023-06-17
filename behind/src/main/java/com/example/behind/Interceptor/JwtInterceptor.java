package com.example.behind.Interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.behind.common.Result;
import com.example.behind.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        Result result;
        if (token == null || token.isEmpty()) {
            String json = new ObjectMapper().writeValueAsString(Result.fail(650, "请先登录"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(json);
            return false;
        }
        try {
            JwtUtil.verify(token);
            return true;
        } catch (SignatureVerificationException e) {
            //e.printStackTrace();
            result = Result.fail(600, "无效签名");
        } catch (TokenExpiredException e) {
            //e.printStackTrace();
            result = Result.fail(601, "令牌过期");
        } catch (AlgorithmMismatchException e) {
            //e.printStackTrace();
            result = Result.fail(602, "token算法不一致");
        } catch (Exception e) {
            //e.printStackTrace();
            result = Result.fail(603, "token失效");
        }

        //将map转化成json，response使用的是Jackson
        String json = new ObjectMapper().writeValueAsString(result);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(json);
        return false;
    }
}
