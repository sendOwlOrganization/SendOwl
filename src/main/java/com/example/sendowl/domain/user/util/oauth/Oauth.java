package com.example.sendowl.domain.user.util.oauth;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.domain.user.dto.Oauth2User;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public interface Oauth {
    public Oauth2User getOauth2User(String token);

    public boolean disconnectUser(String token);

    /**
     * 인증헤더를 추가하고, 각 서비스에 맞는 API를 호출해서 결과를 반환합니다.
     * @param parameter: httpMethod에 보낼 parameter 입니다.
     * @param header : Bearer Token외에 추가로 셋팅할 헤더를 지정합니다.
     * @param accessToken : 인증 헤더에 추가될 토큰값입니다.
     * @return : ResponseEntity<Map>
     */
    public static ResponseEntity<Map> requestApi(HttpMethod httpMethod, String URL, MultiValueMap<String, String> parameter, MultiValueMap<String, String> header, String accessToken){
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType(MediaType.APPLICATION_FORM_URLENCODED, StandardCharsets.UTF_8);
        headers.setContentType(mediaType);
        headers.addAll(header);
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(parameter, headers);
        ResponseEntity<Map> response = null;

        try{
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(URL, httpMethod, httpEntity, Map.class);
        }catch(HttpStatusCodeException e){
            throw new BaseException(e.getStatusCode(), e.getMessage(), e);
        }
        return response;

    };

}
