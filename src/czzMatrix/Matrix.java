package czzMatrix;

import java.util.LinkedList;

public class Matrix {
	
	/**
	 * �����ά����*/
	private float [][] matrix;
	
	/**
	 * ����*/
	private int row;
	
	/**
	 * ����*/
	private int column;
	
	/*================================���� methods================================*/
	
	/**
	 * �չ��췽��*/
	public Matrix(){
		this.row = 0;
		this.column = 0;
		this.matrix = null;
	}
	
	/**
	 * �޳�ʼ����ֵ���췽��
	 * @param row ����
	 * @param column ����*/
	public Matrix(int row, int column){
		this.row = row;
		this.column = column;
		this.matrix = new float[row][];
		for(int i = 0; i < row; i++) {
			this.matrix[i] = new float[column];
		}
	}
	
	/**
	 * ���췽��
	 * @param row ����
	 * @param column ����
	 * @param num ��ʼ����ֵ*/
	public Matrix(int row, int column, float num){
		this.row = row;
		this.column = column;
		this.matrix = new float[row][];
		for(int i = 0; i < row; i++) {
			this.matrix[i] = new float[column];
			for(int j = 0; j < column; j++) {
				this.matrix[i][j] = num;
			}
		}
	}
	
	/**
	 * ���ݶ�ά����װ�ش˾��󣬾���δ��ʼ��������ͬ��״����װ��
	 * @param mat ��ά����*/
	public boolean load(float[][] mat) {
		boolean ret = false;
		int r = mat.length, c;
		if(r > 0) {
			c = mat[0].length;
			if(c > 0) {
				if(this.matrix == null) {					//����δ��ʼ��
					this.row = r;
					this.column = c;
					this.matrix = new float[this.row][];
					for(int i = 0; i < this.row; i++) {
						this.matrix[i] = new float[this.column];
					}
				}
				if(this.row == r && this.column == c) {			//����ͬ��
					for(int i = 0; i < this.row; i++) {
						for(int j = 0; j < this.column; j++) {
							this.matrix[i][j] = mat[i][j];
						}
					}
				}
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * ����һ��m����
	 * @param m �����Ƶľ���*/
	public boolean copy(Matrix m) {
		boolean ret = false;
		if(m.getRow() > 0 && m.getColumn() > 0) {
			this.load(m.getMatrix());
			ret = true;
		}
		return ret;
	}
	
	/**
	 * @return ��������*/
	public int getRow() {
		return this.row;
	}
	
	/**
	 * @return ��������*/
	public int getColumn() {
		return this.column;
	}
	
	/**
	 * @return ����*/
	public float[][] getMatrix() {
		return this.matrix;
	}
	
	/**
	 * ��þ����е�ֵ,���д�0��ʼ
	 * @param row ��row�� 0 1 2
	 * @param column ��column�� 0 1 2
	 * @return ������Ӧλ�õ�ֵ�����߱�ʾ�����ڵ�null*/
	public Float get(int row, int column) {
		Float ret = null; 
		if(row >= 0 && column >=0 && row < this.row && column < this.column) {
			ret = matrix[row][column];
		}
		return ret;
	}
	
	/**
	 * ���þ����е�ֵ,���д�0��ʼ
	 * @param row ��row�� 0 1 2
	 * @param column ��column�� 0 1 2
	 * @param f ���õ���*/
	public void set(int row, int column, float f) {
		if(row >= 0 && column >=0 && row < this.row && column < this.column) {
			matrix[row][column] = f;
		}
	}
	
	/**
	 * ����˷�a * b
	 * @param a ����a
	 * @param b ����b
	 * @return ����a * b*/
	public static Matrix multiply(Matrix a, Matrix b) {
		int ar, ac, br, bc;
		ar = a.getRow();
		ac = a.getColumn();
		br = b.getRow();
		bc = b.getColumn();
		int i, j, k;
		Matrix ret = null;
		if(ar > 0 && ac == br && br > 0 && bc > 0) {
			ret = new Matrix(ar, bc, 0);
			for(i = 0; i < ar; i++) {
				for(j = 0; j < bc; j++) {
					ret.getMatrix()[i][j] = 0;
					for(k = 0; k < ac; k++) {
						ret.getMatrix()[i][j] += a.get(i, k) * b.get(k, j);
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * ����˷���this * a
	 * @return �������*/
	public Matrix multiply(Matrix m) {
		return Matrix.multiply(this, m);
	}
	
	/**
	 * �������ˣ�ÿ��λ�ó�f
	 * @param f һ��������*/
	public static Matrix multiply(Matrix m, float f) {
		Matrix ret = null;
		if(m.row > 0 && m.column > 0) {
			ret = new Matrix(m.row, m.column);
			for(int i = 0; i < m.row; i++) {
				for(int j = 0; j < m.column; j++) {
					ret.set(i, j, m.get(i, j) * f);
				}
			}
		}
		return ret;
	}
	
	/**
	 * �������ˣ�ÿ��λ�ó�f
	 * @param f һ��������*/
	public void multiply(float f) {
		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.column; j++) {
				matrix[i][j] *= f;
			}
		}
	}
	
	/**
	 * ��ȡ��ǰ�����ת�þ���
	 * @return ��ǰ�����ת�þ���*/
	public Matrix transposition() {
		Matrix ret = null;
		if(this.row > 0 && this.column > 0) {
			ret = new Matrix(this.column, this.row);
			for(int i = 0; i < this.row; i++) {
				for(int j = 0; j < this.column; j++) {
					ret.set(j, i, this.get(i, j));
				}
			}
		}
		return ret;
	}
	
	/**
	 * �˾��������˾���ͬ�εľ���m
	 * @param m ��˾���ͬ�ξ���m*/
	public void add(Matrix m) {
		if(this.row == m.getRow() && this.column == m.getColumn()) {
			for(int i = 0; i < this.row; i++) {
				for(int j = 0; j < this.column; j++) {
					this.matrix[i][j] += m.get(i, j);
				}
			}
		}
	}
	
	/**
	 * ����m1������m1����ͬ�εľ���m2
	 * @param m1 ����m1
	 * @param m2 ����m2*/
	public static Matrix add(Matrix m1, Matrix m2) {
		Matrix ret = null;
		if(m1.row == m2.getRow() && m1.column == m2.getColumn()) {
			ret = new Matrix(m1.row, m1.column);
			for(int i = 0; i < m1.row; i++) {
				for(int j = 0; j < m1.column; j++) {
					ret.set(i, j, m1.matrix[i][j] + m2.get(i, j));
				}
			}
		}
		return ret;
	}
	
	/**
	 * ������m����r * c�Σ��¾����൱��r��c�и�m
	 * @param m �����ƾ���
	 * @param rowTimes �и��ƴ���
	 * @param columnTimes �и��ƴ���
	 * @return ���ƽ��*/
	public static Matrix repeat(Matrix m, int rowTimes, int columnTimes) {
		Matrix ret = null;
		int mr = m.getRow();
		int mc = m.getColumn();
		if(mr > 0 && mc > 0 && rowTimes > 0 && columnTimes > 0) {
			ret = new Matrix(rowTimes * mr, columnTimes * mc);
			for(int a = 0; a < mr; a++) {
				for(int b = 0; b < mc; b++) {
					for(int c = 0; c < rowTimes; c++) {
						for(int d = 0; d < columnTimes; d++) {
							ret.set(c * mr + a, d * mc + b, m.get(a, b));
						}
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * ���˾�����r * c�Σ��¾����൱��r��c�и��˾���
	 * @param rowTimes �и��ƴ���
	 * @param columnTimes �и��ƴ���
	 * @return ���ƽ��*/
	public Matrix repeat(int rowTimes, int columnTimes) {
		return Matrix.repeat(this, rowTimes, columnTimes);
	}
	
	/**
	 * �����Ϊ������*/
	public void negative() {
		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.column; j++) {
				this.matrix[i][j] = -this.matrix[i][j];
			}
		}
	}
	
	/**
	 * ������е�ƽ����
	 * @return 1��c�о���c��������������ÿ��������һ�е�ƽ����*/
	public Matrix mean() {
		Matrix ret = null;
		if(this.row > 0 && this.column > 0) {
			ret = new Matrix(1, this.column, 0);
			for(int i = 0; i < this.row; i++) {
				for(int j= 0; j < this.column; j++) {
					ret.matrix[0][j] += this.matrix[i][j];
				}
			}
			ret.multiply(1.0f / this.row);
		}
		return ret;
	}
	
	/**������Ӿ���
	 * @param r0 ����ʼ��������
	 * @param r1 �н�����������
	 * @param c0 ����ʼ��������
	 * @param c1 �н�����������
	 * @return ��r0��r1����c0��c1�ģ��Ӿ���
	 * */
	public Matrix subMatrix(int r0, int r1, int c0, int c1) {
		Matrix ret = null;
		if(r0 <= r1 && r1 < this.row && c0 <= c1 && c1 < this.column) {
			ret = new Matrix(r1 - r0 + 1, c1 - c0 + 1);
			for(int i = r0; i <= r1; i++) {
				for(int j= c0; j <= c1; j++) {
					ret.matrix[i][j] += this.matrix[r0 + i][c0 + j];
				}
			}
		}
		return ret;
	}
	
	/**
	 * n-1�׵�ȥ��i��j�еľ��󣨴�������ʽ��
	 * @param i ׼��ȥ���ĵ�i��
	 * @param j ׼��ȥ���ĵ�j��
	 * @return n-1�׵��Ӿ���*/
	public Matrix subIJ(int i, int j) {
		Matrix ret = null;
		if(i >= 0 && i < this.row && j >= 0 && j < this.column) {
			ret = new Matrix(this.row - 1, this.column - 1);
			int r, c;
			for(int a = 0; a < this.row; a++) {
				if(a == i) continue;
				r = a;
				if(a > i) r--; 
				for(int b = 0; b < this.column; b++) {
					if(b == j) continue;
					c = b;
					if(b > j) c--;
					ret.matrix[r][c] += this.matrix[a][b];
				}
			}
		}
		return ret;
	}
	
	/**
	 * ���������ʽ*/
	public Float determinant() {
		Float ret =  null;
		if(this.row == this.column && this.row > 0) {
			LinkedList<Matrix> matrixQueue = new LinkedList<Matrix>();				//������ľ�����У��ö��д���ݹ�
			LinkedList<Float> coefficientQueue = new LinkedList<Float>();				//ϵ������
			Matrix mat;
			int n;
			matrixQueue.add(this);
			coefficientQueue.add(1f);
			ret = 0f;
			while(matrixQueue.size() > 0) {
				mat = matrixQueue.peek();			//��ǰջ��Ԫ��
				n = mat.getRow();
				if(n == 1) {
					ret += coefficientQueue.poll() * mat.get(0, 0);
				}
				else if(n == 2) {
					ret += coefficientQueue.poll() * (mat.get(0, 0) * mat.get(1, 1) - mat.get(0, 1) * mat.get(1, 0));
				}
				else if(n > 2) {
					int symbol = 1;
					for(int i = 0; i < n; i++) {
						matrixQueue.addLast(mat.subIJ(0, i));				//����һ��չ��
						coefficientQueue.addLast(symbol * coefficientQueue.peek() * mat.get(0, i));
						symbol = -symbol;
					}
					coefficientQueue.poll();
				}
				matrixQueue.poll();
			}
		}
		return ret;
	}
	
	/**
	 * ����İ������*/
	public Matrix adjoint() {
		Matrix ret = null;
		if(this.row == this.column && this.row > 0) {
			ret = new Matrix(this.row, this.column);
			int symbol;
			for(int i = 0; i < this.row; i++) {
				if(i % 2 == 0) symbol = 1;
				else symbol = -1;
				for(int j= 0; j < this.column; j++) {
					ret.matrix[j][i] = symbol * this.subIJ(i, j).determinant();
					symbol = -symbol;
				}
			}
		}
		return ret;
	}
	
	/**
	 * ������������ʽ�������
	 * @return �˾���������*/
	private Matrix defineInv() {
		Matrix ret = null;
		if(this.row == this.column && this.row > 0) {
			float det = this.determinant();
			if(det != 0) {						//�������
				ret = this.adjoint();
				ret.multiply(1 / this.determinant());
			}
			else ret = null;
		}
		return ret;
	}
	
	/**
	 * ����������
	 * @return �˾���������*/
	public Matrix inverse() {
		return defineInv();
	}
	
	/**
	 * Э�������
	 * @param m ����
	 * @return m�����Э�������*/
	public static Matrix cov(Matrix m) {
		int i, j, row, column;
		Matrix ret = null;
		row = m.getRow();
		column = m.getColumn();
		if(row > 0 && column > 0) {
			Matrix mean = m.mean();											//ÿ�о�ֵ
			Matrix s = new Matrix(row, column);
			for(i = 0; i < row; i++) {
				for(j = 0; j < column; j++) {
					s.set(i, j, m.get(i, j) - mean.get(0, j));					//��Ӧ��ȥÿ�еľ�ֵ
				}
			}
			ret = new Matrix(column, column, 0);
			for(i = 0; i < column; i++) {
				for(j = 0; j < column; j++) {
					for(int k = 0; k < row; k++) {
						ret.getMatrix()[i][j] += s.get(k, i) * s.get(k, j);		//��ת��
					}
					ret.getMatrix()[i][j] /= row - 1;							//��n-1
				}
			}
		}
		return ret;
	}
	
	/**
	 * Э�������
	 * @return �˾����Э�������*/
	public Matrix cov() {
		return Matrix.cov(this);
	}
	
	/**
	 * @return ���������Ϊ����*/
	public boolean isSquare() {
		boolean ret = (this.row == this.column && this.row > 0);
		return ret;
	}
	
	/**
	 * ʹ��gram-schmit��������QR�ֽ⣬QΪ��������RΪ�����������Ǿ���A=QR����Ҫ����A�����죬�����ȡ����������޹أ�
	 * @param A ����A
	 * @return ����0ΪQ��1ΪR*/
	public static Matrix[] QR(Matrix A) {			//Gram�CSchmidt��������ʽ
		Matrix[] ret = null;
		if(A.isSquare()) {	//QR�ֽ��A����һ���Ƿ���Ҳ������m��n�У���ʱQΪm*n,RΪn*n
			ret = new Matrix[2];
			//Matrix Q = new Matrix(A.getRow(), A.getColumn());				//Q
			//Matrix R = new Matrix(A.getColumn(), A.getColumn());			//R
			Matrix B = new Matrix();										//beta
			Matrix T = new Matrix(A.getColumn(), A.getColumn(), 0);			//T			Q=AT
			B.load(A.getMatrix());							//b1 = a1;
			float numerator, denominator;
			float[] l2 = new float[A.getColumn()];			//beta��ģ��������
			int i, j, k;
			for(i = 0; i < A.getColumn(); i++) {			//��ѭ����
				l2[i] = 0;
				for(j = 0; j <= i; j++) {					//��
					if(i == j) T.set(i, j, 1);			//�Խ���
					else if(i < j) continue;			//�����ǣ�����ִ�д˷�֧
					else if(i > j) {					//������
						numerator = 0;
						denominator = 0;
						for(k = 0; k < A.getColumn(); k++) {
							numerator += A.get(k, i) * B.get(k, j);					//alpha(i)*beta(j)
							denominator += B.get(k, j) * B.get(k, j);				//beta(j)*beta(j)
						}
						T.set(j, i, -numerator / denominator);					//����ǰ�Ĳ���
						for(k = 0; k < A.getRow(); k++) {
							B.getMatrix()[k][i] += T.get(j, i) * B.get(k, j);	//beta(i) = alpha(i) - sum(k=1,j-1)((alpha(i)beta(k)/beta(k)beta(k))beta(k))
						}
					}
				}
				for(k = 0; k < A.getColumn(); k++) {
					l2[i] += Math.pow(B.get(k, i), 2);
				}
				l2[i] = (float) Math.sqrt(l2[i]);
			}
			int symbol = -1;
			for(i = 0; i < A.getColumn(); i++) {					//ÿһ�ж���һ������
				for(j = 0; j < A.getRow(); j++) {					//�����ķ���
					B.getMatrix()[j][i] /= (symbol * l2[i]);				//��λ��
					if(i <= j) T.getMatrix()[j][i] /=l2[i];		//��Ӧ��ʩ��������������
				}
				symbol = -symbol;
			}
			ret[0] = B;								//QΪbeta�е�λ��
			//ret[1] = T.inverse();					//RΪT�������
			ret[1] = B.transposition().multiply(A);		//R=Q^T * A(Q^-1 * A)
		}
		return ret;
	}
	
	/**
	 * ͨ��QR�ֽ�����������������ֵ
	 * @param m ����m
	 * @return ����ֵ����*/
	public static Matrix diag(Matrix m) {
		Matrix ret = null;
		//int n = 0;
		Matrix mk = m;
		if(m.isSquare()) {
			Matrix[] qr;
			boolean flag = true;
			while(flag) {
				Matrix mk1;
				qr = Matrix.QR(mk);									//A(k) = Q(k) * R(k)
				mk1 = qr[1].multiply(qr[0]);							//A(k+1)=R(k)Q(k)								
				flag = false;
 				total : for(int i = 0; i < mk1.getRow(); i++) {				//�ԽǾ���
					for(int j = 0; j < mk1.getRow(); j++) {
						if(i > j && Math.abs(mk1.get(i, j)) > 1e-5) {		//������Ϊ0
							flag = true;					//��������
							break total;
						}
					}
				}
				//n++;
				mk = mk1;										//��������
			}
			ret = mk;
		}
		return ret;
	}
	
	/**
	 * ͨ���ݷ����������2������ֵ�����Ӧ����������
	 * @param m ����m
	 * @return ����ֵ�������Ӧ������������*/
	static Eigen getMaxTwoDiags(Matrix m) {						//���ɼ���package��
		Eigen ret = null;
		if(m.isSquare()) {			//mΪ����n
			ret = new Eigen();
			float[] eigenvalues = new float[2];	//����ֵ
			Matrix eigenvectors = new Matrix(m.getRow(), 2, 0);			//ֻ��������������
			PowerMethod pm = new PowerMethod(m);
			Eigen columnV;
			columnV = pm.maxEigen();				//�������ֵ����������
			eigenvalues[0] = columnV.eigenvalues[0];
			int i;
			for(i = 0; i < m.getRow(); i++) {		//��
				eigenvectors.set(i, 0, columnV.eigenvectors.get(i, 0));			//�������������Ƶ���Ӧ������ֵ�ģ�λ��
			}
			int notZero = -1;				//������ֵ��Ӧ���������еķ�����������
			for(notZero = 0; notZero < m.getRow(); notZero++) {
				if(Math.abs(columnV.eigenvectors.get(notZero, 0)) != 0) break;
				if(notZero == m.getRow() - 1) notZero = -1;					//ȫ��Ϊ0�������ܳ��ֵ����
			}
			Matrix v = new Matrix(1, m.getColumn());		//x1' * v = 1�� v�ж��ֹ��췽ʽ�������v��Ϊһ�ж�����һ�У����ں��������������
			//v=1/l1/x1i*(ai1, ai2, ����, ain)'	����l1Ϊ����ֵ�������ֵlamda1,x1iΪl1��Ӧ��������x1�з�0������iΪ����������±�
			//iҲ�Ǿ���A���У�Ҳ����ȡ��A�ĵ�i�У�ת��֮�󣬳���l1 * x1i��i��[0, n-1]
			float divide = columnV.eigenvalues[0] * columnV.eigenvectors.get(notZero, 0);		//lamda1 * x1i
			for(i = 0; i < m.getColumn(); i++) {				
				v.set(0, i, m.get(notZero, i) / divide);
			}
			Matrix vectorM = columnV.eigenvectors.multiply(v);
			vectorM.multiply(-columnV.eigenvalues[0]);
			pm.setMatrix(Matrix.add(m,vectorM));					//�����ǰ����ֵ��Ӱ��,B=A-l1*x1*v'
			columnV = pm.maxEigen();				//���������ֵ����������
			eigenvalues[1] = columnV.eigenvalues[0];
			//x2=(l2-l1)*y2 + l1*(v' * y2)*x1
			float l2SubL1 = eigenvalues[1] - eigenvalues[0];
			float l1MultiVTMultiY2 = eigenvalues[0] * v.multiply(columnV.eigenvectors).get(0, 0);
			Matrix x2notNorm = new Matrix(m.getRow(), 1);				//û�й�һ������������
			for(i = 0; i < m.getRow(); i++) {		//��
				x2notNorm.set(i, 0, l2SubL1 * columnV.eigenvectors.get(i, 0) + l1MultiVTMultiY2 * eigenvectors.get(i, 0));		//���δ��һ����x2
			}
			float x2Norm2 = PowerMethod.getLength(x2notNorm);				//����������ģ
			for(i = 0; i < m.getRow(); i++) {		//��
				eigenvectors.set(i, 1, x2notNorm.get(i, 0) / x2Norm2);		//�������������Ƶ���Ӧ������ֵ�ģ�λ��
			}
			ret.eigenvalues = eigenvalues;
			ret.eigenvectors = eigenvectors;
		}
		return ret;
	}
	
	/**
	 * ���ɷַ�������Principal Component Analysis��PCA�����������ά������ά��2ά�������ͱ��ڻ�ͼ
	 * @param m ����ÿ����һ��������ÿ���������ķ���
	 * @return ÿ����һ��������2��
	 * */
	public static Matrix PCA(Matrix m) {
		Matrix ret = null;
		if(m.getRow() > 0 && m.getColumn() > 2) {
			Matrix covM = Matrix.cov(m);			//Э�������;
			Eigen max2Eigen = Matrix.getMaxTwoDiags(covM);			//Э�������ģ�����ֵ�������������ֵ���Ӧ����������
			ret = m.multiply(max2Eigen.eigenvectors);
		}
		return ret;
	}
	
	/**
	 * ת��Ϊ������ʽ���ַ���*/
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("[");
		for(int i = 0; i < this.row; i++) {
			if(i != 0) str.append(", ");
			str.append("[");
			for(int j = 0; j < this.column; j++) {
				if(j != 0) str.append(", ");
				str.append(this.matrix[i][j]);
			}
			str.append("]");
		}
		str.append("]");
		return str.toString();
	}
}
