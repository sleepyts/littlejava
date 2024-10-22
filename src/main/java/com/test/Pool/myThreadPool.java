package com.test.Pool;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
@RequiredArgsConstructor
public class myThreadPool {

    private ThreadPoolExecutor executor;
}
