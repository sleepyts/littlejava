import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class VolatileAtomicityDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,// 核心线程数
                2,// 最大线程数 = 核心线程数 + 非核心线程数(救急线程)
                0L,// 救急线程存活时间
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(2),// 等待队列，当核心线程数满时，新任务会进入等待队列，等待队列满了之后，新任务成为救急线程
                (r) -> new Thread(r, "VolatileAtomicityDemo-Thread-" + System.currentTimeMillis()), // 线程工厂，用于创建线程
                new ThreadPoolExecutor.AbortPolicy()); // 拒绝策略,当各种线程都在繁忙时，新任务会根据拒绝策略被拒绝;
        executor.execute(() -> {
            System.out.println("Task1 start");
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Task1 end");
        });
    }
}
