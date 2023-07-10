package czzVector;

/**
 * �����ӿڣ�ά�������ȣ�����
 *
 * @author CZZ
 */
public interface IVector {

    /**
     * ����һ���µ�����
     */
    void copy(IVector v);

    /**
     * ������һ������������Ϊsize
     */
    void resize(int size);

    /**
     * ������һ������������Ϊsize��ÿ������ʼ��Ϊseed
     */
    void resizeLoad(int size, float seed);

    /**
     * ���������з������seed
     */
    void load(float seed);

    /**
     * ������������
     */
    float[] getVector();

    /**
     * ����������ά��
     */
    int getSize();

    /**
     * ���������ĳ��ȣ�ģ��2-������
     */
    float getLength();

    /**
     * ����������
     */
    void multiply(float number);

    /**
     * ���ˣ�����һ���µ�������
     */
    IVector new_Multi(float number);

    /**
     * �����˷�
     */
    float multiply(IVector v2);

    /**
     * ������Ӧ�������
     */
    void add(IVector v2);

    /**
     * ������Ӧ������ӣ�����һ���µ�������
     */
    IVector new_Add(IVector v2);

    /**
     * ����֮���ŷʽ����
     */
    float distance(IVector v);
}
