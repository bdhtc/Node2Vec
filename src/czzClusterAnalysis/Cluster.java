package czzClusterAnalysis;

import czzVector.IVector;

import java.util.ArrayList;

/**
 * �����������
 *
 * @author CZZ
 */
public abstract class Cluster<T> {

    /**
     * �ȴ�����Ľڵ�*/
    protected ArrayList<ClusterNode<T>> nodes;
	
	/*================================���� methods================================*/
	
	/**
	 * ���췽��*/
	public Cluster() {
        nodes = new ArrayList<>();
	}
	
	/**
	 * ��Ӵ�����ڵ�
	 * @param name �����������ƻ��߱��
	 * @param v �ڵ�����
	 * @return ��ӳɹ�����ʱ���ù�ϣ���ֹ�ظ����룩*/
	public boolean addNode(T name, IVector v) {
		nodes.add(new ClusterNode<T>(name, v));
		return true;
	}
	
	/**
	 * ��ʼ����*/
	public abstract boolean runCluster(int k);
	
	public ArrayList<ClusterNode<T>> getNodes() {
		return nodes;
	}
}
