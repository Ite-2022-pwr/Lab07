package pl.pwr.ite.service;

import lombok.Getter;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class ClientBase {
    protected final DataParser dataParser = DataParser.getInstance();
    private SocketChannel clientSocket;
    private final String host;
    private final int port;
    @Getter
    private boolean connected;

    public ClientBase(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SneakyThrows
    public void connect() {
        clientSocket = SocketChannel.open(new InetSocketAddress(host, port));
        connected = true;
    }

    @SneakyThrows
    private void restartConnection() {
        disconnect();
        connect();
    }

    @SneakyThrows
    public void disconnect() {
        clientSocket.close();
        connected = false;
    }


    @SneakyThrows
    public String send(byte[] data) {
        var dataBuffer = ByteBuffer.wrap(data);
        connect();
        clientSocket.write(dataBuffer);
        dataBuffer = ByteBuffer.allocate(32768);
        clientSocket.read(dataBuffer);
        disconnect();
        var responseString = dataParser.deserialize(dataBuffer);
        dataBuffer.clear();
//        restartConnection();
        return responseString;
    }
}
