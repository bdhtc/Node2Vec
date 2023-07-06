package czzLog;

/**
 * һ����Ϣ
 * @author CZZ*/
public class Message {

	/**
	 * ��Ϣ*/
	String message;
	
	/**
	 * ʱ���*/
	long timeMilli;
	
	/*================================���� methods================================*/
	
	/**
	 * ���췽��*/
	Message(String message){
		this.message = message;
		timeMilli = System.currentTimeMillis();
	}
	
	/**
	 * ��ȡʱ���*/
	public long getTimeMilli() {
		return timeMilli;
	}
	
	/**
	 * ��ȡ��Ϣ*/
	public String getMessage() {
		return message;
	}
	
	/**
	 * ��Ϣת��Ϊ�ַ���*/
	public String toString() {
		return (timeMilli + ":" + message);
	}
}
