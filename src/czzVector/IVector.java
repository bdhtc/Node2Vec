package czzVector;

/**
 * �����ӿڣ�ά�������ȣ�����
 * @author CZZ*/
public interface IVector {

	/**
	 * ����һ���µ�����*/
	public void copy(IVector v);
	
	/**
	 * ������һ������������Ϊsize*/
	public void resize(int size);
	
	/**
	 * ������һ������������Ϊsize��ÿ������ʼ��Ϊseed*/
	public void resizeLoad(int size, float seed);

	/**
	 * ���������з������seed*/
	public void load(float seed);
	
	/**
	 * ������������*/
	public float[] getVector();
	
	/**
	 * ����������ά��*/
	public int getSize();
	
	/**
	 * ���������ĳ��ȣ�ģ��2-������*/
	public float getLength();
	
	/**
	 * ����������*/
	public void multiply(float number);
	
	/**
	 * ���ˣ�����һ���µ�������*/
	public IVector new_Multi(float number);
	
	/**
	 * �����˷�*/
	public float multiply(IVector v2);
	
	/**
	 * ������Ӧ�������*/
	public void add(IVector v2);
	
	/**
	 * ������Ӧ������ӣ�����һ���µ�������*/
	public IVector new_Add(IVector v2);
	
	/**
	 * ����֮���ŷʽ����*/
	public float distance(IVector v);
}
