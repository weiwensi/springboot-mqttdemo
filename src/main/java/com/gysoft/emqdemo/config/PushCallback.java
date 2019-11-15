package com.gysoft.emqdemo.config;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


/**
 * @author 魏文思
 * @date 2019/11/14$ 15:57$
 */
public class PushCallback   implements MqttCallback {

    @Override
    public void connectionLost(Throwable throwable) {
        //在断开连接时使用，主要用于重连
        do {

        } while(true/*!this.gyMqttClient.isConnected() && this.gyMqttClient.getReConnTimes() < this.gyMqttClient.getMaxReconnTimes()*/);

        //this.logger.error("mqtt connectionLost error={}...", throwable);
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
       // System.out.println("deliveryComplete---------" + token.isComplete());
    }
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
       //服务端不用关心，客户端的业务
        // subscribe后得到的消息会执行到这里面
       /* System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload()));*/

    }
}
