package cn.xyf.algorithm;

import java.util.LinkedList;

public class SlidingWindowMaxArray {
    public static int[] getMaxWindow(int[] arr,int w){
        if (arr == null || arr.length < w || w < 1){
            return null;
        }
        LinkedList<Integer> qmax = new LinkedList<Integer>();//用来记录最大值的更新
        int[] res = new int[arr.length-w+1];//用来记录结果
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            //1到4步骤的过程
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[i]){
                qmax.pollLast();
            }
            qmax.addLast(i);

            //第5步:过期计算
            if (qmax.peekFirst() == i-w){
                System.out.println(qmax.peekFirst());
                qmax.pollFirst();
            }

            //第6步:记录res值
            if (i-w >= -1){
                res[index++] = arr[qmax.peekFirst()];
            }
        }
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {4, 3, 5, 4, 3, 3, 6, 7};
        int w = 3;
        printArray(getMaxWindow(arr, w));
    }
}
