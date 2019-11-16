package com.gysoft.emqdemo.server;

import com.gysoft.emqdemo.bean.PushPayload;
import com.gysoft.emqdemo.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

/**
 *
 * @author 魏文思
 * @date 2019/11/14$ 15:55$
 */
@Slf4j
@Component
public class MqttPushServer {

    private MqttClient client;

    private static volatile MqttPushServer mqttPushClient = null;
    //重连次数
    private int reConnTimes;

    public int getReConnTimes() {
        return this.reConnTimes;
    }

    public void setReConnTimes(int reConnTimes) {
        if (this.isConnected()) {
            reConnTimes = 0;
        }

        this.reConnTimes = reConnTimes;
    }

    public int getMaxReconnTimes() {
        return PropertiesUtil.MQTT_MAXRECONNECTTIMES;
    }

    public int getReconnInterval() {
        return PropertiesUtil.MQTT_RECONNINTERVAL;
    }

    public static MqttPushServer getInstance(){

        if(null == mqttPushClient){
            synchronized (MqttPushServer.class){
                if(null == mqttPushClient){
                    mqttPushClient = new MqttPushServer();
                }
            }

        }
        return mqttPushClient;

    }

    private MqttPushServer() {
        connect();
    }

    public void connect(){
        try {
            client = new MqttClient(PropertiesUtil.MQTT_HOST, PropertiesUtil.MQTT_CLIENTID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            /**
             * clean session 值为false,既保留会话，那么该客户端上线的时候，并订阅了主题“r”,那么该主题会一直存在，即使客户端离线，该主题也仍然会记忆在EMQ服务器内存。
             * 当客户端离线又上线时，仍然会接受到离线期间别人发来的publish消息（QOS=0,1,2）.类似及时通讯软件，终端可以接受离线消息。
             * 除非客户端主动取消订阅主题， 否则主题一直存在。另外，mnesia不会持久化session，subscription和topic，服务器重启则丢失。

             * 当clean session 为true
             * 该客户端上线，并订阅了主题“r”，那么该主题会随着客户端离线而删除。
             * 当客户端离线又上线时，接受不到离线期间别人发来的publish消息
             *
             * 不管clean session的值是什么，当终端设备离线时，QoS=0,1,2的消息一律接收不到。
             * 当clean session的值为true，当终端设备离线再上线时，离线期间发来QoS=0,1,2的消息一律接收不到。
             * 当clean session的值为false，当终端设备离线再上线时，离线期间发来QoS=0,1,2的消息仍然可以接收到。如果同个主题发了多条就接收多条，一条不差，照单全收
             *
             */
            options.setCleanSession(false);
            /*options.setUserName(PropertiesUtil.MQTT_USER_NAME);
            options.setPassword(PropertiesUtil.MQTT_PASSWORD.toCharArray());*/
            options.setConnectionTimeout(PropertiesUtil.MQTT_TIMEOUT);
            options.setKeepAliveInterval(PropertiesUtil.MQTT_KEEP_ALIVE);
            try {
                client.setCallback(new PushCallback());
                client.connect(options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发布，默认qos为0，非持久化
     * @param topic
     * @param pushMessage
     */
    public void publish(String topic,PushPayload pushMessage){
        publish(0, false, topic, pushMessage);
    }

    /**
     * 发布
     * @param qos
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos,boolean retained,String topic,PushPayload pushMessage){
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.toString().getBytes());
        MqttTopic mTopic = client.getTopic(topic);
        if(null == mTopic){
            log.error("topic not exist");
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题，qos默认为0
     * @param topic
     */
    public void subscribe(String topic){
        subscribe(topic,0);
    }

    /**
     * 订阅某个主题
     * @param topic
     * @param qos
     */
    public void subscribe(String topic,int qos){
        try {
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public boolean isConnected() {
        return client.isConnected();
    }

    public static void main(String[] args) throws Exception {
        String kdTopic = "demo/topics";
        PushPayload pushMessage = PushPayload.getPushPayloadBuider().setMobile("17637900215")
                .setContent("designModel")
                .bulid();
        MqttPushServer.getInstance().publish(0, false, kdTopic, pushMessage);

    }
}
