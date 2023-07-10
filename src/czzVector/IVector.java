package czzVector;

/**
 * 向量接口，维数，长度，范数
 *
 * @author CZZ
 */
public interface IVector {

    /**
     * 复制一个新的向量
     */
    void copy(IVector v);

    /**
     * 新申请一个向量，长度为size
     */
    void resize(int size);

    /**
     * 新申请一个向量，长度为size，每个都初始化为seed
     */
    void resizeLoad(int size, float seed);

    /**
     * 将向量所有分量变成seed
     */
    void load(float seed);

    /**
     * 返回向量数组
     */
    float[] getVector();

    /**
     * 返回向量的维度
     */
    int getSize();

    /**
     * 返回向量的长度（模，2-范数）
     */
    float getLength();

    /**
     * 向量的数乘
     */
    void multiply(float number);

    /**
     * 数乘（返回一个新的向量）
     */
    IVector new_Multi(float number);

    /**
     * 向量乘法
     */
    float multiply(IVector v2);

    /**
     * 向量对应分量相加
     */
    void add(IVector v2);

    /**
     * 向量对应分量相加（返回一个新的向量）
     */
    IVector new_Add(IVector v2);

    /**
     * 向量之间的欧式距离
     */
    float distance(IVector v);
}
