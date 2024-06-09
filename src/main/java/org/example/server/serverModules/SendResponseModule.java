package org.example.server.serverModules;

import org.example.common.Responses.Response;
import org.example.common.Utils.IOUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class SendResponseModule {

    public void handleWrite(SelectionKey key, Response response) throws IOException {
        System.out.println(key.toString());
        SocketChannel client = (SocketChannel) key.channel(); // получаем канал для работы
        client.configureBlocking(false); // неблокирующий режим
        ByteBuffer buffer = ByteBuffer.wrap(IOUtils.toByteArray(response));
        client.write(buffer);
    }



}
