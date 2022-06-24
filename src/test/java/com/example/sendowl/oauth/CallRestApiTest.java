package com.example.sendowl.oauth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Headers;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class CallRestApiTest {

    @Test
    void GetRestApiForTest(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("https://httpbin.org/get", String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void GetRestApiWithHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("test");

        HttpEntity entity = new HttpEntity("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://httpbin.org/get", HttpMethod.GET, entity, String.class);
        System.out.println(response);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
