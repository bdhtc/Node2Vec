package czzNode2Vec;

import java.util.List;

/**
 * ͼ�ӿڣ�Node2Vec��ʹ������ӿڣ����������߿��Ա�д�Լ�����̳�IGraph�ӿ�
 *
 * @author CZZ
 */
public interface IGraph {

    /**
     * ���ļ�װ��ͼ
     */
    public boolean loadGraphFromEdgelistFile(String name, String split, boolean isDirected, boolean isWeighted);

    /**
     * ���idΪ��id���Ľڵ�
     */
    public boolean addNode(int id);

    /**
     * �Ƴ�idΪ��id���Ľڵ�
     */
    public boolean removeNode(int id);

    /**
     * �Ƿ����ĳ��idΪ��id���Ľڵ�
     */
    public boolean hasNode(int id);

    /**
     * ���һ����id1�ڵ㵽id2�ڵ��ȨֵΪweight�ı�
     */
    public boolean addEdge(int id1, int id2, Integer weight);

    /**
     * �Ƴ���id1�ڵ㵽id2�ڵ�ıߣ���Ϊ����ͼ��ͬʱɾ����id2-id1
     */
    public boolean removeEdge(int id1, int id2);

    /**
     * �Ƿ���д�id1��id2�ı�
     */
    public boolean hasEdge(int id1, int id2);

    /**
     * ��ȡ�ߵ�Ȩֵ
     */
    public Integer getEdgeWeight(int id1, int id2);

    /**
     * �޸ıߵ�Ȩֵ
     */
    public void setEdgeWeight(int id1, int id2, Integer weight);

    /**
     * �ж�ĳ���ڵ㣨id�����Ե�����ھ�
     */
    public List<Integer> neighbors(int id);

    /**
     * ����ͼ�Ľڵ�����
     */
    public List<Integer> getNodeList();

    /**
     * @return �ڵ����
     */
    public int getNodeNumber();

    /**
     * ������ͼ
     */
    public void clear();
}
