package czz2D;

import java.awt.Container;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * �߼�����ϵ
 * @author CZZ*/
public class CoordinateSystem {

	/**
	 * �����ؼ�*/
	private Container canvas;
	
	/**
	 * ����Ļ���Ͻ����߼�����ϵ��screenLT��*/
	private Point screenLT;
	
	/**
	 * �߼�����ϵx���굥λ���ȵ��ڶ�����Ļ����ϵ����*/
	private float kx;
	
	/**
	 * �߼�����ϵy���굥λ���ȵ��ڶ�����Ļ����ϵ����*/
	private float ky;
	
	/**
	 * ���б�*/
	private ArrayList<Point> pointList;
	
	/**
	 * ��Ļ���б�*/
	private ArrayList<ViewPoint> viewPointList;
	
	/**
	 * ��ļ����б�*/
	private ArrayList<PointSet> pointSetList;
	
	/**
	 * ��Ļ��ļ����б�*/
	private ArrayList<ViewPointSet> viewPointSetList;
	
	/*================================���� methods================================*/
	
	/**
	 * ���췽��*/
	public CoordinateSystem(Container canvas, Point screenLT, float kx, float ky) {
        this.pointList = new ArrayList<>();
        this.viewPointList = new ArrayList<>();
        pointSetList = new ArrayList<>();
        viewPointSetList = new ArrayList<>();
        this.setCanvas(canvas);
        this.screenLT = screenLT;
        this.kx = kx;
        this.ky = ky;
    }
	
	/**
	 * ��ȡ����*/
	public Container getCanvas() {
		return canvas;
	}

	/**
	 * ���û���*/
	public void setCanvas(Container canvas) {
		this.canvas = canvas;
	}
	
	/**
	 * ����ӳ����㣬������ϵ�еĵ������Ļ�ϵĵ�*/
	public void Point2View(Point p, ViewPoint vp) {
		vp.x = (int) ((p.x - screenLT.x) * kx);
		vp.y = (int) ((screenLT.y - p.y) * ky);
	}
	
	/**
	 * ����ӳ����㣬����Ļ�ϵĵ��������ϵ�еĵ�*/
	public void View2Point(ViewPoint vp, Point p) {
		
	}

	/**
	 * ���һ����*/
	public boolean addPoint(Point p) {
		boolean ret = true;
		this.pointList.add(p);
		ViewPoint vp = new ViewPoint();
		p.vp = vp;							//ͶӰ��󶨵���
		viewPointList.add(vp);
		vp.p = p;							//��󶨵�ͶӰ��
		this.Point2View(p, vp);					//����任
		return ret;
	}
	
	/**
	 * ���һ����*/
	public boolean addPointSet(PointSet ps) {
		boolean ret = true;
		this.pointSetList.add(ps);
		ViewPointSet vps = new ViewPointSet();
		ps.vps = vps;							//ͶӰ��󶨵���
		viewPointSetList.add(vps);
		vps.ps = ps;							//��󶨵�ͶӰ��
		Point p;
		ViewPoint vp;
		for(int i = 0; i < ps.pointSet.size(); i++) {
			p = ps.pointSet.get(i);
			vp = new ViewPoint();
			vps.viewPointSet.add(vp);
			p.vp = vp;
			vp.p = p;
			this.Point2View(p, vp);					//����任
		}
		return ret;
	}
	
	/**
	 * ��ͼ*/
	public void draw(Graphics g) {
		g.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
		drawPointSet(g);
		ViewPoint vp;
		for(int i = 0; i < this.viewPointList.size(); i++) {
			vp = viewPointList.get(i);				//TODO��ͬʱ͸��ʱ���޸�Ĭ����ɫ�Ĵ���
			if(vp.centerColor != null) {					//͸������
				g.setColor(vp.centerColor);
				g.fillOval(vp.x - vp.r, vp.y - vp.r, vp.r, vp.r);
			}
			if(vp.lineColor != null) {						//͸������
				g.setColor(vp.lineColor);
				g.drawOval(vp.x - vp.r, vp.y - vp.r, vp.r, vp.r);
			}
		}
	}
	
	/**
	 * ���㼯*/
	private void drawPointSet(Graphics g) {
		ViewPointSet vps;
		ViewPoint vp;
		for(int i = 0; i < this.viewPointSetList.size(); i++) {
			vps = this.viewPointSetList.get(i);
			for(int j = 0; j < vps.viewPointSet.size(); j++) {
				vp = vps.viewPointSet.get(j);				//TODO��ͬʱ͸��ʱ���޸�Ĭ����ɫ�Ĵ���
				if(vp.centerColor != null) {					//͸������
					g.setColor(vp.centerColor);
					g.fillOval(vp.x - vp.r, vp.y - vp.r, vp.r, vp.r);
				}
				if(vp.lineColor != null) {						//͸������
					g.setColor(vp.lineColor);
					g.drawOval(vp.x - vp.r, vp.y - vp.r, vp.r, vp.r);
				}
			}
		}
	}
}
