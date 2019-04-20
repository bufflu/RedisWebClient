package com.frog.core;

/**
 * classname: RedisException
 * description: exception in redis client
 * date: 2019/4/19 21:45
 *
 * @auther lu
 */
public class RedisException extends RuntimeException {

    public RedisException(String message) {
        super(message);
    }

    public RedisException(Throwable cause) {
        super(cause);
    }
}
