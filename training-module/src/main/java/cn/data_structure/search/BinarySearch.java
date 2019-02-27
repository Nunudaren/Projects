package cn.data_structure.search;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author:chendong
 * @Date:2018/10/25
 */
public class BinarySearch {

    public static int binarySearch1(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;
        int middle;

        if (key < array[low] || key > array[high] || low > high) {
            return -1;
        }
        while (low <= high) {
            middle = (low + high) / 2;
            if (key < array[middle]) {
                high = middle - 1;
            } else if (key > array[middle]) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    public static int binarySearch2(int[] array, int key, int low, int high) {

        if (key < array[low] || key > array[high] || low > high) {
            return -1;
        }
        int middle = (low + high) / 2;
        if (key < array[middle]) {
            return binarySearch2(array, key, low, middle - 1);
        } else if (key > array[middle]) {
            return binarySearch2(array, key, middle + 1, high);
        } else {
            return middle;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        int[] array = new int[10];
        for (int i = 0; i < 10; i++) {
            array[i] = new Random().nextInt(100);
        }

        Arrays.sort(array);
        System.out.println(Arrays.toString(array));

//        int[] array2 = {2, 10, 35, 54, 67, 68, 90, 101};

        int key = 53;
//        int index = binarySearch2(array, key, 0, array.length - 1);
        int index = binarySearch1(array, key);
        System.out.println(index);
    }
}
