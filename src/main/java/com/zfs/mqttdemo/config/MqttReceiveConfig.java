package com.zfs.mqttdemo.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @ClassName MqttReceiveConfig.java
 * @Description mqtt订阅类
 * @Author 朱福盛
 * @Date 2020/4/29 10:09
 * @Version 1.0
 */
@Configuration
@IntegrationComponentScan
public class MqttReceiveConfig {

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.default.topic}")
    private String defaultTopic;

    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout ;   //连接超时


    @Bean
    public MqttConnectOptions getMqttConnectOptions(){
        MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{hostUrl});
        mqttConnectOptions.setKeepAliveInterval(2);
        return mqttConnectOptions;
    }
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    //接收通道
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    //配置client,监听的topic
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId+"_inbound", mqttClientFactory(),
                        /*"IMEI/864626043637690/FD", */"IMEI/868334033327861/FD");
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    //通过通道获取数据
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
                String type = topic.substring(topic.lastIndexOf("/")+1, topic.length());
                if("IMEI/864626043637690/FD".equalsIgnoreCase(topic)){
//                    String str = new String((byte[]) message.getPayload());
                    System.out.println("test,IMEI/864626043637690/FD,"+ message.getPayload().toString());
                }else if("IMEI/868334033327861/FD".equalsIgnoreCase(topic)){
                    DefaultPahoMessageConverter dpmc = new DefaultPahoMessageConverter();
                    MqttMessage mqttMessage = dpmc.fromMessage(message, MqttMessage.class);
                    String s = message.getPayload().toString();
                    byte[] bytes = s.getBytes();
                    char[] chars = message.getPayload().toString().toCharArray();
//                    char[] chars = message.getPayload();
//                    System.out.println("test,IMEI/868334033327861/FD,"+message.getHeaders().toString() + ":" + message.getPayload().toString());
//                    if (bytes[0] == 11) {
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("cmdType", bytes[0]);
//                        map.put("len", bytes[2] + bytes[1]);
//                        map.put("CSQ", bytes[3]);
//                        map.put("sceneId", bytes[5] + bytes[4]);
//                        map.put("TimePlanID", bytes[6]);
//                        map.put("CommType", bytes[7]);
//                        map.put("outputNum", bytes[8]);
//                        map.put("temperature", bytes[9]/2.5);
//                        map.put("D0", bytes[10]);
//                        map.put("dim", bytes[11]);
//                        map.put("energy1", bytes[15] + bytes[14] + bytes[13] + bytes[12]);
//                        short v = (short) bytes[16];
//                        System.out.println(bytes[16]);
//                        System.out.println(bytes[17]);
//                        System.out.println(v);
//                        map.put("voltage", (bytes[17] + bytes[16 ])/100.00);
//                        map.put("current", (bytes[19] + bytes[18])/100.00);
//                        map.put("power", (bytes[21] + bytes[20])/100.00);
//                        map.put("pf", (bytes[23] + bytes[22])/100.00);
//                        map.put("lampOnTime", bytes[27] + bytes[26] + bytes[25] + bytes[24]);
//                        System.out.println(map);
//                        int a = chars[17] + chars[16];
//                        System.out.println((int) chars[16]);
//                        System.out.println((int) chars[17]);
//                        System.out.println(chars[17] + chars[16]);
//                        System.out.println(a);
//                        System.out.println(chars.length);
//
//                        long l = Long.parseLong(String.valueOf(chars[16]), 16);
//                        System.out.println(l);

//                    }
                    System.out.println("-----------------");

                } else {
                    System.out.println(message.getPayload().toString());
                }
            }
        };
    }

}