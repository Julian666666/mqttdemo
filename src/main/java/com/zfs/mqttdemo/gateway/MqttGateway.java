package com.zfs.mqttdemo.gateway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @InterfaceName MqttGateway.java
 * @Description 消息推送接口类
 * @Author 朱福盛
 * @Date 2020/4/29 10:24
 * @Version 1.0
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}