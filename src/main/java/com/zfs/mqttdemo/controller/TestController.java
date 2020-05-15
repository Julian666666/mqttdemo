package com.zfs.mqttdemo.controller;

import com.zfs.mqttdemo.gateway.MqttGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName TestController.java
 * @Description 测试
 * @Author 朱福盛
 * @Date 2020/4/29 10:27
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private MqttGateway mqttGateway;

    @RequestMapping("/sendMqtt")
    public String sendMqtt(String sendData, String topic){
        mqttGateway.sendToMqtt(sendData,topic);
        return "OK";
    }
}