package czzSelectItem;

/**
 �����Զ����ݽṹ����Ӧpython�����е�J��q
 @author CZZ*/
public class ProbabilityPair {
	
	/**
	 ������*/
	public float prob;
	
	/**
	 �±�*/
	public int index;
	
	public String toString() {
		StringBuilder str = new StringBuilder("[J:" + index + "|p:" + prob + "]");
		return str.toString();
	}
}
