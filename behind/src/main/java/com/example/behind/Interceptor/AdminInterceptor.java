package com.example.behind.Interceptor;

import com.example.behind.common.JwtUserData;
import com.example.behind.common.Result;
import com.example.behind.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {
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
        Result result;
        JwtUserData jwtUserData = JwtUtil.getToken(token);
        boolean permission = false;
        //System.out.println(jwtUserData.getRole());
        if(jwtUserData.getRole().equals("\"Admin\"")){
            permission = true;
        } else {
            result = Result.fail(630,"无访问权限");
            String json = new ObjectMapper().writeValueAsString(result);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(json);
        }
        return permission;
    }
}
