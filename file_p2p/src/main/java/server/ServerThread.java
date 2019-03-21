package server;

import org.apache.log4j.Logger;

import java.net.Socket;

/**
 * by yidi on 3/7/19
 */

public class ServerThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ServerThread.class);
    private Socket socket;

    public ServerThread(Socket socket) {
        init(socket);
    }

    private void init(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
