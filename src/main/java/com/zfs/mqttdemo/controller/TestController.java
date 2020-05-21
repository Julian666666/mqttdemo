package com.zfs.mqttdemo.controller;

import com.zfs.mqttdemo.gateway.MqttGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

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
//        byte[] sd = {0, 5, 'f', 'f', 'f', 'f', '0', '4', '0', '0'};
        byte[] x = {0x05, (byte)0xff, (byte)0xff, 0x00, 0x00};
//        byte[] x = {0x1e, (byte)0xff, (byte)0xff, 0x03, 0x00};
        System.out.println(Arrays.toString(x));
        mqttGateway.sendToMqtt(x,"IMEI/864626043637690/TD");
        return "OK";
    }
}