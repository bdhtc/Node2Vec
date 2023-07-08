package czzLog;

import java.util.ArrayList;

/**
 * 日志
 * @author CZZ*/
public class Log {

	public static ArrayList<Message> logList = new ArrayList<>();
	
	/*================================方法 methods================================*/
	
	public static void clear() {
		logList.clear();
	}
	
	public static void addMessage(String message) {
		logList.add(new Message(message));
	}
	
	public static String getAll() {
        StringBuilder ret = new StringBuilder();
        long time0;
		if(logList.size() == 1) {
			ret.append(logList.get(0));
		}
		else if(logList.size() > 1) {
            time0 = logList.get(0).getTimeMilli();
            for (Message message : logList) {
                ret.append((message.getTimeMilli() - time0)).append(":").append(message.getMessage()).append("\n");
            }
        }
		return ret.toString();
	}
}
