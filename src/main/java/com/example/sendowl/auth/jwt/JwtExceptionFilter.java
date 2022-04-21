package com.example.sendowl.auth.jwt;

import com.example.sendowl.auth.exception.JwtInvalidException;
import com.example.sendowl.auth.exception.JwtNotFoundException;
import com.example.sendowl.auth.exception.enums.JwtErrorCode;
import com.example.sendowl.auth.exception.handler.JwtExceptionHandler;
import com.example.sendowl.common.dto.BaseErrorResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }catch(JwtNotFoundException ex){
            JwtNotFoundException boxedException = new JwtNotFoundException(
                    JwtErrorCode.NOT_FOUND, ex);
            setErrorResponse(new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),boxedException.getErrorStatus()), response);
        }catch (JwtInvalidException ex){
            JwtInvalidException boxedException = new JwtInvalidException(
                    JwtErrorCode.INVALID, ex);
            setErrorResponse(new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),boxedException.getErrorStatus()), response);
        }
    }

    public void setErrorResponse(ResponseEntity<BaseErrorResponseDto> responseEntity, HttpServletResponse response){
        response.setStatus(responseEntity.getStatusCodeValue());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try{
            String json = convertObjectToJson(responseEntity.getBody());
            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private String convertObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
