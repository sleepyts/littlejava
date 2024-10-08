import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class PoolTest {
    public static void main(String[] args) throws InterruptedException {
        enum myStrategies {
            STRATEGIE1{
                @Override
                void run() {
                    System.out.println("STRATEGIE1");
                }
            },
            STRATEGIE2{
                @Override
                void run() {
                    System.out.println("STRATEGIE2");
                }
            };
            void run(){

            }
        }
        System.out.println(myStrategies.STRATEGIE1);
    }
}

class temp{
    private static volatile Object instance = null;

    public Object getInstance(){
        if (instance == null){
            synchronized (temp.class){
                if (instance == null) {
                    instance = new Object();
                }
            }
        }

        return instance;
    }


}
