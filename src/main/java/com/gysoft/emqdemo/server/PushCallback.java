package com.gysoft.emqdemo.server;

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
       /* MqttPushServer mqttPushServer = MqttPushServer.getInstance();
        //在断开连接时使用，主要用于重连
        System.out.println("开始判断是否进入重连");
        do {
            System.out.println("进入重连");
            if (NetUtils.connectTest(PropertiesUtil.prefixUrl)) {
                mqttPushServer.connect();
                mqttPushServer.setReConnTimes(mqttPushServer.getReConnTimes() + 1);
            }

            try {
                TimeUnit.SECONDS.sleep((long) mqttPushServer.getReconnInterval());

            } catch (InterruptedException var3) {
                System.out.println("重连出现异常");
                var3.printStackTrace();
            }
        } while (!mqttPushServer.isConnected() && mqttPushServer.getReConnTimes() < mqttPushServer.getMaxReconnTimes());
        System.out.println("重试成功！！！");*/
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
