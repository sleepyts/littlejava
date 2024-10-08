
package com.test;

import cn.hutool.json.JSONUtil;
import com.test.Domain.MyObject;
import com.test.Stream.Producer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.Serializable;
import java.time.LocalDateTime;

@SpringBootApplication
public class LittleJavaApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(LittleJavaApplication.class, args);
        KafkaTemplate<String,String> bean = run.getBean(KafkaTemplate.class);
        for (int i = 0; i < 10000; i++) {
            bean.send("test.topic", JSONUtil.toJsonPrettyStr(new MyObject(9,"tom",LocalDateTime.now(),LocalDateTime.now())));
        }

    }
}

