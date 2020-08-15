package com.zhangzhe.handler;

import com.zhangzhe.utils.ApiResult;
import com.zhangzhe.utils.JsonUtils;
import com.zhangzhe.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    RedisOperator redis;
    private static final String USER_REDIS_SESSION = "user-redis-session";
    /**
     *拦截请求，在controller调用之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)){
            String uniqueToken = redis.get(USER_REDIS_SESSION + ":" + userId);
            if(StringUtils.isEmpty(uniqueToken) && StringUtils.isNotBlank(uniqueToken)){
                returnErrorResponse(response, new ApiResult().errorTokenMsg("请登录..."));
                return false;
            }else {
                if (!uniqueToken.equals(userToken)) {

                    returnErrorResponse(response, new ApiResult().errorTokenMsg("账号被挤出..."));
                    return false;
                }
            }
        }else {
            returnErrorResponse(response, new ApiResult().errorTokenMsg("请登录..."));
            return false;
        }
        return true;
    }
    private void returnErrorResponse(HttpServletResponse response, ApiResult result)
            throws IOException {
        OutputStream out=null;
        try{
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(Objects.requireNonNull(JsonUtils.objectToJson(result)).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } finally{
            if(out!=null){
                out.close();
            }
        }
    }
}
