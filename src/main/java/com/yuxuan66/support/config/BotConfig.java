package com.yuxuan66.support.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * BotConfig类用于读取配置文件中的机器人配置。<br>
 * @author Sir丶雨轩
 * @since 2023/8/31
 */
@Data
@Configuration
public class BotConfig {

    /**
     * 机器人的QQ号
     */
    @Value("${bot.qq}")
    private Long qq;
}
