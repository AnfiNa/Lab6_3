package org.example.client;

import org.example.common.Requests.Request;
import org.example.common.Responses.Response;
import org.example.common.Utils.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPManager {

    private static TCPManager instance;

    public static TCPManager getInstance() throws IOException {
        if (instance == null) {
            instance = new TCPManager();
        }
        return instance;
    }

    private TCPManager() throws IOException{
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", port));
    }

    private final int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 1234;
    private SocketChannel socketChannel;

    private void openSocket() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", port));
    }

    private void closeSocket() throws IOException {
        if (socketChannel != null && socketChannel.isOpen()) {
            socketChannel.close();
        }
    }

    public void tryConnection() throws IOException {
        openSocket();
        closeSocket();
    }


    private void sendRequest(Request request) throws IOException {
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        ObjectOutputStream toServer = new ObjectOutputStream(bais);
        toServer.writeObject(request);

        socketChannel.write(ByteBuffer.wrap(bais.toByteArray()));
    }

    private Response getResponse() throws IOException {
        // Receive response from the main.server
        ByteBuffer fromServer = ByteBuffer.allocate(4096);
        socketChannel.read(fromServer);
        return IOUtils.<Response>fromByteArray(fromServer.array());
    }

    public Response sendAndGetResponse(Request request) throws IOException {
        openSocket();
        sendRequest(request);
        var result = getResponse();
        closeSocket();
        return result;
    }

}
