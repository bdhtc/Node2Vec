package czzMatrix;

/**
 * ��������ֵ�����������Ľṹ��������������
 * @author CZZ*/
public class Eigen {

	/**
	 * ����ֵ������Ϊl��l<n*/
	public float[] eigenvalues;
	
	/**
	 * ������������ÿ���Ǿ����Ӧ����ֵ����������*/
	public Matrix eigenvectors;
	
	/*================================���� methods================================*/
	
	/**
	 * �չ��췽��*/
	public Eigen() {
		this.eigenvalues = null;
		this.eigenvectors = null;
	}
}
