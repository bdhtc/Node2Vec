package czzWord2Vec;

/**
 �����ת����Ƕ�룬embedding��Ϊ�����Ĵ���
 @author CZZ*/
public class Word<T>{
	
	/**
	 �ʣ����ʹ�õ�����ǡ������*/
	public T word;
	
	/**
	 ��ͳ�ƵĴ�Ƶ��Ҳ�����Դ�Ϊ���ݽ���Huffman��*/
	public int wordFrequency;
	
	/*================================���� methods================================*/
	
	/**
	 �չ��췽��*/
	public Word() {
		this.word = null;
		this.wordFrequency = 0;
	}
	
	/**
	 ���췽��*/
	public Word(T word) {
		this.word = word;
		this.wordFrequency = 1;
	}
	
	/**
	 ���췽��*/
	public Word(int wordFrequency) {
		this.word = null;
		this.wordFrequency = wordFrequency;
	}
}
