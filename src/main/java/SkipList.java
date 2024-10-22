import java.util.*;

public class SkipList<E extends Comparable<E>> {
    // 最大索引层数
    public static final int MAX_LEVEL = 16;
    // 添加高度概率
    public static final float UP_LEVEL_CHANCE = 0.5f;
    // 降序跳表
    public static final int DESC = 0;
    // 升序跳表
    public static final int ASC = 1;
    // 存储跳表每一层的头节点
    @SuppressWarnings("unchecked")
    private final Node<E>[] heads = new Node[MAX_LEVEL];
    // 排序策略
    private int ORDER_WAY = ASC;
    // 跳表的最大索引层
    private int maxLevel = 1;
    // 跳表的大小
    private int size = 0;

    SkipList() {
        initHeads();
    }

    // 构造函数
    SkipList(int ORDER_WAY) {
        initHeads();
        if (ORDER_WAY != DESC && ORDER_WAY != ASC)
            throw new IllegalArgumentException();
        this.ORDER_WAY = ORDER_WAY;
    }

    /**
     * 初始化头节点
     * 初始化每一层的头节点 将其链接到上一层的尾节点
     * node15 -> null level16
     * ⬇
     * node14 -> null level15
     * ⬇
     * node13 -> null level14
     * ⬇
     * ......
     */
    private void initHeads() {
        Node<E> up = null;
        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            heads[i] = new Node<>(null, i + 1);
            if (up != null)
                up.bottom = heads[i];
            up = heads[i];
        }
    }

    // 添加元素接口
    public void add(E val) {
        Node<E> newNode = new Node<>(val, randomLevel());
        insert(newNode);
        size++;
    }

    // 生成随机索引高度
    private int randomLevel() {
        int level = 1;
        Random random = new Random();
        while (random.nextFloat(1) < UP_LEVEL_CHANCE && level < MAX_LEVEL)
            level++;
        return level;
    }

    // 插入节点
    private void insert(Node<E> node) {
        int level = node.level;
        this.maxLevel = Math.max(level, this.maxLevel);
        // 从最上层开始找
        int findLevel = this.maxLevel;
        /**
         * 插入栈
         * 每一层都会压入本层中该节点应插入位置的前驱
         */
        Deque<Node<E>> insertStack = new ArrayDeque<>();
        // current 指向比较的节点， prev指向插入的节点
        Node<E> current, prev = heads[findLevel - 1];
        while (findLevel > 0) {
            current = prev.next;
            while (current != null && compare(node.val, current.val)) {
                prev = current;
                current = current.next;
            }
            insertStack.addLast(prev);
            // 从下一层继续找
            prev = prev.bottom;
            findLevel--;
        }

        Node<E> bottom = null;
        // 根据节点的层数弹出插入节点，将其插入
        while (level > 0) {
            Node<E> prevInsert = insertStack.removeLast();
            Node<E> newNode = new Node<>(node.val, level);
            newNode.next = prevInsert.next;
            prevInsert.next = newNode;
            newNode.bottom = bottom;
            bottom = newNode;

            level--;
        }
    }

    public void remove(E val) {
        int findLevel = this.maxLevel;
        Deque<Node<E>> removeStack = new ArrayDeque<>();
        Node<E> current, prev = heads[findLevel - 1];
        while (findLevel > 0) {
            current = prev.next;
            while (current != null) {
                if (compare(val, current.val)) {
                    prev = current;
                    current = current.next;
                } else if (val.equals(current.val)) {
                    removeStack.addLast(prev);
                    break;
                } else
                    break;
            }
            prev = prev.bottom;
            findLevel--;
        }
        while (!removeStack.isEmpty()) {
            Node<E> prevRemove = removeStack.removeLast();
            prevRemove.next = prevRemove.next.next;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(E val) {
        int findLevel = this.maxLevel;
        Node<E> current, prev = heads[findLevel - 1];
        while (findLevel > 0) {
            current = prev.next;
            while (current != null) {
                if (compare(val, current.val)) {
                    prev = current;
                    current = current.next;
                } else if (val.equals(current.val))
                    return true;
                else
                    break;
            }
            prev = prev.bottom;
            findLevel--;
        }
        return false;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        int level = MAX_LEVEL;
        StringBuilder s = new StringBuilder();
        while (level > 0) {
            s.append("level").append(level).append("    ");
            Node<E> head = heads[level - 1];
            while (head != null) {
                s.append(head.val).append("-> ");
                head = head.next;
            }
            s.append("\n");
            level--;
        }
        return s.toString();
    }

    private boolean compare(E e1, E e2) {
        if (ORDER_WAY == ASC) {
            return e1.compareTo(e2) > 0;
        } else {
            return e1.compareTo(e2) < 0;
        }
    }

    public static class Node<E> {
        // 单个节点的值
        E val;
        // 当前层的下一个节点
        Node<E> next;
        // 索引层的下一层节点
        Node<E> bottom;
        // 索引层的高度
        int level;

        Node(E val, int level) {
            this.val = val;
            this.next = null;
            this.bottom = null;
            this.level = level;
        }
    }
}