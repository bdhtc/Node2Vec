package czz2D;

import java.awt.Color;

/**
 * ��Ļ�ϵĵ㣬��ʵ��һ��Բ������������߹���
 * @author CZZ*/
public class ViewPoint {

	/**
	 * ����x,y*/
	int x, y;
	
	/**
	 * �����Ļ�������ĸ��߼���*/
	Point p;
	
	/**
	 * �����ɫ*/
	Color centerColor, lineColor;
	
	/**
	 * �������*/
	int lineWidth;
	
	/**
	 * ��İ뾶��С*/
	int r;
	
	/*================================���� methods================================*/
	
	/**
	 * �չ��췽��*/
	ViewPoint(){
		this.x = 0;
		this.y = 0;
		this.init();
	}
	
	/**
	 * ��ʼ����ɫ*/
	private void init() {
		this.centerColor = Color.WHITE;				//������
		this.lineColor = Color.BLACK;				//������
		this.lineWidth = 2;
		this.r = 8;
	}
	
	/**
	 * ����������ɫ
	 * @param centerColor ������ɫ��null����͸��*/
	public void setCenterColor(Color centerColor) {
		this.centerColor = centerColor;
	}
	
	/**
	 * ���ñ�����ɫ
	 * @param lineColor ������ɫ��null����͸��*/
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	
	/**
	 * ���ñ��߿��
	 * @param lineWidth �������һ��Բ������һ���㣬��ȿ��Դ�һЩ���ı����g.drawLineʱ��brush*/
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}
	
	/**
	 * ����Բ��뾶
	 * @param r Բ��İ뾶*/
	public void setRadius(int r) {
		this.r = r;
	}
	
	/**
	 * ͬʱ����ȫ������
	 * @param centerColor ������ɫ��null����͸��
	 * @param lineColor ������ɫ��null����͸��
	 * @param lineWidth �������һ��Բ������һ���㣬��ȿ��Դ�һЩ
	 * @param r Բ��İ뾶*/
	public void set(Color centerColor, Color lineColor, int lineWidth, int r) {
		this.centerColor = centerColor;
		this.lineColor = lineColor;
		this.lineWidth = lineWidth;
		this.r = r;
	}
}
