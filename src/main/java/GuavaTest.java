import com.google.common.base.Predicates;
import com.google.common.base.Strings;

import java.io.Console;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class GuavaTest {

    public static void main(String[] args) {
        Map<String, String> getenv = System.getenv();
        getenv.forEach((k,v) ->{
            System.out.println(k+":"+v);
        });
    }
}
