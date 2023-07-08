package czzSelectItem;

import java.util.ArrayList;
import java.util.Random;

/**
 * ѡ��������һ����ֵ��ͬ����Ŀ���У�������ֵռ�����ı������ѡ��һ����Ŀ
 *
 * @author CZZ
 */
public final class Selector {

    /**
     * ����Ȩֱֵ��ѡ��
     */
    public static int select(float[] itemWeights) {
        int ret = -1;
        int n = itemWeights.length;
        float sum = 0;
        for (int i = 0; i < n; i++) {
            sum += itemWeights[i];
        }
        float result = 0;            //������
        if (sum != 0) {                    //ֻҪ����ֵ�Ϳ��ԱȽϴ�С������ǡ����0
            Random random = new Random();
            result = random.nextFloat() * sum;            //[0, 1) * sum
            float tempSum = 0;            //��ʱ��
            for (ret = 0; ret < n; ret++) {
                tempSum += itemWeights[ret];
                if (tempSum > result) break;                //��ΪnextFloat������ҿ����䣬���Բ�ȡ�Ⱥ�
            }
        }
        return ret;
    }

    /**
     * Ȩֵ����ת��Ϊ����������
     */
    public static float[] toProbs(float[] itemWeights) {
        int n = itemWeights.length;
        float[] probs = new float[n];
        float sum = 0;
        int i;
        for (i = 0; i < n; i++) {
            sum += itemWeights[i];
        }
        if (sum != 0) {                    //ֻҪ����ֵ�Ϳ��ԱȽϴ�С������ǡ����0
            for (i = 0; i < n; i++) {
                probs[i] = itemWeights[i] / sum;
            }
        }
        return probs;
    }

    /**
     * https://hips.seas.harvard.edu/blog/2013/03/03/the-alias-method-efficient-sampling-with-many-discrete-outcomes/
     * ,�������������
     */
    public static ProbabilityPair[] alias_setup(float[] probs) {
        int K = probs.length;
        ProbabilityPair[] jq = new ProbabilityPair[K];
        //jq.prop//q  = np.zeros(K)
        //jq.index//J  = np.zeros(K, dtype=np.int)

        //# Sort the data into the outcomes with probabilities
        //# that are larger and smaller than 1/K.
        ArrayList<Integer> smaller = new ArrayList<Integer>();
        ArrayList<Integer> larger = new ArrayList<Integer>();
        int kk;
        for (kk = 0; kk < K; kk++) {
            float prob = probs[kk];
            jq[kk] = new ProbabilityPair();
            jq[kk].prob = K * prob;
            if (jq[kk].prob < 1.0) smaller.add(kk);
            else larger.add(kk);
        }
        //# Loop though and create little binary mixtures that
        //# appropriately allocate the larger outcomes over the
        //# overall uniform mixture.
        int small, large;
        int sn, ln;
        while (smaller.size() > 0 && larger.size() > 0) {
            sn = smaller.size() - 1;
            small = smaller.get(sn);
            smaller.remove(sn);
            ln = larger.size() - 1;
            large = larger.get(ln);
            larger.remove(ln);
            jq[small].index = large;
            jq[large].prob = jq[large].prob - (1.0f - jq[small].prob);
            if (jq[large].prob < 1.0) smaller.add(large);
            else larger.add(large);
        }
        return jq;
    }

    /**
     * �Ӵ����Ŀ�����������ѡ��һ��
     */
    public static int alias_draw(ProbabilityPair[] jq) {
        //Draw sample from a non-uniform discrete distribution using alias sampling.
        int K = jq.length;
        Random random = new Random();
        int kk = (int) (Math.floor(random.nextFloat() * K));
        return random.nextFloat() < jq[kk].prob ? kk : jq[kk].index;
    }

    /**
     * ��0����n-1��n�������ѡ��k��
     *
     * @param n ����
     * @param k ѡ����
     * @return ����Ϊk��ѡ��������
     */
    public static int[] kInN(int n, int k) {
        int[] arr = null;
        int[] selectArray = null;
        if (n > 0 && k > 0 && n > k) {
            int i;
            selectArray = new int[k];
            arr = new int[n];
            Random rand = new Random();
            int index;
            for (i = 0; i < n; i++) {
                arr[i] = i;
            }
            for (i = 0; i < k; i++) {
                index = rand.nextInt(n - i) + i;
                selectArray[i] = arr[index];
                arr[index] = arr[i];
            }
        }
        return selectArray;
    }
}