package com.gysoft.emqdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 魏文思
 * @date 2019/11/14$ 15:50$
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.gysoft.emqdemo.config")
public class MqttConfiguration {

    private String host;

    private String clientid;

    private String topic;

    /*private String username;

    private String password;*/

    private int timeout;

    private int keepalive;

}
