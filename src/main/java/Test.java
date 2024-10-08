import java.lang.reflect.*;
import java.util.*;

interface MyService {

    void perform();
}

public class Test {

    public static class StaticFunc {

    }

    public static void main(String[] args) {
        new MyServiceImpl().perform();
    }

    static long optimalNumOfBits(long n, double p) {
        if (p == 0.0) {
            p = Double.MIN_VALUE;
        }

        return (long) ((double) (-n) * Math.log(p) / (Math.log(2.0) * Math.log(2.0)));
    }

    public int[] resultsArray(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        boolean ok = true;
        for (int i = 0; i < k - 1; i++) {
            if (nums[i] >= nums[i + 1]) {
                ok = false;
                deque.push(i);
            }
        }
        if (ok)
            res[0] = nums[k - 1];
        else
            res[0] = -1;
        for (int i = k; i < nums.length; i++) {
            if (nums[i - k + 1] <= nums[i - k])
                deque.pollFirst();
            if (nums[i - 1] >= nums[i]) {
                deque.push(i);
            }
            if (deque.isEmpty())
                res[i - k + 1] = nums[i];
            else
                res[i - k + 1] = -1;
        }
        return res;
    }
}

class reflect {
    public static void main(String[] args)
            throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Solution1 solution1 = new Solution1();
        Class<? extends Solution1> aClass = solution1.getClass();
        Method findKthLargest = aClass.getMethod("findKthLargest", int[].class, int.class);
        Parameter[] parameters = findKthLargest.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println(parameter.getName());
        }

    }
}

class myFactory {

    public static Object createObject(String className, Object... args) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = Class.forName(className);
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }

        return clazz.getConstructor(parameterTypes).newInstance(args);
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException,
            IllegalAccessException, InstantiationException, NoSuchMethodException {
        ListNode listNode = (ListNode) myFactory.createObject("ListNode", 1);
        System.out.println(listNode.val);
    }
}

class ListNode {
    int val;
    ListNode next;

    public ListNode(Integer x) {
        val = x;
        next = null;
    }

    public ListNode(Integer x, ListNode next) {
        this.val = x;
        this.next = next;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class TreeSolution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.push(root);
        res.add(Arrays.asList(root.val));
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();

        }
        return res;

    }
}

class Solution1 {
    public int findKthLargest(int[] nums, int k) {
        int heapSize = nums.length;
        buildMaxHeap(nums, heapSize);
        for (int i = nums.length - 1; i >= nums.length - k + 1; --i) {
            swap(nums, 0, i);
            --heapSize;
            maxHeapify(nums, 0, heapSize);
        }
        return nums[0];
    }

    public void buildMaxHeap(int[] a, int heapSize) {
        for (int i = heapSize / 2; i >= 0; --i) {
            maxHeapify(a, i, heapSize);
        }
    }

    public void maxHeapify(int[] a, int i, int heapSize) {
        int l = i * 2 + 1, r = i * 2 + 2, largest = i;
        if (l < heapSize && a[l] > a[largest]) {
            largest = l;
        }
        if (r < heapSize && a[r] > a[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap(a, i, largest);
            maxHeapify(a, largest, heapSize);
        }
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    int count = 0;
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> temp = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        dfs(nums);
        return res;
    }

    void dfs(int[] nums) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 99) {
                int tempVal = nums[i];
                nums[i] = 99;
                temp.add(tempVal);
                dfs(nums);
                temp.remove(temp.size() - 1);
                nums[i] = tempVal;
            }
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null)
            return null;
        head.next = removeNthFromEnd(head.next, n);
        count++;
        if (count == n)
            return head.next;
        else
            return head;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null)
            return list2;
        else if (list2 == null)
            return list1;
        else if (list1.val < list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }

    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                while (head != slow) {
                    head = head.next;
                    slow = slow.next;
                }
                return head;
            }
        }
        return null;
    }

    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }

        return false;
    }

    public boolean isPalindrome(ListNode head) {
        ListNode slow = head, fast = head;
        boolean flag = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast != null && fast.next == null)
                flag = true;
        }
        if (flag)
            slow = slow.next;
        slow = reverse(slow);
        fast = head;
        while (slow != null) {
            if (slow.val != fast.val)
                return false;
            slow = slow.next;
            fast = fast.next;
        }
        return true;
    }

    private ListNode reverse(ListNode head) {
        ListNode prev = null, curr = head;
        ListNode temp = null;
        while (curr != null) {
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

    public int lengthOfLongestSubstring(String s) {
        int ans = 1;
        Map<Character, Integer> mp = new HashMap<>();
        int p1 = 0, p2 = -1;
        while (p2 < s.length()) {
            p2++;
            if (p2 >= s.length())
                break;
            char ch = s.charAt(p2);

            mp.put(ch, mp.getOrDefault(ch, 0) + 1);
            while (mp.get(ch) > 1) {
                p1++;
                mp.put(s.charAt(p1), mp.get(s.charAt(p1)) - 1);

            }
            ans = Math.max(ans, p2 - p1 + 1);
        }

        return ans;
    }
}

class LRUCache {

    class Node {
        Integer key;
        Integer value;
        Node next;
        Node prev;
        Node (){

        }
        Node(Integer key,Integer val){
            this.value=val;
            this.key=key;
        }
    }
    int capacity;
    int size=0;

    Map<Integer,Node> cache;
    Node head;
    Node tail;

    public LRUCache(int capacity) {
        this.capacity=capacity;
        cache=new HashMap<>();
        head=new Node();
        tail=head;
        head.next=tail;
        head.prev=tail;
    }
    public int get(int key) {
        if (cache.containsKey(key)){
            Node cur = cache.get(key);
            moveToHead(cur);

            return  cur.value;
        }else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)){
            Node cur = cache.get(key);
            cur.value=value;
            moveToHead(cur);
        }else{
            Node newN= new Node(key,value);
            cache.put(key,newN);
            if (size == capacity){
                addToHead(newN);
                deleteTail();
            }else{
                addToHead(newN);
                size++;
            }
        }
    }

    private void addToHead(Node node){
        node.prev=head;
        node.next=head.next;
        head.next.prev=node;
        head.next=node;
    }
    private void moveToHead(Node node){
        node.prev.next=node.next;
        node.next.prev=node.prev;
        node.prev=head;
        node.next=head.next;
        head.next.prev=node;
        head.next=node;
    }

    private void deleteTail(){
        cache.remove(tail.key);
        tail.prev.prev.next=tail.prev.next;
        tail.prev.next.prev=tail.prev.prev;
    }
    public void sout(){
        Node temp = head;
        do{
            System.out.print(temp.key+":"+temp.value+"<->");
            temp=temp.next;
        }
        while ( temp !=head );
    }
    public static void main(String[] args) {
        LRUCache lruCache=new LRUCache(2);
        lruCache.put(1,1);
        lruCache.put(3,3);
        lruCache.put(2,2);
        lruCache.sout();
    }
}

class MyServiceImpl implements MyService {

    @Override
    public void perform() {
        System.out.println("Performing service...");
    }

}

class MyInvoker implements InvocationHandler {

    private final MyService target;

    MyInvoker(MyService target) {
        synchronized (this) {
            this.target = target;
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before invoking service...");
        Object result = method.invoke(target, args);
        System.out.println("After invoking service...");
        return result;
    }
}