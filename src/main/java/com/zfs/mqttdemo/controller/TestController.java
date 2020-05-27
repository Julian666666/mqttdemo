package com.zfs.mqttdemo.controller;

import com.zfs.mqttdemo.gateway.MqttGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public String sendMqtt(/*String id, String content*/){
//        byte a = 0;
//        byte b = 0;
//        List<Byte> byteList = new ArrayList<>();
//        if ("05".equals(id)) {
//            Byte[] bytes = {0x05, (byte)0xff, (byte)0xff, 0x01, 0x00};
//            byteList.addAll(bytes);
//        }
        byte[] x = {0x05, (byte)0xff, (byte)0xff, 0x03, 0x00};
//        byte[] x = {0x07};
//        byte[] x = {(byte)0xAE, 0x04, 0x00, 0x01, 0x01, 0x0A, 0x00};
//        byte[] x = {0x6F, 0x07, 0x00};
//        byte[] x = {0x1e, (byte)0xff, (byte)0xff, 0x03, 0x00};
//        byte[] x = {0x0d, (byte)0x10, (byte)0x04, (byte)0xd6, 0x01, 0x00, (byte)0x10, (byte)0x9c, (byte)0x00, (byte)0x00, (byte)0x08};
        mqttGateway.sendToMqtt(x,"IMEI/864626043637690/TD");
        return "OK";
    }

}