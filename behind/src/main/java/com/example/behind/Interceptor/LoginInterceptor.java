package com.example.behind.Interceptor;

import com.example.behind.common.Result;
import com.example.behind.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.servlet.HandlerInterceptor;


public class LoginInterceptor implements HandlerInterceptor {
    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        try{
            if(token==null || token.isEmpty())throw new Exception("无token");
            JwtUtil.verify(token);
            String json = new ObjectMapper().writeValueAsString(Result.fail(660,"重复登录"));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(json);
            return false;
        }catch (Exception e) {
            return true;
        }
    }
}
