package com.jindreamit.practice.leetcode.sort;

import java.util.Arrays;

public class HeapSort {

    private static void heapSort(int[] a, int len) {

        int lastParent=len/2;
        for (int i = lastParent; i >= 0; i--) {
            adjustHeap(a,i,len);
        }

//        swap first element and last element
        int t=a[len-1];
        a[len-1]=a[0];
        a[0]=t;
        if (len>1)
            heapSort(a,len-1);

        System.out.println(Arrays.toString(a));
    }

    private static void adjustHeap(int[] a, int parent,int len) {
        int leftChild = 2 * parent + 1;
        int rightChild = 2 * parent + 2;
        if (leftChild > len - 1)
            return;
        if (rightChild > len - 1) {

            if (a[parent] > a[leftChild]) {
                int t = a[parent];
                a[parent] = a[leftChild];
                a[leftChild] = t;
            }

        } else {

            boolean f = a[leftChild] < a[rightChild];
            if (f && a[leftChild] < a[parent]) {
//            swap parent and left child
                int t = a[parent];
                a[parent] = a[leftChild];
                a[leftChild] = t;
            } else if (!f && a[rightChild] < a[parent]) {
//            swap parent and right child
                int t = a[parent];
                a[parent] = a[rightChild];
                a[rightChild] = t;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {23, 12, 19, 45, 21, 7, 56, 25};
        heapSort(a,a.length);
//        for (int i = a.length; i >0 ; i--) {
//            heapSort(a,i);
//        }
//        System.out.println(Arrays.toString(a));
    }
}
