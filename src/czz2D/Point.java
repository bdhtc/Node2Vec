package czz2D;

/**
 * ����ϵ�еĵ�
 * @author CZZ*/
public class Point extends GeometricElement{

	/**
	 * ����*/
	float x, y;
	
	/**
	 * ��Ļ�ϵĵ㣬��Ļ�ϵĵ��ͶӰ������*/
	ViewPoint vp;
	
	/*================================���� methods================================*/
	
	/**
	 * �չ��췽��*/
	public Point() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * ���췽��*/
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
