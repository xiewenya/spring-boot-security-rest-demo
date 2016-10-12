package com.baojinsuo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.baojinsuo.common.SafeUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by bresai on 2016/10/10.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    protected static final Logger logger = Logger.getLogger("AuthWithUsername");

    @Autowired
    protected TestRestTemplate template;

    @Value("${baojinsuo.token.header}")
    protected String tokenHeader;

    protected static String token;

    protected HttpEntity entity;

    protected ObjectMapper mapper = new ObjectMapper();

    protected MultiValueMap getHeaders(){
        MultiValueMap headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        if (SafeUtils.isNotEmpty(token)) headers.add(tokenHeader, token);
        return headers;
    }

    protected  <T> String getRequest(T model) throws JsonProcessingException {
        return mapper.writeValueAsString(model);
    }

    protected <T> HttpEntity getEntity(T model, MultiValueMap headers) throws JsonProcessingException {
        String request = getRequest(model);
        return new HttpEntity<>(request, headers);
    }

    protected <T> HttpEntity getEntity(T model) throws JsonProcessingException {
        String request = getRequest(model);
        return new HttpEntity<>(request, getHeaders());
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> getBody(ResponseEntity response) throws IOException {
        return mapper.readValue(response.getBody().toString(), Map.class);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> getData(Map<String, Object> body){
        return (Map<String, Object>) body.get("data");
    }

    protected String getCode(Map<String, Object> body){
        return body.get("code").toString();
    }

    protected String getMessage(Map<String, Object> body){
        return body.get("message").toString();
    }
}
