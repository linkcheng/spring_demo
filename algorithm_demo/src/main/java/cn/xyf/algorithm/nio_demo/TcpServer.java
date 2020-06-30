package cn.xyf.algorithm.nio_demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.Iterator;

public class TcpServer {
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private String ip;
    private int port;
    private int bufferSize = 4096;

    public TcpServer() throws IOException {
        this("0.0.0.0", 8000);
    }

    public TcpServer(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        this.selector = Selector.open();
        this.serverChannel = ServerSocketChannel.open();
    }

    public void startUp() throws IOException {
        ServerSocket socket = serverChannel.socket();
        socket.bind(new InetSocketAddress(ip, port));
        register(serverChannel, SelectionKey.OP_ACCEPT);
        System.out.println("Start server");
    }

    public void close() throws IOException {
        System.out.println("close server");
        this.serverChannel.close();
        this.selector.close();
    }

    private void register(AbstractSelectableChannel channel, int ops) throws IOException {
        channel.configureBlocking(false);
        channel.register(selector, ops);
    }

    private void modify(SelectionKey key, int ops) {
        key.interestOps(ops);
    }


    public void run() throws IOException{
        while (true) {
            int count = selector.select();

            if (count > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        SocketChannel clientChannel = serverChannel.accept();
                        register(clientChannel, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                        modify(key, SelectionKey.OP_READ);
                    }
                }
            }
        }
    }

    private void read(SelectionKey key) {
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer buf = ByteBuffer.allocate(bufferSize);

        int len;
        StringBuilder sb = new StringBuilder();
        try {
            while ((len=channel.read(buf)) > 0) {
                //将缓冲区当前的 limit 设置为 position, position 设置为 0
                buf.flip();
                sb.append(new String(buf.array(),0, len));
                buf.clear();
            }
        } catch (IOException e) {
            key.cancel();
            return;
        }

        String msg = sb.toString();
        System.out.println("from 客户端：" + msg);
        if (msg.equals("q") || msg.equals("Q")) {
            modify(key, 0);
            key.cancel();
            return;
        }
        key.attach(msg);
        modify(key, SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel)key.channel();

        String msg = (String)key.attachment();
        System.out.println("要发送给客户端的数据：" + msg);
        socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
    }

    public static void main(String[] args) throws IOException {
        TcpServer server = new TcpServer();
        server.startUp();
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.close();
        }
    }
}
