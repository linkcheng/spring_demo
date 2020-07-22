package cn.xyf.algorithm;

import java.util.ArrayList;

public class SkipList {
    private SkipListNode head;
    private final static int MAX_HEIGHT = 4;
    private final static double PROB = 0.5;

    public SkipList() {
        this.head = new SkipListNode(Integer.MIN_VALUE, 1);
    }

    public void insert(int val) {
        int height = getHeight();
        SkipListNode node = new SkipListNode(val, height);

        int max_height = head.nextNodes.size();
        // 头结点为最高
        while (head.nextNodes.size() < height) {
            head.nextNodes.add(node);
        }

        int min_height = Math.min(height, max_height);
        // 垂直方向移动
        for (int lvl=min_height-1; lvl>=0; lvl--) {
            // 水平方向移动，直到 val <= 节点的值
            SkipListNode cur = getLastLessNode(head, lvl, val);
            SkipListNode next = cur.nextNodes.get(lvl);
            cur.nextNodes.set(lvl, node);
            node.nextNodes.set(lvl, next);
        }
    }

    public SkipListNode search(int val) {
        int height = head.nextNodes.size();
        for (int lvl=height-1; lvl>=0; lvl--) {
            SkipListNode next = getLastLessNode(head, lvl, val).nextNodes.get(lvl);
            if (next!=null && next.val == val) {
                return next;
            }
        }
        return null;
    }

    public SkipListNode remove(int val) {
        SkipListNode node = search(val);
        if (node == null) {
            return null;
        }

        int height = head.nextNodes.size();

        for (int lvl=height-1; lvl>=0; lvl--) {
            SkipListNode cur = getLastLessNode(head, lvl, val);
            SkipListNode next = cur.nextNodes.get(lvl);
            if (next!=null && next.val == val) {
                cur.nextNodes.set(lvl, next.nextNodes.get(lvl));
            }
        }
        return node;
    }

    /**
     * 返回当前层上，第一个 >= val 的节点的前一个节点
     * @param cur 头结点
     * @param lvl 层
     * @param val 值
     * @return next 节点
     */
    private SkipListNode getLastLessNode(SkipListNode cur, int lvl, int val) {
        SkipListNode next = cur.nextNodes.get(lvl);
        while (next!=null && val > next.val) {
            cur = next;
            next = cur.nextNodes.get(lvl);
        }
        return cur;
    }

    /**
     * 计算高度
     */
    private int getHeight() {
        int height = 1;
        while (Math.random() > PROB && height < MAX_HEIGHT) {
            ++height;
        }
        return height;
    }

    public static void main(String[] args) {
        SkipList list = new SkipList();

        // insert
        for (int i=0; i<32; i+=2) {
            list.insert(i);
        }

        // print
        SkipListNode cur = list.head;
        while (cur != null) {
            System.out.println("val="+cur.val+",height="+cur.nextNodes.size());
            cur = cur.nextNodes.get(0);
        }

        // search
        System.out.println(list.search(4));
        System.out.println(list.search(5));
        System.out.println(list.search(40));

        // remove
        list.remove(4);
        list.remove(5);
        list.remove(40);

        // print
        cur = list.head;
        while (cur != null) {
            System.out.println("val="+cur.val+",height="+cur.nextNodes.size());
            cur = cur.nextNodes.get(0);
        }
    }
}


class SkipListNode {
    int val;
    ArrayList<SkipListNode> nextNodes;

    SkipListNode(int val, int height) {
        this.val = val;
        nextNodes = new ArrayList<>(height);
        for (int i=0; i<height; i++) {
            nextNodes.add(null);
        }
    }
    SkipListNode(int val) {
        this(val, 1);
    }
}
