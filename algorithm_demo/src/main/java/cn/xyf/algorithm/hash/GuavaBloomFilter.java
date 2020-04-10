package cn.xyf.algorithm.hash;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

public class GuavaBloomFilter {
    public static void main(String[] args) {
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 1000, 0.0001);
        bf.put("121");
        bf.put("122");
        bf.put("123");

        System.out.println(bf.mightContain("12321"));
    }
}
