package czzLog;

import lombok.Data;

/**
 * һ����Ϣ
 *
 * @author CZZ
 */
@Data
public class Message {

    /**
     * ��Ϣ
     */
    private String message;

    /**
     * ʱ���
     */
    private long timeMilli;

    /**
     * ���췽��
     */
    Message(String message) {
        this.message = message;
        timeMilli = System.currentTimeMillis();
    }
}
