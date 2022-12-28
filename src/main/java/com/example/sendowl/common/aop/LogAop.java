package com.example.sendowl.common.aop;

import com.example.sendowl.api.service.LogService;
import com.example.sendowl.auth.PrincipalDetails;
import com.example.sendowl.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
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
public class LogAop {


    private final LogService logService;

    // 게시물 조회 로깅
    @Around("execution(* com.example.sendowl.api.service.BoardService.getBoardList(..))")
    public Object boardLog (ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            // get Parameters from HttpRequest
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            //Map<String,String[]> params = request.getParameterMap();
            if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))) {

                PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = principal.getUser();

                // call service
                logService.LoggingBoardLog(Long.parseLong(request.getParameter("categoryId")), user.getMbti());
            }
            // 없으면 진행
            return joinPoint.proceed();
        } catch (Throwable e) {
            // 예외나오류 시, 로그 찍지않고 서비스는 정상 실행
            return joinPoint.proceed();
        }
    }

    //검색 로깅
    @Around("execution(* com.example.sendowl.api.service.BoardService.getBoardBySearch(..))")
    public Object searchLog (ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            // get Parameters from HttpRequest
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            //Map<String,String[]> params = request.getParameterMap();
            if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))) {

                PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = principal.getUser();

                // call service
                logService.LoggingSearchKeywordLog(request.getParameter("query"), user.getMbti());
            }
            // 없으면 진행
            return joinPoint.proceed();
        } catch (Throwable e) {
            return joinPoint.proceed();
        }
    }

    //게시글 좋아요/취소 로깅

    @Around("execution(* com.example.sendowl.api.service.LikeService.setBoard*(..))")
    public Object likeLog (ProceedingJoinPoint joinPoint) throws Throwable {
        // 좋아요 인지 좋아요 취소인지
        boolean isLike = joinPoint.getSignature().getName().equals("setBoardLike");

        try {
            // get Parameters from HttpRequest
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            //Map<String,String[]> params = request.getParameterMap();
            if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))) {

                PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = principal.getUser();

                // call service
                logService.LoggingLikeLog(Long.parseLong(request.getParameter("boardId")), user.getMbti(), isLike);
            }
            // 없으면 진행
            return joinPoint.proceed();
        } catch (Throwable e) {
            return joinPoint.proceed();
        }
    }
}