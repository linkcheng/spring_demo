package cn.xyf.algorithm.hash;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * 布隆过滤器
 */
public class BloomFilter {
    private static int DEFAULT_SIZE = 1 << 20;
    // 标志位容器
    private static BitSet bitSet = new BitSet(DEFAULT_SIZE);

    private static final int[] seeds = new int[] { 5, 7, 11, 13, 31, 37, 61 };
    // 生成多个哈希函数
    private SimpleHash[] hashFuncs = new SimpleHash[seeds.length];


    public BloomFilter() {
        for (int i = 0; i < seeds.length; i++) {
            hashFuncs[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    public void add(String value) {
        for (SimpleHash hashFunc : hashFuncs) {
            int hash = hashFunc.hash(value);
            bitSet.set(hash, true);
        }
    }

    public boolean contains(String value) {
        if (null == value) {
            return false;
        }

        boolean ret = true;
        for (SimpleHash hashFunc : hashFuncs) {
            int hash = hashFunc.hash(value);
            ret = ret && bitSet.get(hash);
        }
        return ret;
    }

    public static void main(String[] args) {
//        System.out.println(1<<10);

        BloomFilter bf = new BloomFilter();
        List<String> strList = new ArrayList<>();
        strList.add("123456");
        strList.add("hello word");
        strList.add("transDocId");
        strList.add("123456");
        strList.add("transDocId");
        strList.add("hello word");
        strList.add("test");

        for (int i=0; i<strList.size(); i++) {
            String s = strList.get(i);
            boolean flag = bf.contains(s);

            if(flag){
                System.out.println(i+","+s);
            }else{
                bf.add(s);
            }
        }
    }
}


/**
 * hash 函数生成
 */
class SimpleHash {
    private int cap;
    private int seed;

    SimpleHash(int cap, int seed) {
        this.cap = cap;
        this.seed = seed;
    }

    int hash(String value) {
        int result = 0;
        int len = value.length();
        for (int i = 0; i < len; i++) {
            result = seed * result + value.charAt(i);
        }
        return (cap - 1) & result;
    }
}
