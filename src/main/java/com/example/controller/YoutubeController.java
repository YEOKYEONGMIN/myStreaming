package com.example.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/youtube/*")
public class YoutubeController {

    // 방송 리스트 가져오기(라이브방송만)
    @GetMapping(value = "/liveList",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getYoutubeLiveList() {
        System.out.println("list 접근");

        HashMap<String, Object> result = new HashMap<String, Object>();

        String jsonInString = "";

        try {

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000); //타임아웃 설정 5초
            factory.setReadTimeout(5000);//타임아웃 설정 5초
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            header.add("Accept","application/json");
            HttpEntity<?> entity = new HttpEntity<>(header);

            String maxResults = "20";
            String playlistId = "PLU12uITxBEPGpEPrYAxJvNDP6Ugx2jmUx";
            String apiKey = "AIzaSyAUs8F9TOZXZVz17UDKM8ojkbGtXCqTf8w";
            StringBuilder url = new StringBuilder("https://youtube.googleapis.com/youtube/v3/playlistItems?");
            url.append("part=snippet");
            url.append("&maxResults=" + maxResults);
            url.append("&playlistId=" + playlistId);
            url.append("&key=" + apiKey);

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url.toString()).build();
            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

            //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
            ObjectMapper mapper = new ObjectMapper();
            jsonInString = mapper.writeValueAsString(resultMap.getBody());

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            System.out.println(e.toString());

        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            System.out.println(e.toString());
        }

        return jsonInString;

    } //getTwitchLiveList

    
    @GetMapping(value = "/channelId/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public String getYoutubeProfilepic(@PathVariable("id") String id) {
    	System.out.println("youtube profilepic 접근...");
    	
        HashMap<String, Object> result = new HashMap<String, Object>();

        String jsonInString = "";

        try {

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000); //타임아웃 설정 5초
            factory.setReadTimeout(5000);//타임아웃 설정 5초
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            header.add("Accept","application/json");
            HttpEntity<?> entity = new HttpEntity<>(header);

            String channelId = id;
            String apiKey = "AIzaSyAUs8F9TOZXZVz17UDKM8ojkbGtXCqTf8w";
            StringBuilder url = new StringBuilder("https://www.googleapis.com/youtube/v3/channels?");
            url.append("part=snippet");
            url.append("&id=" + channelId);
            url.append("&fields=items");
            url.append("&key=" + apiKey);

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url.toString()).build();
            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

            //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
            ObjectMapper mapper = new ObjectMapper();
            jsonInString = mapper.writeValueAsString(resultMap.getBody());

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            System.out.println(e.toString());

        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            System.out.println(e.toString());
        }

        return jsonInString;
    	
    } // getYoutubeProfilepic

}
