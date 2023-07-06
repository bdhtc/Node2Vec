package czzNode2Vec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import czzLog.Log;
import czzNode2Vec.NextWalkSelector.Algorithm;

/**
node2vec��ͼ���ϵı�ʾѧϰ�㷨��ܡ������κ�ͼ��������ѧϰ�ڵ������������ʾ��Ȼ��������ڸ������λ���ѧϰ����
@author CZZ*/
public class Node2Vec {
	
	/**
	 �������д洢��ʽ��ToArrayList���������������У�������С�ļ���ToFile��д���ļ��������ڴ�ͼ��Both�������ڴ棬ͬʱд�ļ�*/
	public enum WalkStorage {ToArrayList, ToFile, Both};
	
	/**
	 ͼ���ӿڣ�������ĺ���*/
	private IGraph _G;
	
	/**
	 ������p�����Ʊ����ķ���ǰһ���ڵ�ĸ��ʡ�Return hyperparameter.*/
	private float _p;
	
	/**
	 ������q�����ƽڵ������ǰ�еĸ��ʡ�Inout hyperparameter.*/
	private float _q;
	
	/**
	 ÿ��·���Ͻڵ��������Length of walk per source.*/
	private int _walk_length;
	
	/**
	 ��ͷ��β��������������Number of walks per source.*/
	private int _num_walks;
	
	/**
	 * �������д洢��ʽ*/
	WalkStorage walkStorage;
	
	/**
	 * ��������д���Ŀ���ļ�*/
	File storageFile;
	
	/**
	 �洢�ڵ㵽�ھӵĿ��������еĽṹ<�ڵ�id����һ��ѡ����>*/
	private HashMap<Integer, NextWalkSelector> _alias_nodes;
	
	/**
	 �洢�ߵĿ��������еĽṹ<��һ���ڵ�id,��ʱ�Ľڵ�id,��һ��ѡ����>*/
	private HashMap<Integer, HashMap<Integer, NextWalkSelector>> _alias_edges;
	
	/*================================���� methods================================*/
	
	/**
	 ���ִ��ͼ�й���*/
	public Node2Vec(IGraph _G, WalkStorage walkStorage, String walkStorageFileName) {
		this._G = _G;
		this._p = 1;
		this._q = 1;
		this._walk_length = 80;
		this._num_walks = 10;
		this.walkStorage = walkStorage;
		if(walkStorage != WalkStorage.ToArrayList) getStorageFile(walkStorageFileName);
		this._alias_nodes = new HashMap<Integer, NextWalkSelector>();
		this._alias_edges = new HashMap<Integer, HashMap<Integer, NextWalkSelector>>();
	}
	
	/**
	 ��ȡ������:p*/
	public float getP() {
		return this._p;
	}
	
	/**
	 �ı䳬����:p*/
	public void setP(float p) {
		this._p = p;
	}
	
	/**
	��ȡ������:q*/
	public float getQ() {
		return this._q;
	}
	
	/**
	 �ı䳬����:q*/
	public void setQ(float q) {
		this._q = q;
	}
	
	/**
	 ��ȡ���õ�ÿ��·���Ĳ���*/
	public int getWalk_length() {
		return _walk_length;
	}

	/**
	 ����ÿ��·���Ĳ���*/
	public void setWalk_length(int _walk_length) {
		this._walk_length = _walk_length;
	}

	/**
	 ��ȡ���õ�·������*/
	public int getNum_walks() {
		return _num_walks;
	}

	/**
	 ����·������*/
	public void setNum_walks(int _num_walks) {
		this._num_walks = _num_walks;
	}
	
	/**
	 ͬʱ���ø�������*/
	public void setParams(float p, float q, int _walk_length, int _num_walks) {
		this.setP(p);
		this.setQ(q);
		this.setWalk_length(_walk_length);
		this.setNum_walks(_num_walks);
	}
	
	private boolean getStorageFile(String walkStorageFileName) {
		storageFile = new File(walkStorageFileName);
        if (storageFile.exists())  storageFile.delete();		//ɾ�������ļ�
        try {
        	storageFile.createNewFile();				//�������ļ�
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 ��õ�ǰ�ڵ��ĳһ���ھӵ�id�����û����һ��������Ҫ����p��q��
	 @param id ����id
	 @return ���ѡ����ھӵ�id*/
	public int getNextStep(int id) {
		NextWalkSelector nws = this._alias_nodes.get(id);
		if(nws == null) {
			Integer[] neighbors = this._G.neighbors(id);			//��ȡ�����ھ�
			int n = neighbors.length;
			float[] probabilitys = new float[n];
			for(int i = 0; i < n; i++) {
				probabilitys[i] = this._G.getEdgeWeight(id, neighbors[i]);
			}
			nws = new NextWalkSelector(Algorithm.Normal, neighbors, probabilitys, false);
			this._alias_nodes.put(id, nws);
		}
		return(nws.getItemRandomly());
	}
	
	/**
	 ǰ�����ڵ���src��dst�����dst��ǰ�ڵ��ĳһ���ھӽڵ�
	 @param src source�������ڶ���
	 @param destination��������һ�� 
	 @return ���ѡ���dst���ھӵ�id*/
	public Integer getNextStep(int src, int dst) {
		float p = this._p;
		float q = this._q;
		HashMap<Integer, NextWalkSelector> srcHashMap = this._alias_edges.get(src);
		if(srcHashMap == null) {
			srcHashMap = new HashMap<Integer, NextWalkSelector>();
			this._alias_edges.put(src, srcHashMap);
		}
		NextWalkSelector nws = srcHashMap.get(dst);
		if(nws == null) {
			Integer[] dst_neighbors = this._G.neighbors(dst);			//��ȡ�����ھ�
			int n = dst_neighbors.length;
			float[] probabilitys = new float[n];
			for(int i = 0; i < n; i++) {
				if(dst_neighbors[i] == src) {
					probabilitys[i] = this._G.getEdgeWeight(dst, dst_neighbors[i]) / p;
				}
				else if(this._G.hasEdge(dst_neighbors[i], src)) {
					probabilitys[i] = this._G.getEdgeWeight(dst, dst_neighbors[i]);
				}	
				else{
					probabilitys[i] = this._G.getEdgeWeight(dst, dst_neighbors[i]) / q;
				}
			}
			nws = new NextWalkSelector(Algorithm.Normal, dst_neighbors, probabilitys, false);
			srcHashMap.put(dst, nws);
		}
		return(nws.getItemRandomly());
	}
	
	/**
	 ����N2V������Simulate a random walk starting from start node.
	 @param start_node ��ʼ�ڵ�
	 @return ��start_node��ʼ�ģ�����Ϊwalk_length�ı�������*/
	public Integer[] node2vec_walk(Integer start_node) {
		int walk_length = this._walk_length;
		ArrayList<Integer> walk = new ArrayList<Integer>();
		walk.add(start_node);
		while(walk.size() < walk_length) {
			Integer cur = walk.get(walk.size() - 1);					//��ǰ�ĵ�dst
			Integer[] cur_nbrs = this._G.neighbors(cur);
			if(cur_nbrs.length > 0) {							//���Լ�������ȥ
				if(walk.size() == 1) {
					Integer next = this.getNextStep(cur);
					walk.add(next);
				}
				else{
					Integer prev = walk.get(walk.size() - 2);			//ǰһ���ڵ�src
					Integer next = this.getNextStep(prev, cur);
					walk.add(next);
				}
			}
			else{
				break;
			}
		}
		Integer[] ret = new Integer[walk.size()];
		return walk.toArray(ret);
	}
	
	/**
	 �ظ����������Repeatedly simulate random walks from each node.
	 @return �������*/
	public ArrayList<Integer[]> simulate_walks() {
		ArrayList<Integer[]> walks = new ArrayList<Integer[]>();
		Integer[] nodes = this._G.nodesArray();
		StringBuffer walkBuff = new StringBuffer();				//д�ļ�������
		int lineNumber = 0;										//������������
		//System.out.print("·������");		//Walk iteration
		if(this.walkStorage != WalkStorage.ToArrayList) {	//д�ļ�
			try {
		       	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.storageFile, false)));
				out.write(this._G.getNodeNumber() * this._num_walks + "\n");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for(int i = 0; i < this._num_walks; i++) {
			System.out.println((i+1) +  "/" + this._num_walks);			//��ǰ���/��ѭ����
			Log.addMessage((i+1) +  "/" + this._num_walks);
			List<Integer> nodesArray = new ArrayList<Integer>();		
			nodesArray = Arrays.asList(nodes);						//����ת��Ϊ�б�
			Collections.shuffle(nodesArray);						//�����б�
			nodes = nodesArray.toArray(nodes);						//�б�ת��Ϊ����
			Integer[] walk = null;
			for(int j = 0; j < nodes.length; j++) {
				walk = this.node2vec_walk(nodes[j]);
				if(this.walkStorage != WalkStorage.ToFile) {		//��������
					walks.add(walk);
				}
				if(this.walkStorage != WalkStorage.ToArrayList) {	//д�ļ�
					for(int k = 0; k < walk.length; k++) {
						walkBuff.append(walk[k]);
						if(k < walk.length - 1) walkBuff.append(",");
						else walkBuff.append("\n");					//һ������
					}
					if(lineNumber >= 1023) {				//ÿ1024������׷��д���ļ�
						try {
				        	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.storageFile, true)));
							out.write(walkBuff.toString() + "\n");
							out.flush();
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						walkBuff.delete(0, walkBuff.length());
						lineNumber = 0;
					}
					else {
						lineNumber++;
					}
				}
			}	
		}
		if(walkBuff.length() > 0) {						//��������������
			try {
	        	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.storageFile, true)));
				out.write(walkBuff.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			walkBuff.delete(0, walkBuff.length());
		}
		/*Integer[][] ret = new Integer[walks.size()][];
		ret = walks.toArray(ret);
		return ret;*/
		return walks;
	}
}
