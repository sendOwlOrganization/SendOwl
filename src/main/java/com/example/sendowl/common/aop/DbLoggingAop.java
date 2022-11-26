package com.example.sendowl.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class DbLoggingAop {


    // 게시물 상세조회시
    @Around("execution(* com.example.sendowl.api.service.BoardService.getBoardList(..))")
    public Object boardLog (ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            System.out.println("This is Aop");
            ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();

            return joinPoint.proceed();
        } finally {

        }
    }
}
