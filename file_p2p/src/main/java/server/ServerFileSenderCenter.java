package server;

import model.FileTaskInfo;
import org.apache.log4j.Logger;
import resourcetable.ResourceTable;
import resourcetable.allocationpolicy.AllocationPolicy;

import java.io.File;
import java.net.Socket;
import java.util.List;
import java.util.Set;

/**
 *  by yidi on 5/14/19
 */

public class ServerFileSenderCenter {
    private static final Logger LOGGER = Logger.getLogger(ServerFileSenderCenter.class);
    private String filePath;
    private String fileName;
    private long fileLen;

    public ServerFileSenderCenter(String filePath) {
        this.filePath = filePath;
        File file = new File(filePath);
        fileName = file.getName();
        fileLen = file.length();
    }

    public void start() {
        for (String clientId : ServerCenter.clientInfoMap.keySet()) {
            Socket socket = ServerCenter.clientInfoMap.get(clientId);
            Set<String> clients = ResourceTable.getClientsByFileName(fileName);
            List<FileTaskInfo> fileTaskInfos = AllocationPolicy.allocationPolicyForServer(fileName, fileLen, clients);
            if (fileTaskInfos == null || fileTaskInfos.size() == 0) {
                LOGGER.error("服务端下发文件，任务分配失败");
                return;
            }
            // 由服务端来发送文件
            if (fileTaskInfos.size() == 1 && fileTaskInfos.get(0).getClientId().equals("-1")) {
                addFiletask(fileTaskInfos.get(0), clientId, socket);
            }
        }


    }

    public void addFiletask(FileTaskInfo fileTaskInfo, String clientId, Socket socket) {
        new Thread(new ServerFileSenderThread(fileTaskInfo, clientId, socket)).start();
    }
}
