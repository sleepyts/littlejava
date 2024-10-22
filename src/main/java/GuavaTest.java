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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static javax.xml.crypto.dsig.SignatureMethod.HMAC_SHA256;
import static javax.xml.crypto.dsig.SignatureMethod.SHA256_RSA_MGF1;

@Slf4j
public class GuavaTest {

    public static void main(String[] args) {
        Map<String, String> mp = new HashMap<>();
        mp.put(HMAC_SHA256, SHA256_RSA_MGF1);
        // HashMap put 流程:
        /**
         * 获取key的hashcode 对其进行hash操作并对底层数组长度取余得到哈希槽
         * 判断当前哈希槽是否含有元素 不含有则直接加入
         * 含有则先遍历这个哈希槽的链表（红黑树）来用equals方法查询是否有元素与其相等 相等则直接替换 没有相等的则产生哈希冲突
         * 采用尾插法将当前元素插入到链表中 如果链表长度大于8且数组长度小于64则变成红黑树
         * 如果插入后含有元素的哈希槽的数量大于总体数组的数量*负载因子（通常为0.75）则会开始扩容
         * 扩容时会先将数组的大小扩充为原来的2倍 然后将每个Key的hashcode与当前数组长度相与获得新的哈希槽位置 然后开始转移元素
         */
    }

    private static boolean verifySignature(String signature, String payload) {
        // 提取签名值（去掉 sha256=）
        signature = signature.replace("sha256=", "");
        try {
            // 使用 GitHub 提供的 secret 创建 HMAC 密钥
            SecretKeySpec keySpec = new SecretKeySpec("It's a Secret to Everybody".getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256");

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
