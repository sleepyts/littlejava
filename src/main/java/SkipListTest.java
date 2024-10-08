 import java.util.*;

public class SkipListTest {

    public static void main(String[] args) throws InterruptedException {
        int loop = 200000;
        Random random = new Random();
        SkipList<Integer> skipList = new SkipList<>();
        List<Integer> list = new ArrayList<>();

        System.out.println("########## 添加测试 ##########");
        long listStartTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            int value = random.nextInt(1000000);
            int addIndex=0;
            for(int j = 0; j < list.size(); j++){
                if(list.get(j) > value){
                    addIndex = j;
                    break;
                }
            }
            list.add(addIndex, value);
            if (i % (loop / 100) == 0) {
                System.out.printf("List添加进度：%d%%\r", i / (loop / 100));
            }
        }
        System.out.println("List添加进度：100%");
        System.out.println("List添加耗时：" + (System.currentTimeMillis() - listStartTime) + "ms");

        // Adding elements with progress display

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            skipList.add(list.get(i));
            if (i % (loop / 100) == 0) {
                System.out.printf("SkipList添加进度：%d%%\r", i / (loop / 100));
            }
        }
        System.out.println("SkipList添加进度：100%");
        System.out.println("SkipList添加耗时：" + (System.currentTimeMillis() - startTime) + "ms");

        // Contains test with progress display
        System.out.println("########## 包含测试 ##########");
        Thread containsThread = new Thread(() -> {
            long containsStartTime = System.currentTimeMillis();
            for (int i = 0; i < loop; i++) {
                skipList.contains(random.nextInt());
                if (i % (loop / 100) == 0) {
                    System.out.printf("包含进度：%d%%\r", i / (loop / 100));
                }
            }
            System.out.println("包含进度：100%");
            System.out.println("包含耗时：" + (System.currentTimeMillis() - containsStartTime) + "ms");
        });
        containsThread.start();

        Thread listContainsThread = new Thread(() -> {
            long listContainsStartTime = System.currentTimeMillis();
            for (int i = 0; i < loop; i++) {
                list.contains(random.nextInt());
                if (i % (loop / 100) == 0) {
                    System.out.printf("List包含进度：%d%%\r", i / (loop / 100));
                }
            }
            System.out.println("List包含进度：100%");
            System.out.println("List包含耗时：" + (System.currentTimeMillis() - listContainsStartTime) + "ms");
        });
        listContainsThread.start();

        containsThread.join();
        listContainsThread.join();

        // False negative test
        System.out.println("########## 误判率测试 ##########");
        int error = 0;
        for (Integer integer : list) if (!skipList.contains(integer)) error++;
        System.out.println("误判率：" + (float) error / loop + "%");

        // Remove test with progress display
        System.out.println("########## 删除测试 ##########");
        int error1 = 0;
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < loop/2; i++) set.add(list.get(i));
        int removeCount = 0;
        for(Integer integer: set) {
            skipList.remove(integer);
            list.remove(integer);
            removeCount++;
            if (removeCount % (set.size() / 100) == 0) {
                System.out.printf("删除进度：%d%%\r", removeCount / (set.size() / 100));
            }
        }
        System.out.println("删除进度：100%");

        for(Integer integer : list) if(!skipList.contains(integer)) error1++;
        System.out.println("误删率：" + (float) error1 /(loop/2)+"%");

    }
}
