import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Signal {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {

    }
}

enum enumMySignal {
    INSTANCE;
}


class mySignal implements Serializable {

    // 恶汉模式
    private static final mySignal instance2 = new mySignal();
    // 双重检查锁懒汉模式
    private volatile static mySignal instance = null;
    // 静态内部类模式
    private static class mySignalHolder{
        private static final mySignal instance = new mySignal();
    }

    private mySignal() {
    }

    public static synchronized
    mySignal getInstance() {
        if (instance == null) {
            instance = new mySignal();
        }
        return instance;
    }

    public static mySignal getInstance2() {
        return instance2;
    }
    public static mySignal getInstance3() {
        return mySignalHolder.instance;
    }

    @Serial
    public Object readResolve() {
            return getInstance();
    }
}
