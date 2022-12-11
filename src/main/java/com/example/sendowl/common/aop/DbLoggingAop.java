package com.example.sendowl.common.aop;

import com.example.sendowl.api.service.BoardLogsService;
import com.example.sendowl.auth.PrincipalDetails;
import com.example.sendowl.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
public class DbLoggingAop {


    private final BoardLogsService boardLogsService;

    // 게시물 상세조회시
    @Around("execution(* com.example.sendowl.api.service.BoardService.getBoardList(..))")
    public Object boardLog (ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            // get Parameters from HttpRequest
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            //Map<String,String[]> params = request.getParameterMap();

            // get UserInfo
            PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = principal.getUser();

            // call service
            boardLogsService.insertBoardKeywordLog(Long.parseLong(request.getParameter("categoryId")), user.getMbti());
//            System.out.println("This is Aop");
            return joinPoint.proceed();

        } finally {

        }
    }
}