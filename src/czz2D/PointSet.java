package czz2D;

import java.util.ArrayList;

/**
 * ��ļ���
 * @author CZZ*/
public class PointSet extends GeometricElement{

	/**
	 * �㼯*/
	ArrayList<Point> pointSet;
	
	/**
	 * ��Ļ�ϵĵ㼯*/
	ViewPointSet vps;
	
	/**
	 * �㼯�е���Сx����*/
	float minX;
	
	/**
	 * �㼯�е���Сy����*/
	float minY;
	
	/**
	 * �㼯�е����x����*/
	float maxX;
	
	/**
	 * �㼯�е����y����*/
	float maxY;
	
	/*================================���� methods================================*/
	
	/**
	 * ���췽��*/
	public PointSet() {
		this.minX = 0;
		this.minY = 0;
		this.maxX = 0;
		this.maxY = 0;
		pointSet = new ArrayList<Point>();
	}
	

	/**
	 * @return �㼯�е�ĸ���*/
	public int getPointNumber() {
		return this.pointSet.size();
	}
	
	/**
	 * ���ض�Ӧ����Ļ�ϵĵ㼯*/
	public ViewPointSet getViewPointSet() {
		return this.vps;
	}
	
	/**
	 * ��㼯�������һ����
	 * @param p ����ӵĵ�*/
	public boolean addPoint(Point p) {
		boolean ret = true;
		this.pointSet.add(p);
		if(p.x > this.maxX) this.maxX = p.x;
		if(p.x < this.minX) this.minX = p.x;
		if(p.y > this.maxY) this.maxY = p.y;
		if(p.y < this.minY) this.minY = p.y;
		if(vps != null) {
			ViewPoint vp = new ViewPoint();
			vp.p = p;						//��󶨵���Ļ��
			p.vp = vp;						//��Ļ��󶨵���
			if(vps.isUniformAdd()) vp.set(vps.centerColor, vps.lineColor, vps.lineWidth, vps.r);	//ͳһ����
			vps.viewPointSet.add(vp);
		}
		return ret;
	}
}
