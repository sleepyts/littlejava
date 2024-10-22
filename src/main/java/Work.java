import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import cn.hutool.core.util.RandomUtil;

public class Work {
    public static void main(String[] args) {
        X5T2();
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

    public static void X3T9() {
        //
        enum Color {
            R,
            W,
            B,
        }
        int n = 100;
        Color[] colors = new Color[n];
        for (int i = 0; i < n; i++) {
            colors[i] = (Color.values()[new Random().nextInt(0, 3)]);
        }
        int left = -1, right = n, p = 0, pivot = Color.W.ordinal();
        while (p < right) {
            if (colors[p].ordinal() < pivot) {
                swap(colors, ++left, p++);
            } else if (colors[p].ordinal() == pivot) {
                p++;
            } else {
                swap(colors, --right, p);
            }
        }
        System.out.println(Arrays.toString(colors));
    }

    /**
     * 时间复杂度O(N²)
     */
    public static void X3T10() {
        class Point {
            float[] arrs;

            Point(int k) {
                arrs = new float[k];
            }

            float calD(Point p) {
                float sum = 0;
                for (int i = 0; i < arrs.length; i++) {
                    sum += Math.pow(arrs[i] - p.arrs[i], 2);
                }
                return (float) Math.pow(sum, 0.5);
            }
        }
        int k = 4, n = 20;
        float minD = Float.MAX_VALUE;
        Point[] points = new Point[20];
        for (int i = 0; i < n; i++) {
            Point point = new Point(k);
            for (int j = 0; j < k; j++)
                point.arrs[j] = new Random().nextFloat(1000f);
            points[i] = point;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                minD = Math.min(minD, points[i].calD(points[j]));
            }
        }
        System.out.println(minD);
    }

    private static void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void X5T1() {
        class fun {

            static int find(int[] arr, int l, int r, int target) {
                if (l > r) {
                    return -1;
                }
                int mid = (l + r) / 2;
                if (arr[mid] == target) {
                    return mid;
                } else if (arr[mid] > target) {
                    return find(arr, l, mid - 1, target);
                } else {
                    return find(arr, mid + 1, r, target);
                }

            }
        }
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = RandomUtil.randomInt(100);
        }
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(fun.find(arr, 0, arr.length, 10));
    }

    public static void X5T2() {
        class maxHeap {
            int[] heap;
            int size;

            // 建堆 O(n)
            public maxHeap(int[] arr) {
                heap = arr;
                size = arr.length;
                for (int i = size / 2 - 1; i >= 0; i--) {
                    modify(i);
                }
            }

            // 调整堆 O(logn)
            public void modify(int k) {
                while (2 * k + 1 < size) {
                    int j = 2 * k + 1;
                    if (j + 1 < size && heap[j + 1] > heap[j]) {
                        j++;
                    }
                    if (heap[k] >= heap[j]) {
                        break;
                    }
                    swap(heap, k, j);
                    k = j;
                }
            }

            public void remove(int index) {
                swap(heap, index, size - 1);
                size--;
                modify(index);
            }

            private void swap(int[] arr, int i, int j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }

            @Override
            public String toString() {
                return Arrays.toString(Arrays.copyOfRange(heap, 0, size));
            }

        }
        int[] arr = new int[7];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = RandomUtil.randomInt(100);
        }
        arr[0] = 10;
        System.out.println(Arrays.toString(arr));
        maxHeap heap = new maxHeap(arr);
        System.out.println(heap);
        heap.remove(3);
        System.out.println(heap);
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

    public static void blockMatrixMultiply(double[][] A, double[][] B, int N, int blockSize)
            throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        double[][] C = new double[N][N];
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

    public static void fun2(int n, int blockSize) throws ExecutionException, InterruptedException {
        double[][] A = new double[n][n], B = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = RandomUtil.randomDouble();
                B[i][j] = RandomUtil.randomDouble();
            }
        }
        long start = System.nanoTime();
        blockMatrixMultiply(A, B, n, blockSize);
        System.out.printf("矩阵大小为%d,分块大小为%d,运算时间:%s\n", n, blockSize, (System.nanoTime() - start) / 1_000_000.0 + "ms");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // int n=16384;
        // double [][] mat=new double[n][n];
        // double [] vec=new double[n];
        // for (int i = 0; i < n; i++) {
        // vec[i]=RandomUtil.randomDouble();
        // for (int j = 0; j < n; j++) {
        // mat[i][j]= RandomUtil.randomDouble();
        // }
        // }
        // long start=System.nanoTime();
        // rowMajorMultiplication(mat,vec);
        // System.out.println((System.nanoTime()-start)/1_000_000.0+"ms");
        // start=System.nanoTime();
        // columnMajorMultiplication(mat,vec);
        // System.out.println((System.nanoTime()-start)/1_000_000.0+"ms");
        fun2(4096, 128);
        fun2(4096, 64);
        fun2(4096, 32);
        fun2(4096, 16);
        fun2(4096, 8);

        fun2(4096, 4);
    }

    public List<String> generateParenthesis(int n) {
        class func {
            static int n;
            static List<String> ans = new ArrayList<>();

            static void dfs(int p, StringBuilder cur, int l, int r) {

                if (p == n * 2) {
                    ans.add(cur.toString());
                    return;
                }

                if (l < n) {
                    cur.append("(");
                    dfs(p + 1, cur, l + 1, r);
                    cur.deleteCharAt(cur.length() - 1);
                }
                if (r < l) {
                    cur.append(")");
                    dfs(p + 1, cur, l, r + 1);
                    cur.deleteCharAt(cur.length() - 1);
                }
            }
        }
        func.n = n;
        func.dfs(0, new StringBuilder(""), 0, 0);
        return func.ans;
    }

    class Pair {
        Integer k;
        Integer v;

        public Pair(Integer _k, Integer _v) {
            k = _k;
            v = _v;
        }
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int[] ans = new int[temperatures.length];
        Deque<Pair> deque = new LinkedList<>();
        deque.push(new Pair(-1, Integer.MAX_VALUE));
        for (int i = ans.length - 1; i >= 0; i--) {
            while (deque.peekFirst().v <= temperatures[i]) {
                deque.pollFirst();
            }
            if (deque.peekFirst().v == Integer.MAX_VALUE)
                ans[i] = 0;
            else {
                ans[i] = deque.peekFirst().k - i;
            }
            deque.push(new Pair(i, temperatures[i]));
        }
        return ans;
    }

    int ptr;

    public String decodeString(String s) {
        LinkedList<String> stk = new LinkedList<String>();
        ptr = 0;

        while (ptr < s.length()) {
            char cur = s.charAt(ptr);
            if (Character.isDigit(cur)) {
                // 获取一个数字并进栈
                String digits = getDigits(s);
                stk.addLast(digits);
            } else if (Character.isLetter(cur) || cur == '[') {
                // 获取一个字母并进栈
                stk.addLast(String.valueOf(s.charAt(ptr++)));
            } else {
                ++ptr;
                LinkedList<String> sub = new LinkedList<String>();
                while (!"[".equals(stk.peekLast())) {
                    sub.addLast(stk.removeLast());
                }
                Collections.reverse(sub);
                // 左括号出栈
                stk.removeLast();
                // 此时栈顶为当前 sub 对应的字符串应该出现的次数
                int repTime = Integer.parseInt(stk.removeLast());
                StringBuffer t = new StringBuffer();
                String o = getString(sub);
                // 构造字符串
                while (repTime-- > 0) {
                    t.append(o);
                }
                // 将构造好的字符串入栈
                stk.addLast(t.toString());
            }
        }

        return getString(stk);
    }

    public String getDigits(String s) {
        StringBuffer ret = new StringBuffer();
        while (Character.isDigit(s.charAt(ptr))) {
            ret.append(s.charAt(ptr++));
        }
        return ret.toString();
    }

    public String getString(LinkedList<String> v) {
        StringBuffer ret = new StringBuffer();
        for (String s : v) {
            ret.append(s);
        }
        return ret.toString();
    }

    public List<List<String>> partitionLoop(String s) {
        class func {
            static List<List<String>> ans = new ArrayList<>();
            static List<String> path = new ArrayList<>();
            static boolean[][] dp;

            static void solution(String s) {
                dp = new boolean[s.length()][s.length()];
                for (int i = 0; i < dp.length; i++) {
                    Arrays.fill(dp[i], false);
                }
                for (int len = 2; len < s.length(); len++) {
                    for (int l = 0; l < s.length() - len; l++) {
                        int r = len + l - 1;
                        dp[l][r] = s.charAt(l) == s.charAt(r) && dp[l + 1][r - 1];
                    }
                }
                dfs(s, 0);
            }

            static void dfs(String s, int i) {
                if (i == s.length()) {
                    ans.add(new ArrayList<>(path));
                    return;
                }
                for (int j = i + 1; j < s.length(); j++) {
                    if (dp[i][j]) {
                        path.add(s.substring(i, j + 1));
                        dfs(s, j + 1);
                        path.remove(path.size() - 1);
                    }
                }
            }
        }
        func.solution(s);
        return func.ans;
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head, fast = head.next, mid = null;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        mid = slow.next;
        slow.next = null;
        head = sortList(head);
        mid = sortList(mid);
        ListNode res = new ListNode(0), cur = res;
        while (head != null && mid != null) {
            if (head.val < mid.val) {
                cur.next = head;
                head = head.next;
            } else {
                cur.next = mid;
                mid = mid.next;
            }
            cur = cur.next;
        }
        cur.next = head == null ? mid : head;
        return head;

    }
}
