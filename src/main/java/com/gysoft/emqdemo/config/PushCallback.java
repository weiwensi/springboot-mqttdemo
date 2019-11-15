package com.gysoft.emqdemo.config;

import com.gysoft.emqdemo.client.MqttPushClient;
import com.gysoft.emqdemo.util.NetUtils;
import com.gysoft.emqdemo.util.PropertiesUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.TimeUnit;


/**
 * @author 魏文思
 * @date 2019/11/14$ 15:57$
 */
public class PushCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable throwable) {
       /* MqttPushClient mqttClient = MqttPushClient.getInstance();
        //在断开连接时使用，主要用于重连
        do {
            if (NetUtils.connectTest(PropertiesUtil.prefixUrl)) {
                mqttClient.connect();
                mqttClient.setReConnTimes(mqttClient.getReConnTimes() + 1);
            }

            try {
                TimeUnit.SECONDS.sleep((long) mqttClient.getReconnInterval());
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        } while (!mqttClient.isConnected() && mqttClient.getReConnTimes() < MqttPushClient.getInstance().getMaxReconnTimes());
*/

    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
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
