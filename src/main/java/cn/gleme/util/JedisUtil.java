package cn.gleme.util;

import com.jeesuite.cache.redis.JedisProviderFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCommands;

public class JedisUtil {

    /**
     * 在多线程环境同步初始化
     */
    public static JedisCommands getInstance() {
        JedisCommands commands = JedisProviderFactory.getJedisCommands(null);
        return commands;
    }

}
