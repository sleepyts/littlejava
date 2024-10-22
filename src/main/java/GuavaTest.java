import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static javax.xml.crypto.dsig.SignatureMethod.HMAC_SHA256;
import static javax.xml.crypto.dsig.SignatureMethod.SHA256_RSA_MGF1;

@Slf4j
public class GuavaTest {

    public static void main(String[] args) {
        String signature="sha256=757107ea0eb2509fc211221cce984b8a37570b6d7586c22c46f4379c8b043e17";
        String payload="Hello, World";
        System.out.println(payload);
        System.out.println(verifySignature(signature,payload));
    }

    private static boolean verifySignature(String signature, String payload) {
        // 提取签名值（去掉 sha256=）
        signature = signature.replace("sha256=", "");
        try {
        // 使用 GitHub 提供的 secret 创建 HMAC 密钥
        SecretKeySpec keySpec = new SecretKeySpec("It's a Secret to Everybody".getBytes(StandardCharsets.UTF_8), "HmacSHA256");

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
            byte[] hashBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));

            String calculatedSignature = bytesToHex(hashBytes);
            log.info(calculatedSignature);
            // 将计算出的签名与 GitHub 发送的签名进行比较
            return signature.equals(calculatedSignature);
        } catch (Exception e) {
            log.info("Exception when encoding secret :[{}]", e.getMessage());
            return false;
        }
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0'); // 在前面补零
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
