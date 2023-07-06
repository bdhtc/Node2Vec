package czzNode2Vec;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import czzGraph.Edge;
import czzGraph.Graph;

/**
 GraphForNodeToVec.ר��ΪNode2Vec��д��ͼ����������ͼ��Graph��ģ����N2Vģ������䡣ʵ�ֽӿ�IGraph��
 @author CZZ*/
public class Graph4N2V<T> /*extends Graph */implements IGraph{
	
	private Graph<T> _G;
	
	/**
	 ���캯��*/
	public Graph4N2V(){
		this._G = new Graph<T>(false, false);		//Ĭ��Ϊ������Ȩͼ
	}

	/**
	 ���Ӵ�Graph���챾��ķ���*/
	public Graph4N2V(Graph<T> G, boolean isDirected, boolean isWeighted){
		this._G = G;
		if(isDirected) G.toDirected();
		else {
			if(G.isDirected()) G.toUndirected(true);			//����ͼת��Ϊ����ͼ��ȥ������ߵķ��򣨲��䷴��ߣ�
		}
		if(isWeighted) G.toWeighted();
	}
	
	/**
	 ���ļ�װ��ͼ
	 @param file �ļ�·��
	 @param fileType �ļ����ͣ���չ����
	 @param isDirected ����ͼ
	 @param isWeighted ��Ȩͼ
	 @return װ�ؽ���� true��װ�سɹ�,false��װ��ʧ��*/
	@Override
	public boolean loadGraphFromEdgelistFile(String file, String split, boolean isDirected, boolean isWeighted) {
		boolean ret = false;
		ret = this._G.loadGraphFromEdgeListFile(file, split, isDirected, isWeighted);
		return ret;
	}
	

	@Override
	public int getNodeNumber() {
		return this._G.getNodeNumber();
	}
	
	/**
	 ���idΪ��id���Ľڵ�
	 @param id ����ӵĽڵ��id
	 @return true����ӳɹ�*/
	@Override
	public boolean addNode(int id) {
		return this._G.addNode(id);
	}
	
	/**
	 �Ƴ�idΪ��id���Ľڵ�
	 @param id ���Ƴ��Ľڵ��id
	 @return true���Ƴ��ɹ�*/
	@Override
	public boolean removeNode(int id) {
		boolean ret = false;
		if(this._G.removeNode(id) >= 0) ret = true;
		return ret;
	}
	
	/**
	 �Ƿ����ĳ��idΪ��id���Ľڵ�
	 @param id ������Ľڵ��id
	 @return true��ͼ����idΪid�Ľڵ�*/
	@Override
	public boolean hasNode(int id) {
		boolean ret = false;
		if(this._G.getNode(id) != null) ret = true;
		return ret;
	}
	
	/**
	 ���һ����id1�ڵ㵽id2�ڵ��ȨֵΪweight�ı�
	 @param id1 ��ʼ�ڵ�id
	 @param id2 ����Ľڵ��id
	 @param weight �ߵ�Ȩֵ
	 @return true����Ӳ����ɹ�ִ�У�false�����Ѿ����ڻ�������ԭ��*/
	@Override
	public boolean addEdge(int id1, int id2, Integer weight) {
		return this._G.addEdge(id1, id2, weight);
	}
	
	/**
	 �Ƴ���id1�ڵ㵽id2�ڵ�ıߣ���Ϊ����ͼ��ͬʱɾ����id2-id1
	 @param id1 ��ʼ�ڵ�
	 @param id2 ����Ľڵ�
	 @return true:ɾ�������ɹ�ִ�У�false���߲����ڻ�������ʧ��ԭ��*/
	@Override
	public boolean removeEdge(int id1, int id2) {
		return this._G.removeEdge(id1, id2);
	}
	
	/**
	 �Ƿ���д�id1��id2�ı�
	 @param id1 ��ʼ�ڵ�id
	 @param id2 ����Ľڵ��id
	 @return true:ͼ�д��ڴ�id1�ڵ㵽��id2�ڵ��·��*/
	@Override
	public boolean hasEdge(int id1, int id2) {
		boolean ret = false;
		if(this._G.getEdge(id1, id2) != null) ret = true;
		return ret;
	}
	
	/**
	 ��ȡ�ߵ�Ȩֵ
	 @param id1 ��ʼ�ڵ�id
	 @param id2 ����Ľڵ��id
	 @return ����ߴ����򷵻رߵ�Ȩֵ��������null��߲�����*/
	@Override
	public Integer getEdgeWeight(int id1, int id2) {
		Integer ret = null;
		Edge<T> edge = this._G.getEdge(id1, id2);
		if(edge != null) ret = edge.weight();
		return ret;
	}
	
	/**
	 �޸ıߵ�Ȩֵ������ͼ���޸�˫��Ȩֵ���ߴ��ڲ����޸ģ����������addEdge������
	 @param id1 ��ʼ�ڵ�id
	 @param id2 ����Ľڵ��id
	 @param weight �ߵ�Ȩֵ
	 @return �޷���ֵ������ǰ�����Ƿ����*/
	@Override
	public void setEdgeWeight(int id1, int id2, Integer weight) {
		this._G.setEdgeWeight(id1, id2, weight);
	}
	
	/**
	 �ж�ĳ���ڵ㣨id�����Ե�����ھ�
	 @param id ĳ���ڵ��id
	 @return idΪid�Ľڵ���ھ�����*/
	@Override
	public Integer[] neighbors(int id) {
		czzGraph.Node<T> node = this._G.getNode(id);
		int n = node.getOut_Degree();
		ArrayList<Integer> nodelist = new ArrayList<Integer>();
		Iterator<Integer> iter = node.getOutEdgeList().keySet().iterator();
		while (iter.hasNext()) {
			nodelist.add(iter.next());
		}
		//nodelist.sort();
		Integer[] ret = new Integer[n];
		return nodelist.toArray(ret);
	}
	
	/**
	 ����ͼ�Ľڵ�����
	 @return ͼ�����нڵ��id���ɵ�����*/
	public Integer[] nodesArray() {
		List<Integer> nodesList = this._G.getNodesIdList();
		Integer[] ret = new Integer[nodesList.size()];
		return nodesList.toArray(ret);
	}
	
	/**
	 ������ͼ*/
	@Override
	public void clear() {
		this._G.clear();
	}
}
