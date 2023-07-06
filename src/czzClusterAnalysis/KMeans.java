package czzClusterAnalysis;

import java.util.ArrayList;

import czzSelectItem.Selector;
import czzVector.IVector;

/**
 * k��ֵ�����㷨���̳��Գ����ࡰ���ࡱ*/
public class KMeans<T> extends Cluster<T>{

	@Override
	public boolean runCluster(int k) {
		boolean ret = false;
		int n = this.nodes.size();
		if(n > 0 && k > 0 && k < n) {
			int[] seeds = Selector.kInN(n, k);							//��0����n-1��n�������ѡ��k��
			int[] centerNum = new int[k];								//ÿ�����������м���Ԫ��
			int i, index, j;
			ClusterNode<T> temp;
			ArrayList<ClusterNode<T> > arr = new ArrayList<ClusterNode<T> >();			//����
			float minDistance, tempDistance;										//��С����
			int minIndex;
			for(i = 0; i < k; i++) {
				index = seeds[i];
				this.nodes.get(index).label = i;					//ѡ��k�����Ľڵ���Ϊ��ʼ
				temp = this.nodes.get(i);
				this.nodes.set(i, this.nodes.get(index));
				this.nodes.set(index, temp);
				temp = new ClusterNode<T>(null, this.nodes.get(i)._vector);			//��ʼ��k����������
				temp.label = i;
				arr.add(temp);
				centerNum[i] = 1;					//������һ��Ԫ��
			}
			//arr.trimToSize();
			IVector vec;
			for(i = k; i < n; i++) {
				temp = this.nodes.get(i);
				minDistance = temp._vector.distance(arr.get(0)._vector);
				minIndex = 0;
				for(j = 1; j < arr.size(); j++) {
					tempDistance = temp._vector.distance(arr.get(j)._vector);
					if(minDistance > tempDistance) {
						minDistance = tempDistance;
						minIndex = j;
					}
				}
				temp.label = arr.get(minIndex).label;
				vec = arr.get(minIndex)._vector;					//����
				vec.multiply(centerNum[minIndex]);
				vec.add(temp._vector);
				centerNum[minIndex]++;
				vec.multiply(1.0f / centerNum[minIndex]);			//�����µ�����
			}
			ret = true;
		}
		return ret;
	}

}
