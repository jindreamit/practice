package com.jindreamit.practice.leetcode.array.merge;

import java.util.Arrays;

public class Solution {
    static public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null) {
            int m = nums2.length / 2;
            if (nums2.length % 2 == 0) {
                return ((double) nums2[m - 1] + (double) nums2[m]) / 2.0;
            } else return nums2[m];
        }
        if (nums2 == null) {
            int m = nums1.length / 2;
            if (nums1.length % 2 == 0) {
                return ((double) nums1[m - 1] + (double) nums1[m]) / 2.0;
            } else return nums1[m];
        }
        int n = nums1.length + nums2.length;
        int[] all = new int[n];
        int i = 0;
        int j = 0;
        for (int k = 0; k < n; k++) {
            System.out.println("i:" + i + " j: " + j);
            if (i==nums1.length) {
                all[k] = nums2[j];
                j++;

            } else if (j==nums2.length) {
                all[k] = nums1[i];
                i++;
            } else if (nums1[i] <= nums2[j]) {
                all[k] = nums1[i];
                i++;
            } else {
                all[k] = nums2[j];
                j++;
            }
            System.out.println("i:" + i + " j: " + j);
            System.out.println(Arrays.toString(all));
            System.out.println("======================");
        }

        if (n % 2 == 0) return ((double) all[n / 2] + (double) all[n / 2 - 1]) / 2.0;
        else return all[n / 2];
    }

    public static void main(String[] args) {
        int[] a = {1, 3};
        int[] b = {2};
        System.out.println(findMedianSortedArrays(a, b));
    }
}
