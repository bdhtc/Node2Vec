package czzGraph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
ͼ�еĽڵ�,TΪ�ڵ�װ�ص����ݵ�����
@author CZZ*/
public class Node<T> {

	/**
	 ��Ż��߽ڵ��־��*/
	private int _id;
	
	/**
	 �ڵ����*/
	public String name;
	
	/**
	 * ����ΪT�Ľڵ������*/
	T element;
	
	/**
	  �������Ե���Ľڵ�*/
	private HashMap<Integer, Node<T>> _inNodeList;
	
	/**
	  ���ڵ�����Щ����*/
	private HashMap<Integer, Edge<T>> _outEdgeList;
	
	/**
	  �ڵ����ڵ�ͼ*/
	//private Graph location;
	
	/**
	 ���*/
	private int _in_Degree;
	
	/** 
	 ����*/
	private int _out_Degree;
	
	/** 
	 �� */
	private int _degree;

	/*================================���� methods================================*/
	
	/**
	 �ڵ�Ĺ��캯��
	 @param id �ڵ�id*/
	public Node(int id){
		this._id = id;
		this.name = null;
		this._in_Degree = 0;
		this._out_Degree = 0;
		this._degree = 0;
		_inNodeList = new HashMap<Integer, Node<T>>();
		_outEdgeList = new HashMap<Integer, Edge<T>>();
	}
	
	/**
	 �ڵ�Ĺ��캯��
	 @param id �ڵ�id
	 @param name Ԥ��ڵ�����*/
	public Node(int id, String name){
		this._id = id;
		this.name = name;
		this._in_Degree = 0;
		this._out_Degree = 0;
		this._degree = 0;
		_inNodeList = new HashMap<Integer, Node<T>>();
		_outEdgeList = new HashMap<Integer, Edge<T>>();
	}
	
	/**
	 �ڵ�Ĺ��캯��
	 @param id �ڵ�id
	 @param name Ԥ��ڵ�����
	 @param element �ڵ��а�����Ԫ��*/
	public Node(int id, String name, T element){
		this._id = id;
		this.name = name;
		this.element = element;
		this._in_Degree = 0;
		this._out_Degree = 0;
		this._degree = 0;
		_inNodeList = new HashMap<Integer, Node<T>>();
		_outEdgeList = new HashMap<Integer, Edge<T>>();
	}
	
	/**
	 @return �ڵ�����*/
	public int getIn_Degree() {
		return _in_Degree;
	}

	/**
	 @return �ڵ�ĳ���*/
	public int getOut_Degree() {
		return _out_Degree;
	}
	
	/**
 	 @return ��ȡ�ڵ�Ķȣ����+���ȣ�*/
	public int getDegree() {
		_degree = this._in_Degree + this._out_Degree;
		return _degree;
	}

	/**
	 @return �ڵ�id*/
	public int getId() {
		return _id;
	}

	/**
	 @return ���Ե���˽ڵ�Ľڵ��б�*/
	public HashMap<Integer, Node<T>> getInNodeList(){
		return this._inNodeList;
	}
	
	/**
	 @return �����б�*/
	public HashMap<Integer, Edge<T>> getOutEdgeList(){
		return this._outEdgeList;
	}
	
	/**
	 ��¼����ǰ�ڵ����ͨ����v1ev2����ڵ�v2*/
	public boolean arriveAt(Edge<T> v1ev2) {
		boolean notexist = false;
		int v2id = v1ev2.getV2().getId();
		if(this._outEdgeList.get(v2id) == null){
			this._outEdgeList.put(v2id, v1ev2);
			this._out_Degree++;
			notexist = true;
		}
		return notexist;
	}
	
	/**
	 ��¼��ĳ���ڵ�v1���Ե��ﵱǰ�ڵ�*/
	public boolean comeFrom(Node<T> v1) {
		boolean notexist = false;
		int v1id =v1.getId();
		if(this._inNodeList.get(v1id) == null){
			this._inNodeList.put(v1id, v1);
			this._in_Degree++;
			notexist = true;
		}
		return notexist;
	}
	
	/**
	 ͼ��ɾ��ĳ���ڵ�֮��v�������ڵ���Ӧ��ɾ���������ж�������ڵ����ϵ
	 @param v ͼ�б�ɾ���Ľڵ�v*/
	public int removeNode(Node<T> v) {
		int id = v.getId();
		return removeNode(id);
	}
	
	/**
	 ͼ��ɾ��ĳ���ڵ㣨id��֮�������ڵ���Ӧ��ɾ���������ж�������ڵ����ϵ
	 @param id ͼ�б�ɾ���Ľڵ��id*/
	public int removeNode(int id) {
		int ret = 0;
		Node<T> inNode = _inNodeList.get(id);
		if(inNode != null) {
			_inNodeList.remove(id);
			this._in_Degree--;
			ret += 1;
		}
		Edge<T> outEdge = _outEdgeList.get(id);
		if(outEdge != null) {
			_outEdgeList.remove(id);
			this._out_Degree--;
			ret += 2;
		}
		return ret;
	}
	
	/**
	 ɾ���ڽڵ��б���ıߵ�����
	 @param id1 �ߵ�����id
	 @param id2 �ߵ��յ��id
	 */
	public boolean removeEdge(int id1, int id2) {
		boolean ret = false;
		if(id1 == this._id) {			//�ߵ�������Լ�
			if(_outEdgeList.get(id2) != null) {
				_outEdgeList.remove(id2);
				this._out_Degree--;
				ret = true;
			}
		}
		if(id2 == this._id) {			//�ߵ��յ����Լ�
			if(_inNodeList.get(id1) != null) {
				this._inNodeList.remove(id1);
				this._in_Degree--;
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 ɾ���ڽڵ��б���ıߵ�����
	 @param v1 ���
	 @param v2 �յ�
	 */
	public boolean removeEdge(Node<T> v1, Node<T> v2) {
		int id1 = v1.getId();
		int id2 = v2.getId();
		return removeEdge(id1, id2);
	}
	
	/**
	 ɾ���ڽڵ��б���ı�vv������
	 @param vv ��ɾ���ı�*/
	public boolean removeEdge(Edge<T> vv) {
		int id1 = vv.getV1().getId();
		int id2 = vv.getV2().getId();
		return removeEdge(id1, id2);
	}
	
	/**
	 �жϽڵ㣨id����˽ڵ�Ĺ�ϵ
	 @param id ���жϵĽڵ��id
	 @return 0���ɴ1id�ڵ㵽��˽ڵ㣬2�˽ڵ���Ե���id�ڵ㣬3����ɴ�*/
	public int judgeNode(int id) {
		int ret = 0;
		Node<T> in = this._inNodeList.get(id);
		Edge<T> out = this._outEdgeList.get(id);
		if(in != null) {
			if(out != null) ret = 3;
			else ret = 1;
		}
		else {
			if(out != null) ret = 2;
			//else ret = 0;
		}
		return ret;
	}
	
	/**
	  ͼɾ���˽ڵ�ʱ�����ô˷�����֪ͨ�ܱ߽ڵ�Ͽ���˽ڵ����ϵ*/
	public boolean beforeDeleteSelf() {
		Iterator<Entry<Integer, Node<T>>> nodeIter = this._inNodeList.entrySet().iterator();
		Map.Entry<Integer, Node<T>> nodeEntry;
		while (nodeIter.hasNext()) {
			nodeEntry = (Map.Entry<Integer, Node<T>>) nodeIter.next();
			((Node<T>)nodeEntry.getValue()).removeNode(this);			//���Ե���˽ڵ�Ľڵ㣬ɾ���˽ڵ������
		}
		_inNodeList.clear();									//�ͷ��Լ��ģ���ڵ㣩��¼
		Iterator<Entry<Integer, Edge<T>>> edgeIter = this._outEdgeList.entrySet().iterator();
		Map.Entry<Integer, Edge<T>> edgeEntry;
		while (edgeIter.hasNext()) {
			edgeEntry = (Entry<Integer, Edge<T>>) edgeIter.next();
			((Edge<T>)edgeEntry.getValue()).getV2().removeNode(this);		//�˽ڵ���Ե���Ľڵ㣬ɾ���˽ڵ������
		}
		_outEdgeList.clear();									//�ͷ��Լ��ģ����ڵ㣩��¼
		return true;
	}
}
