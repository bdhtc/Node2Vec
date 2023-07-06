package czz2D;

import java.awt.Color;
import java.util.ArrayList;

/**
 * ��Ļ�ϵĵ㼯
 * @author CZZ*/
public class ViewPointSet {
	
	/**
	 * ��Ļ�ϵĵ㼯*/
	ArrayList<ViewPoint> viewPointSet;
	
	/**
	 * �㼯*/
	PointSet ps;
	
	/**
	 * �����ɫ*/
	Color centerColor, lineColor;
	
	/**
	 * �������*/
	int lineWidth;
	
	/**
	 * ��İ뾶��С*/
	int r;
	
	/**
	 * �Ƿ��������ӵĵ������Ϊ�㼯ͳһ����*/
	private boolean isUniformAdd;
	
	/*================================���� methods================================*/
	
	/**
	 * ���췽��*/
	ViewPointSet() {
		setUniformAdd(true);
		viewPointSet = new ArrayList<ViewPoint>();
		init();
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
	 * @return �Ƿ��������ӵĵ������Ϊ�㼯ͳһ����*/
	public boolean isUniformAdd() {
		return isUniformAdd;
	}

	/**
	 * �Ƿ��������ӵĵ������
	 * @param isUniformAdd �Ƿ��������ӵĵ������Ϊ�㼯ͳһ����*/
	public void setUniformAdd(boolean isUniformAdd) {
		this.isUniformAdd = isUniformAdd;
	}
	
	/**
	 * ���㼯֮�����е����ʾ����ͳһ����*/
	public void uniformlySet() {
		for(int i = 0; i < viewPointSet.size(); i++) {
			viewPointSet.get(i).set(this.centerColor, this.lineColor, this.lineWidth, this.r);
		}
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
