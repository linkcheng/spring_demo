package cn.xyf.algorithm;

import java.util.Arrays;
import java.util.LinkedList;

class MyClassLoader extends ClassLoader {

}

/**
 * 二叉搜索树增查删
 */
@SuppressWarnings("all")
public class BinarySearchTree {
    public static class Node{
        int data;
        Node left;
        Node right;

        Node(int key) {
            data = key;
            left = null;
            right = null;
        }
    }

    /**
     * 插入
     */
    public void insert(Node root, int key) {
        if (root == null) {
            return;
        }

        if(key >= root.data) {
            if (root.right == null) {
                root.right = new Node(key);
            } else {
                insert(root.right, key);
            }
        } else {
            if (root.left == null) {
                root.left = new Node(key);
            } else {
                insert(root.left, key);
            }
        }
    }

    /**
     * 找最小
     */
    public Node getMin(Node root) {
        if (root == null) return null;
        return root.left==null ? root : getMin(root.left);
    }

    /**
     * 找最大
     */
    public Node getMax(Node root) {
        if (root == null) return null;
        return root.right==null ? root : getMax(root.right);
    }

    /**
     * 查找
     */
    public Node find(Node root, int key) {
        if(root == null) {
            return null;
        }
        if (root.data < key) {
            return find(root.right, key);
        } else if (root.data > key) {
            return find(root.left, key);
        } else {
            return root;
        }
    }

    /**
     * 删除，返回新树的头结点
     */
    public Node delete(Node root, int key) {
        if (root == null) {
            return null;
        }

        if (key > root.data) {
            root.right = delete(root.right, key);
        } else if (key < root.data) {
            root.left = delete(root.left, key);
        } else {
            // 如果有两个孩子，则在右子树中找到最小的替换当前节点，然后去右子树删除那个最小的
            if (root.right != null && root.left != null) {
                Node min = getMin(root.right);
                root.data = min.data;
                root.right = delete(root.right, min.data);
            } else {
                root = (root.left != null) ? root.left : root.right;
            }
        }

        return root;
    }

    /**
     * 打印树
     */
    public static void print(Node root) {
        if (root == null) {
            return;
        }

        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node t = queue.poll();
            if (t == null) {
                System.out.print("null, ");
            } else {
                System.out.print(t.data+", ");
                queue.add(t.left);
                queue.add(t.right);
            }
        }
        System.out.println();
    }

    /**
     * 通过后续遍历得到的数组 array ，重构 bst
     * @param array 后续遍历得到的数组
     * @return 树的头结点
     */
    public static Node postArrayToBST(int[] array) {
        return build(array, 0, array.length - 1);
    }

    /**
     * 通过后续遍历得到的数组 array ，重构 bst
     * @param array 后续遍历得到的数组
     * @return 树的头结点
     */
    public static Node build(int[] array, int left, int right) {
        if (array == null || array.length == 0 || left > right) {
            return null;
        }

        // left <= right
        Node head = new Node(array[right]);

        // right == left 有且仅有一个数
        if (right == left) {
            return head;
        }

        // left < right
        int pos = - 1;
        // 在 [left...right-1] 范围找切分点, 从左到右，找到最后一个比right小的地方位置
//        pos = search(array, left, right-1, array[right]);

        // 在 [left...right-1] 范围找切分点, 从左到右，找到最后一个比right小的地方位置
        // 二分查找
        pos = binarySearch(array, left, right-1, array[right]);

        head.left = build(array, left, pos);
        head.right = build(array, pos+1, right-1);
        return head;
    }

    /**
     * 在 array 中 left 到 right 范围找到最右端比 key 小的值
     * @param array 不完全有序数组
     * @param key
     * @return
     * 时间复杂度 O(n)
     */
    public static int search(int[] array, int left, int right, int key) {
        int pos = left - 1;

        // 在 [left...right-1] 范围找切分点, 从左到右，找到最后一个比right小的地方位置
        for (int i=left; i<right; i++) {
            if(array[i] < array[right]) {
                pos = i;
            }
        }
        return pos;
    }

    /**
     * 在 array 中 left 到 right 范围找到最右端比 key 小的值
     * @param array 不完全有序数组
     * @param key
     * @return
     * 时间复杂度 O(log n)
     */
    private static int binarySearch(int[] array, int left, int right, int key) {
        int pos = left - 1;

        // 在 [left...right-1] 范围找切分点, 从左到右，找到最后一个比right小的地方位置
        while (left <= right) {
            int mid = left + ((right - left)>>1);
            if (array[mid] < array[right]) {
                pos = mid;
                left = mid + 1;
            } else {
                right = mid -1;
            }
        }

        return pos;
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        Node root = new Node(10);
        tree.insert(root, 5);
        tree.insert(root, 15);
        tree.insert(root, 18);
        tree.insert(root, 16);
        tree.insert(root, 13);
        tree.insert(root, 7);
        tree.insert(root, 2);

//        BinarySearchTree.print(root);

//        System.out.println(tree.getMin(root).data);
//        System.out.println(tree.getMax(root).data);

        Node delete = tree.delete(root, 16);
        BinarySearchTree.print(delete);

//        int[] array = {2, 4, 3, 6, 8, 7, 5};
//        int[] array = {1, 3, 2, 4, 5};
//        int[] array = {1, 2, 3, 4, 5};
//        int[] array = {3, 2, 1};
//        System.out.println(Arrays.toString(array));
//        Node build = BinarySearchTree.postArrayToBST(array);
//        System.out.println(build.data);
//        BinarySearchTree.print(build);
    }
}
