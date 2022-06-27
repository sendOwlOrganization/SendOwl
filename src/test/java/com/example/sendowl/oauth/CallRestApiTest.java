package com.example.sendowl.oauth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class CallRestApiTest {

    @Test
    void GetRestApiForTest(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("https://httpbin.org/get", String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void GetRestApiWithHeaderWillFail(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("wrong-data");

        HttpEntity entity = new HttpEntity("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://www.googleapis.com/oauth2/v3/userinfo", HttpMethod.GET, entity, String.class);
        System.out.println(response);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void GetRestApiWithHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("test");
        HttpEntity entity = new HttpEntity("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://www.googleapis.com/oauth2/v3/userinfo", HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void GetRestApiWithHeaderByMap(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("test");
        HttpEntity entity = new HttpEntity("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange("https://www.googleapis.com/oauth2/v3/userinfo", HttpMethod.GET, entity, Map.class);
        System.out.println(response.getBody());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
