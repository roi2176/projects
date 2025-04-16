package com.swjtu.controller.interceptor;

import com.swjtu.entity.LoginTicket;
import com.swjtu.entity.User;
import com.swjtu.mapper.UserMapper;
import com.swjtu.service.UserService;
import com.swjtu.util.CookieUtil;
import com.swjtu.util.HostHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    //用于在代替session持有用户信息
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie获取ticket凭证
        String ticket = CookieUtil.getValue(request, "ticket");
        if (ticket != null) {
            //查询
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                //查询用户
                User user = userService.findUserById(loginTicket.getUserId());
                hostHolder.setUser(user);
            }
        }

        return true;
    }

    //将user数据放到模型中
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser",user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
