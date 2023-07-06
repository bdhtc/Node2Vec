package czzClusterAnalysis;

import czzVector.CVector;
import czzVector.IVector;

/**
 * ����ڵ㣬���ݽṹ
 * @author CZZ*/
public class ClusterNode<T> {

	/**
	 * ������������*/
	public T name;
	
	/**
	 * ����*/
	public IVector _vector;
	
	/**
	 * �����ǩ*/
	public int label;
	
	/*================================���� methods================================*/
	
	/**
	 * ���췽��*/
	public ClusterNode(T name, IVector _vector) {
		this.name = name;
		this._vector = new CVector();
		this._vector.copy(_vector);
		this.label = -1;
	}
}
