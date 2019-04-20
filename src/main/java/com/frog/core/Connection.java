package com.frog.core;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * classname: Connection
 * description: connect to redis server
 * date: 2019/4/19 20:59
 *
 * @auther lu
 */
public class Connection implements Closeable {

    private String host;
    private int port;
    private int timeOut = 20000;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public Connection() {
        host = "localhost";
        port = 6379;
    }

    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void connect() {
        if (socket != null && socket.isClosed() && !socket.isConnected() && !socket.isBound()) {
            socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(host, port));
                socket.setSoTimeout(timeOut);
                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                throw new RedisException(e);
            }
        }
    }

    public void executeCommand (final byte[] cmd, final byte[]... args) {
        connect();
        Stipulation.command(outputStream, cmd, args);
    }

    public byte[] receive() {
        flush();
        return Stipulation.receive(inputStream);
    }

    private void flush() {
        try {
            outputStream.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            throw new RedisException(e);
        }
    }

    @Override
    public void close() {
        if (socket != null && !socket.isClosed()) {
            try {
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                throw new RedisException(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RedisException(e);
                }
            }
        }
    }
}
