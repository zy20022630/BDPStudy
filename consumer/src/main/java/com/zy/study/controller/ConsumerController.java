package com.zy.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.zy.study.QueryDO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    public static final String QUERY_LIST_URL = "http://PROVIDER/provider/queryBehaviorList/";

    @Resource
    private RestTemplate restTemplate;

    //测试版本：V0.11
    @Resource
    private HttpHeaders httpHeaders;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @ApiOperation(value = "获取用户行为日志信息列表", notes = "给定任意用户ID 以及 时间区间，输出该时间内该用户浏览和购买过的商品（包括购买时间、商品类目、品牌名等）")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户ID（格式：1000205）", required=true, dataType="String"),
            @ApiImplicitParam(name="startTime", value="开始时间（格式：2017-05-03 17:55:01）", required=true, dataType="String"),
            @ApiImplicitParam(name="endTime", value="截止时间（格式：2017-05-03 17:55:51）", required=true, dataType="String")
    })
    @RequestMapping(value = "/queryBehaviorList", method = RequestMethod.POST)
    public Object queryBehaviorList(HttpServletRequest request) throws Exception{
//        String context = HttpUtil.getContentFromRequest(request.getInputStream(), "UTF-8");
//        JSONObject jsonObject = JSONObject.parseObject(context);
//
//        String userId = jsonObject.getString("userId");
//        String startTime = jsonObject.getString("startTime");
//        String endTime = jsonObject.getString("endTime");

//        String userId = jsonObject.getString("userId");
//        String startTime = jsonObject.getString("startTime");
//        String endTime = jsonObject.getString("endTime");


//        QueryDO queryDO = new QueryDO();
//        queryDO.setUserId(userId);
//        queryDO.setStartTime(startTime);
//        queryDO.setEndTime(endTime);
//        queryDO.setFamilyColumns("ipv,buy");
        QueryDO queryDO = new QueryDO();
        queryDO.setUserId("1000205");
        queryDO.setStartTime("2017-05-03 17:55:01");
        queryDO.setEndTime("2017-05-03 17:55:51");
        queryDO.setFamilyColumns("ipv,buy");

        queryDO.setQualifierColumns(null);
        //return restTemplate.exchange(QUERY_LIST_URL, HttpMethod.POST, new HttpEntity<Object>(queryDO, httpHeaders), List.class).getBody();
        return restTemplate.postForObject(QUERY_LIST_URL, queryDO, List.class);
    }

    @ApiOperation(value = "获取用户行为日志信息列表", notes = "给定用户ID=1000205,时间区间(2017-05-03 17:55:01-2017-05-03 17:55:51)，输出该时间内该用户浏览和购买过的商品（包括购买时间、商品类目、品牌名等）")
    @RequestMapping(value = "/testQueryBehaviorList", method = RequestMethod.GET)
    public Object testQueryBehaviorList() {
        QueryDO queryDO = new QueryDO();
        queryDO.setUserId("1000205");
        queryDO.setStartTime("2017-05-03 17:55:01");
        queryDO.setEndTime("2017-05-03 17:55:51");
        queryDO.setFamilyColumns("ipv,buy");
        //return restTemplate.exchange(QUERY_LIST_URL, HttpMethod.POST, new HttpEntity<Object>(queryDO, httpHeaders), List.class).getBody();
        return restTemplate.postForObject(QUERY_LIST_URL, queryDO, List.class);
    }

}