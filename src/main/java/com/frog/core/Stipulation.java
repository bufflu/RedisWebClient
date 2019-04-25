package com.frog.core;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.imageio.IIOException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * classname: Stipulation
 * description: Stipulation
 * date: 2019/4/20 0:19
 *
 * @auther lu
 */
public class Stipulation {

    public static final String ENCODING = "utf-8";

    public static final byte COMMAND_HEAD   = '*';
    public static final byte PARAMETER_HEAD = '$';
    public static final byte NOTIFICATION   = '+';
    public static final byte ERROR          = '-';
    public static final byte INTEGER        = ':';
    public static final byte STRING         = '$';
    public static final byte MULTITERM      = '*';

    public static final byte[] buffer = new byte[512];

    public static void command(OutputStream os, final byte[] cmd, final byte[]... args) {
        if (cmd.length == 0) {
            throw new RedisException("None Execute Command");
        }
        try {
            os.write(COMMAND_HEAD);
            os.write(RedisUtil.int2charBytes(args.length + 1));
            os.write(RedisUtil.ctr());
            os.write(PARAMETER_HEAD);
            os.write(RedisUtil.int2charBytes(cmd.length));
            os.write(RedisUtil.ctr());
            os.write(cmd);
            os.write(RedisUtil.ctr());

            for (byte[] arg : args) {
                os.write(PARAMETER_HEAD);
                os.write(RedisUtil.int2charBytes(arg.length));
                os.write(RedisUtil.ctr());
                os.write(arg);
                os.write(RedisUtil.ctr());
            }
        } catch (IOException e) {
            throw new RedisException(e);
        }
    }

    public static byte[] receive(InputStream is) {
        byte[] input = new byte[512];
        int count = 0; // the elements number of iuputArray
        int read = 0;
        try {
            while ((read = is.read(buffer)) > -1) {
                // double dilatation
                if (input.length < (read + count)) {
                    byte[] inputDil = new byte[input.length << 1];
                    System.arraycopy(input, 0, inputDil, 0, count);
                    input = inputDil;
                }
                System.arraycopy(buffer, 0, input, count, read);
                count += read;
            }
            byte[] result = new byte[count];
            System.arraycopy(input, 0, result, 0, count);
            return result;
        } catch (IOException e) {
            throw new RedisException(e);
        }
    }
}
