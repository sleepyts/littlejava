package Sort;

import com.google.common.base.Predicates;

import java.util.Arrays;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        System.out.println(Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static Object[] mergeSort(Object[] arr, int left, int right) {
        if (left == right)
            return new Object[] { arr[left] };
        int mid = (left + right) / 2;
        Object[] larr = mergeSort(arr, left, mid);
        Object[] rarr = mergeSort(arr, mid + 1, right);
        Object[] ret = new Object[right - left + 1];
        int p1 = 0, p2 = 0, p = 0;
        while (p1 < larr.length && p2 < rarr.length) {
            try {
                Comparable<Object> leftEle = (Comparable<Object>) larr[p1];
                Comparable<Object> rightEle = (Comparable<Object>) rarr[p2];
                ret[p++] = leftEle.compareTo(rightEle) <= 0 ? larr[p1++] : rarr[p2++];
            } catch (Exception e) {
                throw new RuntimeException("元素不支持排序");
            }
        }

        while (p1 < larr.length) {
            ret[p++] = larr[p1++];
        }
        while (p2 < rarr.length) {
            ret[p++] = rarr[p2++];
        }

        return ret;
    }

    // 主函数，快速排序的入口
    public static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            // 找到分区点
            int partitionIndex = partition(array, left, right);

            // 对分区点左侧进行排序
            quickSort(array, left, partitionIndex - 1);

            // 对分区点右侧进行排序
            quickSort(array, partitionIndex + 1, right);
        }
    }

    // 分区函数，将数组划分为两个部分
    private static int partition(int[] array, int left, int right) {
        int pivot = array[right];
        int p = left - 1;
        for (int j = left; j < right; j++) {
            if (array[j] < pivot) {
                j++;
            }
            swap(array, p, j);
        }
        swap(array, p + 1, right);
        return p + 1;
    }

    // 交换数组中两个元素的位置
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
