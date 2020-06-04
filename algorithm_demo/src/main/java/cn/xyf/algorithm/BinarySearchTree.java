package cn.xyf.algorithm;

import java.util.LinkedList;

/**
 * 二叉搜索树增查删
 */
public class BinarySearchTree {
    static class Node{
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
     * 删除
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
    public void print(Node root) {
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

        tree.print(root);

//        System.out.println(tree.getMin(root).data);
//        System.out.println(tree.getMax(root).data);

        tree.delete(root, 16);
        tree.print(root);
    }
}
