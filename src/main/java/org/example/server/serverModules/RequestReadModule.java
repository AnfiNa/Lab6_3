package org.example.server.serverModules;


import org.example.common.Requests.Request;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class RequestReadModule {
    private final Logger logger;

    public RequestReadModule(Logger logger) {
        this.logger = logger;
    }

    public Request handleRead(SelectionKey key) throws IOException, ClassNotFoundException {

        System.out.println(key.toString());
        SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
        client.configureBlocking(false); // неблокирующий режим

        ByteBuffer fromClientBuffer = ByteBuffer.allocate(4096);
        client.read(fromClientBuffer);
        try {
            ObjectInputStream fromClient = new ObjectInputStream(new ByteArrayInputStream(fromClientBuffer.array()));
            Request request = (Request) fromClient.readObject();
            logger.info("Got request: " + request);
            fromClientBuffer.clear();
            client.register(key.selector(), SelectionKey.OP_WRITE, ByteBuffer.allocate(4096));
            return request;
        } catch (StreamCorruptedException e) {
            logger.warning("Client disconnected read" + e.getMessage());
            key.cancel();
            //TODO может выскочить NullPointer где-то выше
            return null;
        }
    }




}
