package cn.xyf.algorithm.hash;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 带虚拟节点的一致性Hash算法
 */
public class ConsistentHashVirtual {
    // 待添加入Hash环的服务器列表
    private static String[] servers = {
            "192.168.56.120:6379",
            "192.168.56.121:6379",
            "192.168.56.122:6379",
            "192.168.56.123:6379",
            "192.168.56.124:6379",
    };
    // 虚拟节点，key表示虚拟节点的hash值，value表示虚拟节点的名称
    private static SortedMap<Integer, String> virtualNodeMap = new TreeMap<>();
    // 真实结点列表,考虑到服务器上线、下线的场景，即添加、删除的场景会比较频繁，这里使用LinkedList会更好
    private static List<String> realNodes = new LinkedList<>();
    // 虚拟节点的数目
    private static final int VIRTUAL_NODE_NUMBER = 5;

    static {
        // 先把原始的服务器添加到真实结点列表中
        realNodes.addAll(Arrays.asList(servers));
        // 再添加虚拟节点，遍历LinkedList使用foreach循环效率会比较高
        for (String node : realNodes) {
            for (int i = 0; i < VIRTUAL_NODE_NUMBER; i++) {
                String vNode = node + "#VN" + i;
                int hash = getHash(vNode);
                System.out.println("虚拟节点[" + vNode + "]被添加, hash值为" + hash);
                virtualNodeMap.put(hash, vNode);
            }
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
     * 经典的Time33 hash算法
     * */
    public static int getHash1(String key) {
        if(StringUtils.isEmpty(key))
            return 0;
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            key = new String(digest.digest(key.getBytes()));
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        int hash = 5381;
        for (int i = 0; i < key.length(); i++) {
            int cc = key.charAt(i);
            hash += (hash << 5) + cc;
        }
        return hash<0 ? -hash : hash;
    }

    /**
     * 得到应当路由到的结点
     */
    public static String getServer(String addr) {
        int hash = getHash(addr);
        // 得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = virtualNodeMap.tailMap(hash);

        int index = subMap.isEmpty() ? virtualNodeMap.firstKey() : subMap.firstKey();

        System.out.printf("%s被路由到虚拟节点[%s]\n", addr, virtualNodeMap.get(index));
        return virtualNodeMap.get(index).substring(0, virtualNodeMap.get(index).indexOf("#"));
    }

    public static void main(String[] args) {
        String[] nodes = {
                "127.0.0.1:1111",
                "221.226.0.1:2222",
                "10.211.0.1:3333",
        };
        for (String node: nodes) {
            System.out.println("[" + node + "]的hash值为" + getHash(node) + ", 被路由到结点[" + getServer(node) + "]");
        }
    }

}
