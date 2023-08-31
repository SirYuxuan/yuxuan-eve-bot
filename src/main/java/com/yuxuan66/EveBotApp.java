package com.yuxuan66;

import io.honeybadger.reporter.HoneybadgerUncaughtExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Sir丶雨轩
 * @since 2023/07/04
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class EveBotApp {

    public static void main(String[] args) {
        SpringApplication.run(EveBotApp.class,args);
    }
}
