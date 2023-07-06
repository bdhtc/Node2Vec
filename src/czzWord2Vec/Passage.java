package czzWord2Vec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ����
 * @author CZZ*/
public class Passage<T> {

	/**
	 * ���´洢��ʽ����������ļ�*/
	public enum PassageStorage {ArrayList, File};
	
	/**
	 * ����*/
	private ArrayList<T[]> _sentences;
	
	/**
	 * �����еľ�������*/
	private long count;
	
	/**
	 * ��ȡָ��*/
	private int pointer;
	
	/**
	 * ���´洢��ʽ����������ļ�*/
	private PassageStorage passageStorage;
	
	/**
	 * ���������ļ�*/
	private File passageFile;
	
	/**
	 * ���ļ�*/
	private BufferedReader bufferedReader;
	
	/*================================���� methods================================*/
	
	/**
	 * ���췽��*/
	public Passage(PassageStorage passageStorage, String passageFileName) {
		this.passageStorage = passageStorage;
		count = 0;
		pointer = -1;
		if (passageStorage == PassageStorage.ArrayList)  _sentences = new ArrayList<T[]>();
		else if (passageStorage == PassageStorage.File) getStorageFile(passageFileName);
	}
	

	private boolean getStorageFile(String passageFileName) {
		boolean ret = false;
		passageFile = new File(passageFileName);
        if (passageFile.exists()) {
        	String readLine;
        	try {
				bufferedReader = new BufferedReader(new FileReader(this.passageFile));
				if((readLine = bufferedReader.readLine()) != null){				//��һ�У�������
					this.count = Integer.parseInt(readLine);
				}
				bufferedReader.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	if(count > 0) ret = true;
        }
        else passageFile = null;
		return ret;
	}
	
	/**
	 * �Ӷ�ά������ؾ���
	 * @param ���ʶ�ά����*/
	public void loadSentences(T[][] words) {
		_sentences.clear();					//���
		pointer = -1;
		for(int i = 0; i < words.length; i++) {
			_sentences.add(words[i]);
			count++;
		}
		if(count > 0) pointer = 0;
	}
	
	/**
	 * ��ArrayLisT���ؾ���
	 * @param �����б�*/
	public void loadSentences(ArrayList<T[]> words) {
		_sentences.clear();					//���
		pointer = -1;
		_sentences.addAll(words);
		count = _sentences.size();
		if(count > 0) pointer = 0;
	}
	
	/**
	 * @return ��������*/
	public long getSentenceCount() {
		return count;
	}
	
	/**
	 * @return ��������*/
	public ArrayList<T[]> getSentences() {
		return _sentences;
	}
	
	/**
	 * �ı���ӡ�ָ��"��λ��
	 * @param index ���õ�λ��
	 * @return ���óɹ�*/
	public boolean seek(int index) {
		boolean ret = false;
		if(index >= 0 && index < _sentences.size()) {
			this.pointer = index;
			ret = true;
		}
		return ret;
	}
	
	/**
	 * @return ��һ�����ӣ����ļ���ȡר��*/
	public Integer[] getNextSentenceInteger() {
		Integer[] ret = null;
		String[] numstr = getNextSentenceString();
		if(numstr != null && numstr.length > 0) {
			ret = new Integer[numstr.length];
			for(int i = 0; i < numstr.length; i++) {
				ret[i] = Integer.parseInt(numstr[i]);
			}
		}
		return ret;
	}
	
	/**
	 * @return ��һ�����ӣ����ļ���ȡר��*/
	public String[] getNextSentenceString() {
		String[] ret = null;
		if(passageStorage == PassageStorage.File) {
			if(pointer >= this.count) pointer = 0;			//��β����
			String readLine;
			int sentenceCount = -1;
			if(pointer <= 0) {								//���´��ļ�
				pointer = 0;
				try {
					bufferedReader = new BufferedReader(new FileReader(this.passageFile));
					if((readLine = bufferedReader.readLine()) != null){				//��һ�У�������
						sentenceCount = Integer.parseInt(readLine);
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(sentenceCount != this.count) {
					//TODO:�ļ���ȡ����
				}
			}
			try {
				if ((readLine = bufferedReader.readLine()) != null) {  //��ȡһ��
					ret = readLine.split(",| ");
			       	pointer++;
				}
				if(readLine == null) {				//�ļ�����ͷ��
					if(pointer != this.count) {
						System.out.println("����������������");
					}
					pointer = -1;
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(passageStorage == PassageStorage.File) {
			ret = (String[]) this._sentences.get(pointer++);
		}
		return ret;
	}
	
	/**
	 * @return ��һ�����ӱ��*/
	public int getPointer() {
		return this.pointer;
	}
	
	/**
	 * ����ָ����ŵľ���
	 * @param index ����������
	 * @return ��һ������*/
	public T[] getSentence(int index) {
		T[] ret = null;
		if(index >= 0 && _sentences != null && index < _sentences.size()) ret = _sentences.get(index);
		return ret;
	}
}
