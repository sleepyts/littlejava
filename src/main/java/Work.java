import cn.hutool.core.util.RandomUtil;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Work {
    public static void main(String[] args) {
        X3T10();
    }

    /**
     * 第一题：
     * i = 0：ababcabccabcacbab 的 0-4 比较 ababc vs abcac，不匹配。
     * i = 1：ababcabccabcacbab 的 1-5 比较 babc vs abcac，不匹配。
     * i = 2：ababcabccabcacbab 的 2-6 比较 abcab vs abcac，不匹配。
     * i = 3：ababcabccabcacbab 的 3-7 比较 bcabc vs abcac，不匹配。
     * i = 4：ababcabccabcacbab 的 4-8 比较 cabcc vs abcac，不匹配。
     * i = 5：ababcabccabcacbab 的 5-9 比较 abcc vs abcac，不匹配。
     * i = 6：ababcabccabcacbab 的 6-10 比较 bccab vs abcac，不匹配。
     * i = 7：ababcabccabcacbab 的 7-11 比较 ccabc vs abcac，不匹配。
     * i = 8：ababcabccabcacbab 的 8-12 比较 cabcac vs abcac，匹配，找到位置 8。
     *
     * 对于模式 abcac：
     *
     * NEXT[0] = 0（第一个字符没有前后缀相同）
     * NEXT[1] = 0（前缀 a 和后缀 b 不同）
     * NEXT[2] = 0（前缀 ab 和后缀 c 不同）
     * NEXT[3] = 1（前缀 abc 和后缀 a 匹配）
     * NEXT[4] = 2（前缀 abca 和后缀 ac 匹配）
     * 所以，NEXT 表为：[0, 0, 0, 1, 2]
     * 
     * i = 0 (text)，j = 0 (pattern)：
     * a == a，继续，i++, j++。
     * i = 1 (text)，j = 1 (pattern)：
     * b == b，继续，i++, j++。
     * i = 2 (text)，j = 2 (pattern)：
     * a == c，不匹配，j = NEXT[j-1]，j 更新为 0，继续比较。
     * i = 2 (text)，j = 0 (pattern)：
     * a == a，继续，i++, j++。
     * i = 3 (text)，j = 1 (pattern)：
     * b == b，继续，i++, j++。
     * i = 4 (text)，j = 2 (pattern)：
     * c == c，继续，i++, j++。
     * i = 5 (text)，j = 3 (pattern)：
     * a == a，继续，i++, j++。
     * i = 6 (text)，j = 4 (pattern)：
     * b == c，不匹配，j = NEXT[j-1]，j 更新为 2。
     * i = 6 (text)，j = 2 (pattern)：
     * b == c，不匹配，j = NEXT[j-1]，j 更新为 0。
     * i = 6 (text)，j = 0 (pattern)：
     * b == a，不匹配，i++。
     * 继续这个过程...
     * 当 i = 8，j = 0 时，c == a，继续比较，最终到达：
     * i = 10 (text)，j = 0 (pattern)：
     * a == a，继续，i++, j++。
     * i = 11 (text)，j = 1 (pattern)：
     * b == b，继续，i++, j++。
     * i = 12 (text)，j = 2 (pattern)：
     * c == c，继续，i++, j++。
     * i = 13 (text)，j = 3 (pattern)：
     * a == a，继续，i++, j++。
     * i = 14 (text)，j = 4 (pattern)：
     * c == c，继续，i++, j++。
     * i = 15 (text)，j = 5 (pattern)：
     * 匹配成功，模式 abcac 在文本中的位置为 8。
     */

    public static void X3T9(){
        enum Color{
            R,
            W,
            B,
        }
        int n=100;
        Color[] colors=new Color[n];
        for (int i = 0; i < n; i++) {
            colors[i]=(Color.values()[new Random().nextInt(0,3)]);
        }
        int left=-1,right=n,p=0,pivot=Color.W.ordinal();
        while(p<right){
            if (colors[p].ordinal()<pivot){
                swap(colors,++left,p++);
            }else if (colors[p].ordinal()==pivot){
                p++;
            }else{
                swap(colors,--right,p);
            }
        }
        System.out.println(Arrays.toString(colors));
    }

    /**
     * 时间复杂度O(N²)
     */
    public static void X3T10(){
        class Point{
            float[] arrs;
            Point(int k){
                arrs=new float[k];
            }
            float calD(Point p){
                float sum=0;
                for (int i=0;i<arrs.length;i++){
                    sum+= Math.pow(arrs[i]-p.arrs[i],2);
                }
                return (float) Math.pow(sum,0.5);
            }
        }
        int k=4,n=20;
        float minD=Float.MAX_VALUE;
        Point[] points=new Point[20];
        for (int i = 0; i < n; i++) {
            Point point = new Point(k);
            for (int j = 0; j < k; j++)
                point.arrs[j]=new Random().nextFloat(1000f);
            points[i]=point;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                minD=Math.min(minD,points[i].calD(points[j]));
            }
        }
        System.out.println(minD);
    }
    private static void swap(Object[] arr, int i, int j){
        Object temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}
class MatrixVectorMultiplication {
    public static double[] rowMajorMultiplication(double[][] matrix, double[] vector) {
        int N = matrix.length;
        double[] result = new double[N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }
    // 列优先访问矩阵与向量相乘
    public static double[] columnMajorMultiplication(double[][] matrix, double[] vector) {
        int N = matrix.length;
        double[] result = new double[N];


        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }

        return result;
    }
    public static void blockMatrixMultiply(double[][] A, double[][] B, int N, int blockSize) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        double[][] C=new double[N][N];
        pool.submit(() -> {
            for (int ii = 0; ii < N; ii += blockSize) {
                for (int jj = 0; jj < N; jj += blockSize) {
                    for (int kk = 0; kk < N; kk += blockSize) {
                        // 并行处理每个块
                        int finalIi = ii;
                        int finalJj = jj;
                        int finalKk = kk;
                        pool.submit(() -> {
                            for (int i = finalIi; i < Math.min(finalIi + blockSize, N); i++) {
                                for (int j = finalJj; j < Math.min(finalJj + blockSize, N); j++) {
                                    for (int k = finalKk; k < Math.min(finalKk + blockSize, N); k++) {
                                        C[i][j] += A[i][k] * B[k][j];
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }).get(); // 等待所有任务完成
    }
    public static void fun2(int n,int blockSize) throws ExecutionException, InterruptedException {
        double[][] A=new double[n][n],B=new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j]=RandomUtil.randomDouble();
                B[i][j]=RandomUtil.randomDouble();
            }
        }
        long start=System.nanoTime();
        blockMatrixMultiply(A,B,n,blockSize);
        System.out.printf("矩阵大小为%d,分块大小为%d,运算时间:%s\n",n,blockSize,(System.nanoTime()-start)/1_000_000.0+"ms");
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        int n=16384;
//        double [][] mat=new double[n][n];
//        double [] vec=new double[n];
//        for (int i = 0; i < n; i++) {
//            vec[i]=RandomUtil.randomDouble();
//            for (int j = 0; j < n; j++) {
//                mat[i][j]= RandomUtil.randomDouble();
//            }
//        }
//        long start=System.nanoTime();
//        rowMajorMultiplication(mat,vec);
//        System.out.println((System.nanoTime()-start)/1_000_000.0+"ms");
//        start=System.nanoTime();
//        columnMajorMultiplication(mat,vec);
//        System.out.println((System.nanoTime()-start)/1_000_000.0+"ms");
        fun2(4096,128);
        fun2(4096,64);
        fun2(4096,32);
        fun2(4096,16);
        fun2(4096,8);
        fun2(4096,4);

    }
}
