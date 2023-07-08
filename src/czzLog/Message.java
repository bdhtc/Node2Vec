package czzLog;

import lombok.Data;

/**
 * 一条消息
 *
 * @author CZZ
 */
@Data
public class Message {

    /**
     * 消息
     */
    private String message;

    /**
     * 时间戳
     */
    private long timeMilli;

    /**
     * 构造方法
     */
    Message(String message) {
        this.message = message;
        timeMilli = System.currentTimeMillis();
    }
}
