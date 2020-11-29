package com.jindreamit.practice.leetcode.binary.search;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static int binarySearch(int[] array, int v, int start, int end) {
        if (v>array[end-1])
            return -1;
        if (v<array[start])
            return -1;
        int mid = (start + end) / 2;
        log.info("start:{},end:{}", start, end);
        log.info("mid:{}", mid);
        printArray(array, start, end);

        return array[mid] == v ? mid : array[mid] > v ? binarySearch(array, v, start, mid) : binarySearch(array, v, mid, end);
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 6, 8, 10, 19, 20, 21, 34, 56, 87, 99};
        System.out.println(binarySearch(a, 5, 0, a.length));
    }

    private static void printArray(int[] array, int start, int end) {
        for (int i = start; i < end; i++) {
            log.info(array[i] + "");
        }
    }
}
