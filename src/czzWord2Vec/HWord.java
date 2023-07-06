package czzWord2Vec;

import java.util.ArrayList;

/**
 ���Դ洢Huffman����Ĵ���
 @author CZZ*/
public class HWord<T> extends Word<T>{

	/**
	 * huffman����*/
	public ArrayList<Byte> code;
	
	/**
	 * huffman���У��Ӹ��ڵ㵽��Ҷ�ӽڵ�ķ�Ҷ�ӽڵ�����*/
	public ArrayList<Integer> point;
	
	/*================================���� methods================================*/
	
	/**
	 �չ��췽��*/
	public HWord() {
		super();
		code = new ArrayList<Byte>();
		point = new ArrayList<Integer>();
	}
	
	/**
	 ���췽��*/
	public HWord(T word) {
		super(word);
		code = new ArrayList<Byte>();
		point = new ArrayList<Integer>();
	}

	/**
	 * �鿴word*/
	public String toString() {
		return (String) super.word;
	}
}
