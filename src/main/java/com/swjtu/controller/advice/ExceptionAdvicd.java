package com.swjtu.controller.advice;

import com.swjtu.util.CommunityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@ControllerAdvice(annotations = Controller.class) //用于指定处理哪些bean类
public class ExceptionAdvicd {
    @ExceptionHandler({Exception.class}) //指定处理的异常类型
    public void handleException(Exception e, HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.error("服务器发生异常" + e.getMessage());
        for(StackTraceElement element : e.getStackTrace()){
            //记录每一条
            log.error(element.toString());
        }

        //判断是否为异步请求，模板做法
        String xRequestedWith = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            //是异步请求，返回字符串
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1, "服务器异常！"));
        }else {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
