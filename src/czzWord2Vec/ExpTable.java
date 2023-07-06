package czzWord2Vec;

/**
 * ��Ϊsigmoid����ֻ��0�����仯�ϴ�ֵ���ڣ�-1, 1�������Կ���Ԥ������һ������ֵ���������Խ�Լ����ʱ��
 * @author CZZ*/
public class ExpTable {

	/**
	 * Ԥexp����*/
	private int _tableSize;
	
	/**
	 * �Ա���X��������*/
	private int _maxX;

	/**
	 * Ԥexp��*/
	private float[] _table;
	/*================================���� methods================================*/
	
	/**
	 * Ĭ�Ϲ��췽��*/
	ExpTable(){
		this._tableSize = 2000;
		this._maxX = 8;
		_table = new float[this._tableSize + 1];			//Ϊ���ұߵõ�������
		initTable();
	}
	
	/**
	 * ���ñ�Ĵ�С���Ա���������
	 * @param tableSize ϸ����������
	 * @param max ��[-max, max]*/
	ExpTable(int tableSize, int max){
		if(tableSize <= 0) tableSize = 2000;
		if(max <= 0) max = 8;
		this._tableSize = tableSize;
		this._maxX = max;
		_table = new float[this._tableSize + 1];			//Ϊ���ұߵõ�������
		initTable();
	}
	
	/**
	 * S�ε�sigmoid����
	 * @param x �Ա���
	 * @return ����ֵ*/
	public static float sigmoid(float x) {
		float t = (float) Math.exp(x);					//tֵ��������������NaN��Not a Number��
		return t / (t + 1);							//��������ó����뱻������С�ӽ�
		//return (1/(1 + (float)Math.exp(-x)));			//Sigmoid(x) = 1/(1 + e ^-x)
	}
	
	/**
	 * @return ��ñ�ĳ���*/
	public int getTableSize() {
		return _tableSize;
	}

	/**
	 * @return �Ա���������*/
	public int getMaxX() {
		return _maxX;
	}

	/**
	 * ��ʼ����*/
	private void initTable() {
		float x;
		for(int i = 0; i <= this._tableSize; i++) {
			x = (2.0f * i / this._tableSize - 1) * this._maxX;			//[-maxX, maxX]�ֳ�tableSize��
			_table[i] = ExpTable.sigmoid(x);
		}
	}
	
	/**
	 * ������
	 * @param x �Ա���*/
	public float getSigmoid(float x) {
		float ret = -1;
		if(x > this._maxX) ret = 1;
		else if(x < -this._maxX) ret = 0;
		else ret = this._table[(int) ((x + this._maxX) * (this._tableSize / this._maxX / 2))];
		return ret;
	}
}
