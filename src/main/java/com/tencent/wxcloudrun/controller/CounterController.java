package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

/**
 * counter控制器
 */
@RestController

public class CounterController {

  final CounterService counterService;
  final Logger logger;

  public CounterController(@Autowired CounterService counterService) {
    this.counterService = counterService;
    this.logger = LoggerFactory.getLogger(CounterController.class);
  }


  /**
   * 获取当前计数
   * @return API response json
   */
  @GetMapping(value = "/api/count")
  ApiResponse get() {
    logger.info("/api/count get request");
    Optional<Counter> counter = counterService.getCounter(1);
    Integer count = 0;
    if (counter.isPresent()) {
      count = counter.get().getCount();
    }

    return ApiResponse.ok(count);
  }

  /**
   * 获取当前计数
   * @return API response json
   */
  @GetMapping(value = "/getUserInfo")
  ApiResponse getUserInfo(@RequestParam String code) {
    logger.info("/getUserInfo get request");
    //HttpCli
    //String userId =
    HttpClient httpClient = new HttpClient();
    //code = "093QyB0w3zl3b0320Q1w3HZ5Sg2QyB0j";
    System.out.println("code is " + code);
    PostMethod postMethod = new PostMethod("https://api.weixin.qq.com/sns/jscode2session?appid=wx1d0725ec8a3d6d60&secret=b6a6be2820991cd5f521a1a9defecd38&js_code=" + code + "&grant_type=authorization_code");

    postMethod.addRequestHeader("accept", "*/*");
    //设置Content-Type，此处根据实际情况确定
    postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    String result = "";
    JSONObject data = new JSONObject();
    try {
      int status = httpClient.executeMethod(postMethod);
      if (status == 200){
        result = postMethod.getResponseBodyAsString();
        System.out.println("result:" + result);
        JSONObject json = JSON.parseObject(result);
        System.out.println(json);
        Integer errCode = json.getInteger("errcode");
        if(errCode != null) {
          String errMsg = json.getString("errmsg");
          return ApiResponse.error(errCode, errMsg, json);
        }
        //data = json.getJSONObject("data");
        data = json;
//        String openId = data.getString("openid");
//        String sessionKey = data.getString("session_key");

      }
    } catch (IOException e) {
      e.printStackTrace();
    }


    return ApiResponse.ok(data);
  }

  /**
   * 更新计数，自增或者清零
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @PostMapping(value = "/api/count")
  ApiResponse create(@RequestBody CounterRequest request) {
    logger.info("/api/count post request, action: {}", request.getAction());

    Optional<Counter> curCounter = counterService.getCounter(1);
    if (request.getAction().equals("inc")) {
      Integer count = 1;
      if (curCounter.isPresent()) {
        count += curCounter.get().getCount();
      }
      Counter counter = new Counter();
      counter.setId(1);
      counter.setCount(count);
      counterService.upsertCount(counter);
      return ApiResponse.ok(count);
    } else if (request.getAction().equals("clear")) {
      if (!curCounter.isPresent()) {
        return ApiResponse.ok(0);
      }
      counterService.clearCount(1);
      return ApiResponse.ok(0);
    } else {
      return ApiResponse.error("参数action错误");
    }
  }
  
}