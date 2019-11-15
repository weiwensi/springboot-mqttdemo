package com.gysoft.emqdemo.client;

import com.gysoft.emqdemo.bean.PushPayload;
import com.gysoft.emqdemo.config.PushCallback;
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
public class MqttPushClient {

    private MqttClient client;

    private static volatile MqttPushClient mqttPushClient = null;
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

    public static MqttPushClient getInstance(){

        if(null == mqttPushClient){
            synchronized (MqttPushClient.class){
                if(null == mqttPushClient){
                    mqttPushClient = new MqttPushClient();
                }
            }

        }
        return mqttPushClient;

    }

    private MqttPushClient() {
        connect();
    }

    public void connect(){
        try {
            client = new MqttClient(PropertiesUtil.MQTT_HOST, PropertiesUtil.MQTT_CLIENTID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
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
        MqttPushClient.getInstance().publish(0, false, kdTopic, pushMessage);

    }
}
