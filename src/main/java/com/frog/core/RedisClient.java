package com.frog.core;

import java.io.IOException;

/**
 * classname: RedisClient
 * description: RedisClient
 * date: 2019/4/20 18:53
 *
 * @auther lu
 */
public class RedisClient extends Connection {

    public RedisClient() {
        super();
    }

    public RedisClient(String host, int port) {
        super(host, port);
    }

    public String execute(String cmd, String... args) {
        executeCommand(RedisUtil.encoding(cmd.toLowerCase()), RedisUtil.encoding(args));
        return RedisUtil.decoding(receive());
    }
}
