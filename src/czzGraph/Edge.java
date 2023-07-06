package czzGraph;

/**
 ͼ�ı�
@author CZZ*/
public class Edge<T> {
	
	/**
	 ���*/
	private Node<T> _v1;
	
	/**
	 �յ�*/
	private Node<T> _v2;
	
	/**
	 �ߵ�Ȩ��
	 ��ȨͼȨ���߿����ã���Ȩͼ0��1*/
	private Integer _weight;
	
	/**
	 �ڵ����ڵ�ͼ*/
	//private Graph location;
	
	/*================================���� methods================================*/
	
	/**
	 ���췽��*/
	public Edge(Node<T> v1, Node<T> v2, Integer weight) {
		this._v1 = v1;
		this._v2 = v2;
		this._weight = weight;
	}
	
	/**
	 @return v1�ڵ�����*/
	public Node<T> getV1() {
		return _v1;
	}
	
	/**
	 @return v2�ڵ�����*/
	public Node<T> getV2() {
		return _v2;
	}
	
	/**
	 @return �ߵ�Ȩֵ*/
	public Integer weight() {
		return _weight;
	}
	
	/**
	 �޸ıߵ�Ȩֵ����ֻ��һ�����ݽṹ����ͼ�в��м�ֵ���Ƽ�����ͼ��setEdgeWeight��������������ͼ���޸����⣩
	 @deprecated*/
	public void setWeight(Integer weight) {
		this._weight = weight;
	}
}
