package org.example.server.serverModules;



import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;


public class AcceptConnectionModule {

    private final Logger logger;

    public AcceptConnectionModule(Logger logger) {
        this.logger = logger;
    }

    public void handleAccept(SelectionKey key) throws IOException {

        System.out.println(key.toString());
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel(); // используется для доступа к серверному каналу
        SocketChannel client = serverChannel.accept(); // позволяет вашему серверу принять новое входящее соединение и дает вам возможность взаимодействовать с клиентом, используя этот SocketChannel
        logger.info("Connection accepted from " + client);
        client.configureBlocking(false); // неблокирующий режим
        client.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(4096));
    }
}
