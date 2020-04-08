package cn.xyf.algorithm.hash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 不带虚拟节点的一致性Hash算法
 */
public class ConsistentHash {
    // 待添加入Hash环的服务器列表
    private static String[] servers = {
            "192.168.56.120:6379",
            "192.168.56.121:6379",
            "192.168.56.122:6379",
            "192.168.56.123:6379",
            "192.168.56.124:6379",
    };
    // key表示服务器的hash值，value表示服务器的名称
    private static SortedMap<Integer, String> nodeMap = new TreeMap<>();

    // 程序初始化，将所有的服务器放入sortedMap中
    static {
        for (String server : servers) {
            int hash = getHash(server);
            System.out.println("[" + server + "]加入集合中, 其Hash值为" + hash);
            nodeMap.put(hash, server);
        }
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值
     */
    private static int getHash(String str) {
         final int p = 16777619;
         int hash = (int)2166136261L;

         for (int i = 0; i < str.length(); i++) {
             hash = (hash ^ str.charAt(i)) * p;
         }

         hash += hash << 13;
         hash ^= hash >> 7;
         hash += hash << 3;
         hash ^= hash >> 17;
         hash += hash << 5;

         // 如果算出来的值为负数则取其绝对值
         if (hash < 0) {
             hash = Math.abs(hash);
         }

         return hash;
    }

    /**
     * 使用hashcode算法计算服务器的Hash值
     */
    private static int getHash1(String str) {
        int hash = str.hashCode();

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    /**
     * 得到应当路由到的结点
     */
    public static String getServer(String addr) {
        int hash = getHash(addr);
        // 得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = nodeMap.tailMap(hash);
        int index = subMap.isEmpty() ? nodeMap.firstKey() : subMap.firstKey();

        System.out.printf("%s被路由到节点[%s]\n", addr, nodeMap.get(index));
        return nodeMap.get(index);
    }

    public static void main(String[] args) {
        // System.out.println("192.168.1.0:1111".hashCode());

        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
        for (String node : nodes) {
            System.out.println("[" + node + "]的hash值为" + getHash(node) + ", 被路由到结点[" + getServer(node) + "]");
        }
    }
}
