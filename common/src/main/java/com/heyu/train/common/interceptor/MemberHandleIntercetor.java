package com.heyu.train.common.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.heyu.train.common.resp.MemberDTO;
import com.heyu.train.common.util.JwtUtil;
import context.LoginMemberContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/31 19:59
 */
@Slf4j
@Component
public class MemberHandleIntercetor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StrUtil.isNotBlank(token)) {
            log.info("当前会员登录token:{}", token);
            JSONObject member = JwtUtil.getMember(token);
            MemberDTO loginMember = BeanUtil.toBean(member, MemberDTO.class);
            log.info("当前会员登录信息:{}", loginMember);
            LoginMemberContext.setMember(loginMember);
        }
        return true;
    }
}
