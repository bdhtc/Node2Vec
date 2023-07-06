package czzVector;

/**
 * �����������ͣ�
 * @author CZZ*/
public class CVector implements IVector{
	
	/**
	 * ����*/
	private float[] _vector;
	
	/*================================���� methods================================*/
	
	/**
	 * �չ��췽������ʼ��һ������Ϊlength����������ʼ��Ϊ0
	 * @param length ��ʼ������*/
	public CVector() {
		_vector = null;
	}
	
	/**
	 * ���췽������ʼ��һ������Ϊlength����������ʼ��Ϊ0
	 * @param length ��ʼ������*/
	public CVector(int size) {
		_vector = new float[size];
		for(int i = 0; i < _vector.length; i++) {
			_vector[i] = 0f;								//��ʼ��Ϊ0
		}
	}
	
	/**
	 * @return �������ʵ��*/
	@Override
	public float[] getVector() {
		return this._vector;
	}
	
	/**
	 * @return ������ά��*/
	@Override
	public int getSize() {
		int ret;
		if(this._vector == null) ret = 0;				//����δ��ʼ��
		else ret = this._vector.length;
		return ret;
	}
	
	
	/**
	 * �������ˣ���������ÿ��������number
	 * @param һ����������������ÿ���������*/
	@Override
	public void multiply(float number) {
		if(this._vector != null){
			for(int i = 0; i < this._vector.length; i++) {
				this._vector[i] *= number;
			}
		}
	}

	/**
	 * �������������ֱ����
	 * @param ��һ��ͨ���ȵ�����*/
	@Override
	public float multiply(IVector v2) {
		float ret = 0;
		if(this._vector != null && this._vector.length == v2.getSize()) {			//������Ҫ����ͬ�����ȣ�ά�ȣ�
			for(int i = 0; i < this._vector.length; i++) {
				ret += (this._vector[i] * v2.getVector()[i]);
			}
		}
		return ret;
	}

	/**
	 * @�����ĳ��ȣ�ģ��2-������*/
	@Override
	public float getLength() {
		float s = 0;
		if(this._vector != null) {
			for(int i = 0; i < this._vector.length; i++) {
				s += this._vector[i] * this._vector[i];
			}
		}
		return (float) Math.sqrt(s);
	}

	@Override
	public IVector new_Multi(float number) {
		IVector vret = new CVector(this.getSize());
		for(int i = 0; i < this.getSize(); i++) {
			vret.getVector()[i] = this._vector[i] * number;					//����
		}
		return vret;
	}

	@Override
	public void add(IVector v2) {
		if(this.getSize() == v2.getSize()) {
			for(int i = 0; i < this.getSize(); i++) {
				this._vector[i] += v2.getVector()[i];					//��Ӧλ�����
			}
		}
	}

	@Override
	public IVector new_Add(IVector v2) {
		IVector vret = new CVector(this.getSize());
		for(int i = 0; i < this.getSize(); i++) {
			vret.getVector()[i] = this._vector[i] + v2.getVector()[i];					//��Ӧλ�����
		}
		return vret;
	}


	@Override
	public void load(float seed) {
		if(this._vector != null){
			for(int i = 0; i < this._vector.length; i++) {
				this._vector[i] = seed;
			}
		}
	}
	
	/**
	 * ���µ�����С����������ռ�
	 * @param size ��ʼ���ռ䳤��
	 * @param seed ÿ��������ʼ��Ϊseed*/
	@Override
	public void resizeLoad(int size, float seed) {
		if(size <= 0) return;
		_vector = new float[size];				//ArrayList����ʼ�������Ĺ��췽��
		for(int i = 0; i < _vector.length; i++) {
			_vector[i] = seed;								//��ʼ��Ϊ0
		}
	}
	
	/**
	 * ���µ�����С����������ռ�
	 * @param size ��ʼ���ռ䳤��*/
	@Override
	public void resize(int size) {
		resizeLoad(size, 0);
	}
	
	/**
	 * ת��Ϊ�ַ���������ʾ*/
	public String toString() {
		StringBuffer str = new StringBuffer("[");
		if(this._vector != null) {
			for(int i = 0; i < this._vector.length; i++) {
				if(i != 0) str.append(", ");
				str.append(this._vector[i]);
			}
		}
		str.append("]");
		return str.toString();
	}

	/**
	 * @param v��һ������
	 * @return ��������v�����ĵ�ŷʽ����*/
	@Override
	public float distance(IVector v) {
		float ret = -1;
		if(this.getSize() == v.getSize()) {
			ret = 0;
			for(int i = 0; i < this.getSize(); i++) {
				ret += Math.pow(this._vector[i] - v.getVector()[i], 2);				//����ƽ����
			}
			ret = (float) Math.sqrt(ret);
		}
		return ret;
	}

	/**
	 * ����һ����������ȸ���
	 * @param v ��һ������*/
	@Override
	public void copy(IVector v) {
		if(v.getSize() > 0) {
			this._vector = new float[v.getSize()];
			for(int i = 0; i < this._vector.length; i++) {
				this._vector[i] = v.getVector()[i];
			}
		}
	}


}
