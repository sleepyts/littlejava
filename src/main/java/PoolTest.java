public class PoolTest {
    public static void main(String[] args) throws InterruptedException {
        enum myStrategies {
            STRATEGIE1 {
                @Override
                void run() {
                    System.out.println("STRATEGIE1");
                }
            },
            STRATEGIE2 {
                @Override
                void run() {
                    System.out.println("STRATEGIE2");
                }
            };

            abstract void run();
        }
        myStrategies.STRATEGIE1.run();

    }
}

class temp {
    private static volatile Object instance = null;

    public Object getInstance() {
        if (instance == null) {
            synchronized (temp.class) {
                if (instance == null) {
                    instance = new Object();
                }
            }
        }

        return instance;
    }

}
