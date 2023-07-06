package czzSelectItem;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 ���ݸ��ʣ����ѡ��ĳ����Ŀ����Ԫ����
 @author CZZ*/
public class SelectorTest {
	int repeatNum = 10000000;				//���Թ�100000000��Լ7.91s
	int repeatNum2 = 100000000;				//���Թ�100000000��Լ7.91s
	float delta = 1e-3f;
	
	final int expectN = 30;
	float[] expect = new float[expectN];
	
	
	@Before
	public void setUp() throws Exception {
		expect[0] = 2.5f;
		expect[1] = 3.5f;
		expect[2] = 5.5f;
		expect[3] = 6.5f;
		expect[4] = 2f;
		expect[5] = 8.5f;
		expect[6] = 3.9f;
		expect[7] = 5.1f;
		expect[8] = 6.7f;
		expect[9] = 20f;
		expect[10] = 2.1f;
		expect[11] = 15.5f;
		expect[12] = 2.9f;
		expect[13] = 7.3f;
		expect[14] = 4f;
		expect[15] = 11.5f;
		expect[16] = 3.3f;
		expect[17] = 8.5f;
		expect[18] = 7.7f;
		expect[19] = 6f;
		expect[20] = 10.5f;
		expect[21] = 23.5f;
		expect[22] = 14.5f;
		expect[23] = 26.5f;
		expect[24] = 8f;
		expect[25] = 15.2f;
		expect[26] = 9.2f;
		expect[27] = 5.4f;
		expect[28] = 6.6f;
		expect[29] = 2f;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSelect() {
		int i;
		float[] al = new float[5];
		al[0] = (float) 2.5;
		al[1] = (float) 3.5;
		al[2] = (float) 5.5;
		al[3] = (float) 6.5;
		al[4] = (float) 2;
		float suma = 0, sumc = 0;
		int[] counter = new int[5];
		counter[0] = 0;
		counter[1] = 0;
		counter[2] = 0;
		counter[3] = 0;
		counter[4] = 0;
		int e = -1;
		int n = repeatNum;
		for(i = 0; i < n; i++) {
			e = Selector.select(al);
			assertTrue(e >= 0 && e < 5);
			counter[e]++;
		}
		for(i = 0; i < 5; i++) {
			suma += al[i];
			sumc += counter[i];
		}
		float ap[] = new float[5];
		float cp[] = new float[5];
		for(i = 0; i < 5; i++) {
			ap[i] = al[i] / suma;
			cp[i] = counter[i] / sumc;
			assertEquals(ap[i], cp[i], delta);
		}
	}
	
	@Test
	public void testAliasSelect() {
		int i;
		float[] al = new float[5];
		al[0] = (float) 2.5;
		al[1] = (float) 3.5;
		al[2] = (float) 5.5;
		al[3] = (float) 6.5;
		al[4] = (float) 2;
		float suma = 0, sumc = 0;
		int[] counter = new int[5];
		counter[0] = 0;
		counter[1] = 0;
		counter[2] = 0;
		counter[3] = 0;
		counter[4] = 0;
		int e = -1;
		int n = repeatNum;
		float probs[] = Selector.toProbs(al);
		ProbabilityPair[] jq = Selector.alias_setup(probs);
		for(i = 0; i < n; i++) {
			e = Selector.alias_draw(jq);
			assertTrue(e >= 0 && e < 5);
			counter[e]++;
		}
		for(i = 0; i < 5; i++) {
			suma += al[i];
			sumc += counter[i];
		}
		float ap[] = new float[5];
		float cp[] = new float[5];
		for(i = 0; i < 5; i++) {
			ap[i] = al[i] / suma;
			cp[i] = counter[i] / sumc;
			assertEquals(ap[i], cp[i], delta);
		}
	}
	
	@Test
	public void testSelect2() {
		int i;
		float suma = 0, sumc = 0;
		int[] counter = new int[expectN];
		for(i = 0; i < expectN; i++) counter[i] = 0;
		int e = -1;
		int n = repeatNum2;
		for(i = 0; i < n; i++) {
			e = Selector.select(expect);
			assertTrue(e >= 0 && e < expectN);
			counter[e]++;
		}
		for(i = 0; i < 5; i++) {
			suma += expect[i];
			sumc += counter[i];
		}
		float ap[] = new float[5];
		float cp[] = new float[5];
		for(i = 0; i < 5; i++) {
			ap[i] = expect[i] / suma;
			cp[i] = counter[i] / sumc;
			assertEquals(ap[i], cp[i], delta);
		}
	}
	
	@Test
	public void testAliasSelect2() {
		int i;
		float suma = 0, sumc = 0;
		int[] counter = new int[expectN];
		for(i = 0; i < expectN; i++) counter[i] = 0;
		int e = -1;
		int n = repeatNum2;
		float probs[] = Selector.toProbs(expect);
		ProbabilityPair[] jq = Selector.alias_setup(probs);
		for(i = 0; i < n; i++) {
			e = Selector.alias_draw(jq);
			assertTrue(e >= 0 && e < expectN);
			counter[e]++;
		}
		for(i = 0; i < 5; i++) {
			suma += expect[i];
			sumc += counter[i];
		}
		float ap[] = new float[5];
		float cp[] = new float[5];
		for(i = 0; i < 5; i++) {
			ap[i] = expect[i] / suma;
			cp[i] = counter[i] / sumc;
			assertEquals(ap[i], cp[i], delta);
		}
	}
	
	@Test
	public void testKInN() {
		int n = 20;
		int k = 7;
		int m = 1000;
		int i, j;
		int[] sum = new int[n];
		for(i = 0; i < n; i++) {
			sum[i] = 0;
		}
		int[] result;
		for(i = 0; i < m; i++) {
			result = Selector.kInN(n, k);
			for(j = 0; j < k; j++) sum[result[j]]++;
		}
		for(i = 0; i < n; i++) {
			assertEquals(k * 1.0 / n, sum[i] * 1.0 / m,  5e-2);
		}
	}
}
