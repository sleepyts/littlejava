import com.google.common.primitives.Doubles;

import java.util.Random;

public class RandomDataGenerator {

    public static void main(String[] args) {
        double target = 50.0;      // 目标数值
        double tolerance = 30.0;   // 公差范围
        double[] randomData = generateRandomData(target, tolerance);

        // 打印生成的数据

    }

    public static double[] generateRandomData(double target, double tolerance) {
        Random random = new Random();
        double[] data = new double[10];

        for (int i = 0; i < data.length; i++) {
            double min = target - tolerance;
            double max = target + tolerance;
            // 生成一个在[min, max]范围内的随机数
            data[i] = min + (max - min) * random.nextDouble();
        }

        return data;
    }
}
