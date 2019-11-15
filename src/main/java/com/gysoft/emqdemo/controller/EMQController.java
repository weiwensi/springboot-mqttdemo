package com.gysoft.emqdemo.controller;

import com.gysoft.emqdemo.bean.PushPayload;
import com.gysoft.emqdemo.client.MqttPushClient;
import com.gysoft.emqdemo.util.QosType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


/**
 * @author 魏文思
 * @date 2019/11/15$ 14:36$
 */
@RestController
public class EMQController {


    @GetMapping("/sendMessage")
    public String testMQ() {
        String kdTopic = "demo/topics";
        PushPayload pushMessage = PushPayload.getPushPayloadBuider().setMobile("17637900215")
                .setContent("designModel")
                .bulid();
        /**
         * mqtt发布消息的时候，可以设置保留消息标志，保留消息会驻留在消息服务器，后来的订阅主题仍然可以接受该消息
         * 关于retain的说明：
         * 终端设备publish消息时，如果retain值是true，则会服务器一直记忆，哪怕是服务重启。因为Mnesia会本地持久化。
         * publish某主题的消息，payload为空且retain值是true，则会删除这条持久化的消息。
         *
         * publish某主题的消息，payload为空且retain值是false，则不会删除这条持久化的消息。

         QOS_AT_MOST_ONCE(0, "最多一次，有可能重复或丢失"),
         QOS_AT_LEAST_ONCE(1, "至少一次，有可能重复"),
         QOS_EXACTLY_ONCE(2, "只有一次，确保消息只到达一次");

         */
        //这里将消息异步处理  使用futuretask，或者使用rabbimq进行异步处理或者spring的异步机制进行处理
        FutureTask futureTask = new FutureTask(() -> {
            MqttPushClient.getInstance().publish(QosType.QOS_AT_LEAST_ONCE.getNumber(), true, kdTopic, pushMessage);
            return true;
        });
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(futureTask);
        try {
            Boolean result = (Boolean) futureTask.get();
            if (result == true) {
                System.out.println("消息发送成功");
            } else {
                System.out.println("消息推送异常");
            }
        } catch (Exception e) {
            System.out.println("消息推送异常");
            e.printStackTrace();
        }
        return "ok";
    }

}
