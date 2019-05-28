package server;

import model.FileTaskInfo;
import org.apache.log4j.Logger;

import java.net.Socket;

/**
 *  by yidi on 5/14/19
 */

public class ServerFileSenderThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ServerFileSenderThread.class);

    public ServerFileSenderThread(FileTaskInfo fileTaskInfo, String client, Socket socket) {

    }

    @Override
    public void run() {

    }
}
