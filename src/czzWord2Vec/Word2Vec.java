package czzWord2Vec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

import czzVector.CVector;
import czzVector.IVector;
import czzWord2Vec.Passage.PassageStorage;

/**
 * word2vec��һ�ִ���һ�������д����ϵ�ķ�ʽ����һ�ִ���ġ�Ƕ�롱��ʽ��������One-hot��ʾת��Ϊ�ֲ�ʽDistributed��ʾ����
 * ���������Ա�������һ�ָ����ĳ�����������Ĳ��������Ǵ�������һЩ���ص����ʣ��������ͨ�������ж�������������Ӧ�Ĵʵľ���
 * @author CZZ*/
public class Word2Vec<T> {

	/**
	 ģ�����ͣ�skip-gramģ�ͣ�����CBOWģ��*/
	public enum ModelType {Skip_gram, CBOW};
	
	/**
	 �ֲ�������ֵHierarchical Softmax�����߸�����Negative Sampling*/
	public enum TrainMethod {HS, NS, BOTH};
	
	/**
	 * ��������*/
	public enum WordType {String, Integer};
	
	/**
	 * ģ������*/
	private ModelType modelType;
	
	/**
	 * ѵ����ʽ*/
	private TrainMethod trainMethod;
	
	/**
	 * ��������*/
	private WordType wordType;
	
	/**
	 ������ά�ȣ�Ҳ�����������ز����Ԫ����*/
	private int dimensions;

	/**
	 �����Ĵ��ڴ�Сc��windows������ǰ���е��ʵ�����£����ȡ�õ�ǰ��ǰ���WindowSize�����ʣ�Ҳ�������2*WindowSize�����ʣ�
	 ��������Ӿ��������ǣ�ǰ��ֱ�ѡ��[1,WindowSize]�������������ĴʵĴ������˵�����ױ�ѡ��*/
	private int windowSize;
	
	/**
	 * ����������*/
	private int negative;
	
	/**
	 ��������*/
	private int iteratorNumber;
	
	/**
	 ��ʹ�Ƶ���������Ƶ�ʵĵ��ʲ��ᱻ����ʵ�vocabulary*/
	private int minWordCount;
	
	/**
	 ͬʱ���е��߳���*/
	private int threadNumber;
	
	/**
	 ����һ����ʼ��ѧϰ��*/
	private float startLearnRate;
	
	/**
	 ��ǰѧϰ��*/
	private float learnRate;
	
	/**
	 * ���£�ÿ��һ�䣬ÿ�䶼��һ�������*/
	private Passage<T> passags;
	
	/**
	 * Ԥ�ȼ���һ��sigmoid��������΢���Ч��*/
	private ExpTable expTable;
	
	/**
	 * �ʵ�*/
	private Vocabulary<T> vocabulary;
	
	/**
	 * ģ�Ͳ�����Ҳ����ѵ������ÿ���ʵ�����*/
	private IVector[] _models;					
	
	/**
	 * ���������м�ڵ�Ĳ�����������*/
	private IVector[] _huffmanTheta;			//x * ��
	
	/**
	 * ������������������*/
	private IVector[] _negTheta;			//x * ��
	
	/**
	 * �Ƿ񾭹���ʼ��*/
	private boolean initialized;
	
	/*================================���� methods================================*/
	
	/*
	/**
	 * �չ��췽��*
	public Word2Vec() {
		vocabulary = new Vocabulary<T>();
		setParam(ModelType.CBOW, TrainMethod.BOTH, 5, 200, 5, 0.05f, 0, 1, 1);
	}*/
	
	/**
	 * ���췽��
	 * @param mt ģ������
	 * @param tm ѵ����ʽ
	 * @param dimensions Ƕ�������ά��
	 * @param windowSize ���ڴ�С
	 * @param learnRate ѧϰ��
	 * @param minWordCount ��ʹ�Ƶ
	 * @param iteratorNumber ȫ��������������
	 * @param threadNumber �����߳���
	 * */
	public Word2Vec(WordType wordType, ModelType modelType, TrainMethod trainMethod, int negative, int dimensions, int windowSize, float learnRate, int minWordCount, int iteratorNumber, int threadNumber) {
		this.wordType = wordType;
		expTable = new ExpTable();
		initialized = false;
		setParam(modelType, trainMethod, negative, dimensions, windowSize, learnRate, minWordCount, iteratorNumber, threadNumber);
	}
	
	/**
	 * ���ø�������
	 * @return ���óɹ�����ʧ��*/
	private boolean setParam(ModelType modelType, TrainMethod trainMethod, int negative, int dimensions, int windowSize, float learnRate, int minWordCount, int iteratorNumber, int threadNumber) {
		this.modelType = modelType;
		this.trainMethod = trainMethod;
		this.negative = negative;
		this.dimensions = dimensions;
		this.windowSize = windowSize;
		this.startLearnRate = learnRate;
		this.minWordCount = minWordCount;
		this.iteratorNumber = iteratorNumber;
		this.threadNumber = threadNumber;
		return true;
	}
	
	/**
	 @return ά�ȴ�С*/
	public int getDimensions() {
		return dimensions;
	}

	/**
	 @return ���ڴ�С*/
	public int getWindowSize() {
		return windowSize;
	}

	/**
	 * @return ��������*/
	public int getIteratorNumber() {
		return iteratorNumber;
	}

	/**
	 * @return ���õ��߳���*/
	public int getThreadNumber() {
		return threadNumber;
	}

	/**
	 * �����߳���*/
	public void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	/**
	 * @return ���õ���С��Ƶ*/
	public int getMinWordCount() {
		return minWordCount;
	}

	/**
	 * @return ��ǰ��ѧϰ��*/
	public float getLearnRate() {
		return learnRate;
	}

	/**
	 * ���õ�ǰѧϰ��
	 * @deprecated
	 * @param ����ѧϰ��*/
	public void setLearnRate(int learnRate) {
		this.learnRate = learnRate;
	}
	
	/**
	 * ��ʼ����������Ϊ�Ǵ������л�ã��������ޣ����Խ�������С��ģͼ����
	 * @param words ��������*/
	public void init(ArrayList<T[]> words) {
		this.vocabulary = new Vocabulary<T>(Vocabulary.WordType.Integer);
		this.vocabulary.loadVocabulary(words);
		this.vocabulary.sortVocabulary();			//�ʵ��еĴ��ﰴ�մ�Ƶ��С��������
		this.passags = new Passage<T>(PassageStorage.ArrayList, "");
		this.passags.loadSentences(words);
		this.vocabulary.frequencyFilter(minWordCount);				//���˵�Ƶ��
		if(this.trainMethod == TrainMethod.BOTH || this.trainMethod == TrainMethod.HS) {
			this.vocabulary.getHuffmanCode();			//�����������������ÿ���ʵı���
		}
		if(this.trainMethod == TrainMethod.BOTH || this.trainMethod == TrainMethod.NS) {
			this.vocabulary.initUnigramTable();			//�������ڸ������ı�
		}
		
		initModels();				//��ʼ������
		this.learnRate = this.startLearnRate;
		initialized = true;
	}
	
	/**
	 * ��ʼ��������������Passage�л�����ݣ����¿�������ȫ�ڴ����ݣ�Ҳ�����Ǵ�Ӳ���ж�ȡ
	 * @param passags ѵ����������*/
	public void init(String passageFileName, int i) {
		this.passags = new Passage<T>(PassageStorage.File, passageFileName);		//���ļ���ȡ����������TӦ��ΪString
		if(this.wordType == WordType.Integer) {
			this.vocabulary = new Vocabulary<T>(Vocabulary.WordType.Integer);
		}
		else if(this.wordType == WordType.String) {
			this.vocabulary = new Vocabulary<T>(Vocabulary.WordType.String);
		}
		this.vocabulary.loadVocabulary(passags);
		this.vocabulary.sortVocabulary();			//�ʵ��еĴ��ﰴ�մ�Ƶ��С��������
		this.vocabulary.frequencyFilter(minWordCount);				//���˵�Ƶ��
		if(this.trainMethod == TrainMethod.BOTH || this.trainMethod == TrainMethod.HS) {
			this.vocabulary.getHuffmanCode();			//�����������������ÿ���ʵı���
		}
		if(this.trainMethod == TrainMethod.BOTH || this.trainMethod == TrainMethod.NS) {
			this.vocabulary.initUnigramTable();			//�������ڸ������ı�
		}
		initModels();				//��ʼ������
		this.learnRate = this.startLearnRate;
		initialized = true;
	}
	
	/**
	 * ���ļ���ʼ��*/
	public void init(String wordFile) {
		
	}
	
	/**
	 * ��������������v*/
	public void randomSetVector(IVector v) {
		if(v != null) {
			float[] va = v.getVector();
			if(va != null) {
				Random rand = new Random();
				for(int i = 0; i <va.length; i++) {
					//va[i] = (float) rand.nextDouble();
					va[i] = ((rand.nextFloat() - 0.5f) / this.dimensions);
				}
			}
		}
		
	}
	
	/**
	 * ��ʼ����������*/
	public void initModels() {
		_models = new IVector[this.vocabulary.getVocabularyLength()];
		if(this.trainMethod == TrainMethod.HS || this.trainMethod == TrainMethod.BOTH)
			_huffmanTheta = new IVector[this.vocabulary.getVocabularyLength()];
		if(this.trainMethod == TrainMethod.NS || this.trainMethod == TrainMethod.BOTH)
			_negTheta = new IVector[this.vocabulary.getVocabularyLength()];
		for(int i = 0; i < this.vocabulary.getVocabularyLength(); i++) {
			_models[i] = new CVector(this.dimensions);
			randomSetVector(_models[i]);						//��������
			if(this.trainMethod == TrainMethod.HS || this.trainMethod == TrainMethod.BOTH)
				_huffmanTheta[i] = new CVector(this.dimensions);
			if(this.trainMethod == TrainMethod.NS || this.trainMethod == TrainMethod.BOTH)
				_negTheta[i] = new CVector(this.dimensions);
		}
	}
	
	/**
	 * ��ѵ���õ��ļ���ȡ����
	 * @return ���سɹ���ʧ��*/
	public boolean loadModels(String file) {
		boolean ret = false;
		File f = new File(file);
        if (f.exists()) {				//�ļ�����
        	BufferedReader reader = null; 
        	try { 
        		reader = new BufferedReader(new FileReader(file)); 
        		String tempString = null;
        		String[] str = null;
        		tempString = reader.readLine();
        		str = tempString.split(" ");
        		if(str.length == 2) {
        			int num = Integer.parseInt(str[0]);				//�������
        			int dim = Integer.parseInt(str[1]);				//������ά��
        			if(num > 0 && dim > 0) _models = new IVector[num];
        			for(int i = 0; i < num; i++) {
        				_models[i] = new CVector(dim);
        				tempString = reader.readLine();
                		str = tempString.split(" ");
        				for(int j = 0; j < dim; j++) {
        					_models[i].getVector()[j] = Float.parseFloat(str[j + 1]);
        				}
        			}
        		}
        		reader.close();
        		ret = true;
        	} catch (IOException e) { 
        		e.printStackTrace(); 
        	} finally { 
        		if (reader != null){ 
        			try { 
        				reader.close(); 
        			} catch (IOException e1) { 
        				
        			} 
        		} 
        	} 
        }
		return ret;
	}
	
	/**
	 * ��ʼѵ��*/
	@SuppressWarnings("unchecked")
	public void startTrainning() {
		if(this.initialized) {				//������ʼ��
			int localIteratorNumber = 1;			//��ǰ��������
			int c;
			int randomWindow;									//[1, windowSize]��������ڴ�С��ʹ����֮���ϵ��Ϊ����
			Random rand = new Random();
			HWord<T> word, contextX;								//���Ĵʵ�������
			int wordIndex, contextIndex, thetaIndex;		//���Ĵ�,�������ڴʵ��е�������;����������
			T contextWord;									//������
			int label;								//��������L��(u) u�ʦء�Neg(��)
			IVector e, ehs, ens;				//hs��ns��e
			IVector xsum;						//CBOW�����������
			float f, g;
			int cw;				//CBOW�����ļ���
			for(localIteratorNumber = 1; localIteratorNumber <= this.iteratorNumber; localIteratorNumber++) { //��������
				for(int sentenceIndex = 0; sentenceIndex < this.passags.getSentenceCount(); sentenceIndex++) {
					T[] sentence = null;
					if(this.wordType == WordType.Integer) {
						sentence= (T[]) this.passags.getNextSentenceInteger();
					}
					else if(this.wordType == WordType.String){
						sentence= (T[]) this.passags.getNextSentenceString();
					}
					for(int sentence_position = 0; sentence_position < sentence.length; sentence_position++) {
						randomWindow = (rand.nextInt() + 11) % this.windowSize;		//randomWindow = [0, windowSize - 1],������ڴ�СwindowSize - randomWindow = [1, windowSize]
						wordIndex = this.vocabulary.getWordIndex(sentence[sentence_position]);
						if(wordIndex < this.vocabulary.getStartPointer()) continue;				//��ǰ�ʱ�����
						wordIndex -= this.vocabulary.getStartPointer();		//��Ϊ�е��ʱ����ˣ�����model,theta�����������ı�λ��
						word = this.vocabulary.getWord(sentence[sentence_position]);
						this.learnRate *= 0.9999f;							//��Сѧϰ��
						if (this.learnRate < this.startLearnRate * 0.0001f) this.learnRate = this.startLearnRate * 0.0001f;
						if(this.modelType == ModelType.CBOW) {
							cw = 0;
							e = new CVector(this.dimensions);		//e=0
							xsum = new CVector(this.dimensions);			//xw = 0;
							for (int i = randomWindow; i < this.windowSize * 2 + 1 - randomWindow; i++) {			//������ǰ�ʵ�������
								if (i != windowSize)				//ȡ�������У����˵�ǰ��
								{
									c =	sentence_position -	this.windowSize + i;			//c={sp-w,sp-w+1, ..., sp-1, sp+1, ..., sp+w-1, sp+w}
									if (c <	0) continue;
									if (c >= sentence.length) continue;			//for (int i = c; i < this.windowSize * 2 + 1 - c; i++)
									contextWord = sentence[c];
									contextIndex = this.vocabulary.getWordIndex(contextWord);
									contextX = this.vocabulary.getWord(contextWord);
									if(contextIndex == -1) continue;			//for (int i = c; i < this.windowSize * 2 + 1 - c; i++)
									contextIndex -= this.vocabulary.getStartPointer();		//��Ϊ�е��ʱ����ˣ�����model,theta�����������ı�λ��
									if(contextIndex < 0) continue;			//��ǰ�ʱ����˵���
									xsum.add(this._models[contextIndex]);
									cw++;
								}
							}//for (int i = randomWindow; i < this.windowSize * 2 + 1 - randomWindow; i++)
							if (cw > 0) {				//�������Ϊ0
								xsum.multiply(1f / cw);				//X /= cw;
								// HIERARCHICAL	SOFTMAX
								if (this.trainMethod == TrainMethod.HS || this.trainMethod == TrainMethod.BOTH) {
									for	(int l = 0;	l <	word.code.size(); l++) {		//��huffman·���ֲ�SoftMax
										thetaIndex = word.point.get(l);
										// Propagate hidden -> output
										f = xsum.multiply(this._huffmanTheta[thetaIndex]);
										if (f < -this.expTable.getMaxX()) continue;
										else if (f > this.expTable.getMaxX()) continue;
										else f = expTable.getSigmoid(f);								//sigmoid����
										// 'g' is the gradient multiplied by the learning rate
										g = (1 - word.code.get(l) - f) * this.learnRate;				//ƫ������ѧϰ��
										// Propagate errors output -> hidden
										e.add(this._huffmanTheta[thetaIndex].new_Multi(g));			//e += g * theta
										// Learn weights hidden -> output
										this._huffmanTheta[thetaIndex].add(xsum.new_Multi(g));			//theta += g * xm
									}
								}
								// NEGATIVE	SAMPLING
								if (this.trainMethod == TrainMethod.NS || this.trainMethod == TrainMethod.BOTH) {			
									for (int d = 0; d < this.negative + 1; d++) {
										if (d == 0)	{
											thetaIndex = wordIndex;
											label =	1;
										}
										else {
											thetaIndex = this.vocabulary.negSamplingWord();
											if (thetaIndex == wordIndex) continue;
											label =	0;
										}
										f = xsum.multiply(this._negTheta[thetaIndex]);
										if (f > this.expTable.getMaxX()) g = (label - 1) * this.learnRate;
										else if (f < -this.expTable.getMaxX()) g = (label - 0) * this.learnRate;
										else g = (label - expTable.getSigmoid(f)) * this.learnRate;
										e.add(this._negTheta[thetaIndex].new_Multi(g));			//e += g * theta
										this._negTheta[thetaIndex].add(xsum.new_Multi(g));			//theta += g * xm
									}
								}
								// hidden -> in
								for (int i = randomWindow; i < this.windowSize * 2 + 1 - randomWindow; i++) {
									c =	sentence_position -	this.windowSize + i;			//c={sp-w,sp-w+1, ..., sp-1, sp+1, ..., sp+w-1, sp+w}
									if (c <	0) continue;
									if (c >= sentence.length) continue;			//for (int i = c; i < this.windowSize * 2 + 1 - c; i++)
									contextWord = sentence[c];
									contextIndex = this.vocabulary.getWordIndex(contextWord);
									contextX = this.vocabulary.getWord(contextWord);
									if(contextIndex == -1) continue;			//for (int i = c; i < this.windowSize * 2 + 1 - c; i++)
									contextIndex -= this.vocabulary.getStartPointer();		//��Ϊ�е��ʱ����ˣ�����model,theta�����������ı�λ��
									if(contextIndex < 0) continue;			//��ǰ�ʱ����˵���
									this._models[contextIndex].add(e);
								}
							}
						}//if(this.modelType == ModelType.CBOW)
						else if(this.modelType == ModelType.Skip_gram) {
							for (int i = randomWindow; i < this.windowSize * 2 + 1 - randomWindow; i++) {			//������ǰ�ʵ�������
								if (i != this.windowSize)					//�ǵ�ǰ�ʵ������ģ������ǵ�ǰ��
								{
									c =	sentence_position -	this.windowSize + i;			//c={sp-w,sp-w+1, ..., sp-1, sp+1, ..., sp+w-1, sp+w}
									if (c <	0) continue;
									if (c >= sentence.length) continue;			//for (int i = c; i < this.windowSize * 2 + 1 - c; i++)
									contextWord = sentence[c];
									contextIndex = this.vocabulary.getWordIndex(contextWord);
									contextX = this.vocabulary.getWord(contextWord);
									if(contextIndex == -1) continue;			//for (int i = c; i < this.windowSize * 2 + 1 - c; i++)
									contextIndex -= this.vocabulary.getStartPointer();		//��Ϊ�е��ʱ����ˣ�����model,theta�����������ı�λ��
									if(contextIndex < 0) continue;			//��ǰ�ʱ����˵���
									ehs = new CVector(this.dimensions);		//e=0
									ens = new CVector(this.dimensions);		//e=0
									// HIERARCHICAL	SOFTMAX
									if (this.trainMethod == TrainMethod.HS || this.trainMethod == TrainMethod.BOTH) {
										for	(int l = 0;	l <	contextX.code.size(); l++)		//��huffman·���ֲ�SoftMax
										{
											thetaIndex = contextX.point.get(l);
											// Propagate hidden -> output
											f = this._models[wordIndex].multiply(this._huffmanTheta[thetaIndex]);	//f = v(��) * theta(u,j-1); u��context(��)
											if (f < -this.expTable.getMaxX()) continue;
											else if (f > this.expTable.getMaxX()) continue;
											else f = expTable.getSigmoid(f);								//sigmoid����
											// 'g' is the	gradient multiplied	by the learning	rate
											g = (1 - contextX.code.get(l) - f) * this.learnRate;				//ƫ������ѧϰ��
											// Propagate errors output ->	hidden
											ehs.add(this._huffmanTheta[thetaIndex].new_Multi(g));				//e += g * theta
											// Learn weights hidden -> output
											this._huffmanTheta[thetaIndex].add(this._models[wordIndex].new_Multi(g));	//theta += g * x
										}
										this._models[wordIndex].add(ehs);				// Learn weights input -> hidden
									}
									// NEGATIVE SAMPLING
									if (this.trainMethod == TrainMethod.NS || this.trainMethod == TrainMethod.BOTH) {
										for (int d = 0; d < this.negative + 1; d++) {
											if (d == 0)
											{
												thetaIndex = wordIndex;
												label =	1;					//1������
											}
											else
											{
												thetaIndex = this.vocabulary.negSamplingWord();
												if (thetaIndex == wordIndex) continue;
												label =	0;					//negative������
											}
											f = this._models[contextIndex].multiply(this._negTheta[thetaIndex]);
											if (f > this.expTable.getMaxX()) g = (label - 1) * this.learnRate;
											else if (f < -this.expTable.getMaxX()) g = (label - 0) * this.learnRate;
											else g = (label - expTable.getSigmoid(f)) * this.learnRate;
											ens.add(this._negTheta[thetaIndex].new_Multi(g));				//e += g * theta
											this._negTheta[thetaIndex].add(this._models[contextIndex].new_Multi(g));
										}
										this._models[contextIndex].add(ens);// Learn weights input -> hidden
									}
								}//if (i != this.windowSize)
							}//for (int i = c; i < this.windowSize * 2 + 1 - c; i++)	
						}//if(this.modelType == ModelType.Skip_gram)
					}//for(int sentence_position = 0; sentence_position < this.passags.getSentence(sentenceIndex); sentence_position++) {
				}//for(int sentenceIndex = 0; sentenceIndex < this.passags.getSentenceCount(); sentenceIndex++)
			}//for(localIteratorNumber = 1; localIteratorNumber <= this.iteratorNumber; localIteratorNumber++)
		}//if(this.initialized)
	}
	
	/**
	 * @return ģ�͵�����*/
	public IVector[] getModels() {
		return this._models;
	}
	
	/**
	 * ���ݴʵ��дʵ�������ȡ����
	 * @param ĳ���ʵ�����
	 * @return �����*/
	public T getWordByIndex(int index) {
		T ret = null;
		HWord<T> word = this.vocabulary.getWordByIndex(index);
		if(word != null) ret = word.word;
    	return ret;
	}
	
	/**
	 * ������ļ�file
	 * @param file �ᱻд����ļ�·��
	 * @throws IOException */
	public void outputFile(String file) throws IOException {
		File f = new File(file);
        if (f.exists()) {
            f.delete();
        }
        f.createNewFile();
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		String str;
        out.write(this.vocabulary.getVocabularyLength() + " " + this.dimensions + "\n");
        for(int i = this.vocabulary.getStartPointer(); i < this.vocabulary.getVocabularyFullSize(); i++) {
        	HWord<T> word = this.vocabulary.getWordByIndex(i);
        	out.write(word.word + " ");
        	for(int j = 0; j < this._models[i - this.vocabulary.getStartPointer()].getVector().length; j++) {
        		if(j != 0) out.write(" ");
        		str = _models[i - this.vocabulary.getStartPointer()].getVector()[j] + "";
        		out.write(str);
        	}
        	if(i != this._models.length - 1) out.write("\n");
        }
        out.flush();
        out.close();
	}
}
