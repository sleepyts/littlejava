package Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;

import org.aspectj.lang.JoinPoint;

import cn.hutool.db.sql.SqlBuilder.Join;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Test {
    public static void main(String[] args) {

    }

    public int[][] merge(int[][] nums) {
        Arrays.sort(nums, (a, b) -> a[0] - b[0]);
        List<int[]> list = new ArrayList<>();
        int left = nums[0][0], right = nums[0][1];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i][0] <= right && nums[i][1] >= right) {
                right = nums[i][1];
            } else if (nums[i][0] <= right && nums[i][1] <= right) {
                continue;
            } else {
                list.add(new int[] { left, right });
                left = nums[i][0];
                right = nums[i][1];
            }
        }
        list.add(new int[] { left, right });
        return list.toArray(new int[list.size()][]);
    }

    public int longestConsecutive(int[] nums) {
        int ans = 0;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        for (int num : set) {
            if (set.contains(num - 1))
                continue;
            int cur = num;
            int curlen = 1;
            while (set.contains(cur + 1)) {
                curlen++;
                cur++;
            }
            ans = Math.max(ans, curlen);
        }
        return ans;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class MyObject {
    private int id;
    private String name;
    private String description;

    public void hello(String name, int age) {
        System.out.println("hello " + name + ", age: " + age);
    }
}