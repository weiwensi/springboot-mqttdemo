package com.gysoft.emqdemo.util;

/**
 * @author 魏文思
 * @date 2019/11/15$ 10:27$
 */
public enum QosType {

    QOS_AT_MOST_ONCE(0, "最多一次，有可能重复或丢失"),
    QOS_AT_LEAST_ONCE(1, "至少一次，有可能重复"),
    QOS_EXACTLY_ONCE(2, "只有一次，确保消息只到达一次");
    private int number;
    private String desc;

    QosType(int num, String desc) {
        this.number = num;
        this.desc = desc;
    }

    public int getNumber() {
        return number;
    }


    public String getDesc() {
        return desc;
    }

}
