package czzNode2Vec;

import java.util.Random;

import czzSelectItem.ProbabilityPair;
import czzSelectItem.Selector;

/**
 Node2Vec��������һ��ѡ����
 @author CZZ*/
public class NextWalkSelector {
	
	/**
	 * ѡ���㷨��Normal:��ͨ�㷨�����̶ķ�����Alias������ѡ��*/
	public enum Algorithm {Normal, Alias};
	
	/**
	 ѡ���ѡ���㷨*/
	private Algorithm _algorithm;
	
	/**
	 �淶�����Ӻ�Ϊ1���ĸ���*/
	private boolean _normalized;
	
	/**
	 �ھӵ�id*/
	private Integer[] _neighbors;
	
	/**
	 ��Ӧ�ھӽڵ�ĸ���*/
	private float[] _probabilities;
	
	/**
	 �������*/
	private float _sum;

	/**
	 alias������Ҫ��J��q*/
	private ProbabilityPair[] jq;

	/*================================���� methods================================*/
	
	/**
	 ���췽��
	 @param algorithm 1:��ͨѡ��2��aliasѡ��
	 @param neighbors �ھӽڵ�id����
	 @param probability �ڵ�id��Ӧ�ĸ�������
	 @param normalized �Ƿ񽫸��������һ��*/
	public NextWalkSelector(Algorithm algorithm, Integer[] neighbors, float[] probabilities, boolean normalized){
		this._algorithm = algorithm;
		this._neighbors = neighbors;
		this._probabilities = probabilities;
		this._normalized = normalized;
		this._sum = getProbSum();					//δ���
		normalized();
		if(algorithm == Algorithm.Alias) {			//����ѡ��
			if(!this._normalized) {
				this._normalized = true;
				normalized();
			}
			jq = Selector.alias_setup(this._probabilities);
		}
	}
	
	/**
	 ���ظ������еĺ�*/
	private float getProbSum() {
		float sum = 0;
		if(this._probabilities != null) {
			int n = this._probabilities.length;
			for(int i = 0; i < n; i++) {
				sum += this._probabilities[i];
			}
		}
		else {
			sum = -1;
		}
		return sum;
	}
	
	/**
	 �Ѹ������й�һ����֮��Ϳ��Ե���alias_setuo����*/
	private void normalized() {
		if(this._normalized && this._sum > 0 && this._sum != 1) {			//��Ҫ���ҿ��Թ�һ��
			int n = this._probabilities.length;
			for(int i = 0; i < n; i++) {
				this._probabilities[i] /= this._sum;
			}
			this._sum = 1;
		}
	}
	
	/**
	 �Ѹ����������ƴ��һ��Ͱ�������Ͱ�������һ���㣬�鿴�����λ���ĸ����֣�����ʵ�ֿɼ�czzSelectItem��Selector��select����
	 @return ��һ����id*/
	private int czzBucketSelect() {
		int ret = -1;
		int n = this._probabilities.length;
		float result = 0;			//������
		float sum = this._sum;
		if(sum > 0) {					//ֻҪ����ֵ�Ϳ��ԱȽϴ�С������ǡ����0
			Random random = new Random();
			result = random.nextFloat() * sum;			//[0, 1) * sum
			float tempSum = 0;			//��ʱ��
			for(ret = 0; ret < n ; ret++) {
				tempSum += this._probabilities[ret];
				if(tempSum > result) break;				//��ΪnextFloat������ҿ����䣬���Բ�ȡ�Ⱥ�
			}
		}
		return this._neighbors[ret];					//����������Ӧ�Ľڵ�id
	}
	
	/**
	 aliasѡ��
	 @return ��һ����id*/
	private int aliasSelect() {
		int ret = Selector.alias_draw(jq);
		return this._neighbors[ret];
	}
	
	/**
	 �����û�ѡ����㷨��ѡ����һ���Ľڵ�
	 @return ��һ����id*/
	public int getItemRandomly() {
		int ret = -1;
		if(this._algorithm == Algorithm.Alias) ret =  aliasSelect();
		else if(this._algorithm == Algorithm.Normal) ret = czzBucketSelect();
		return ret;
	}
}
