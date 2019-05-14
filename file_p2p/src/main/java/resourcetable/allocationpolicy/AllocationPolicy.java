package resourcetable.allocationpolicy;

import model.ConfigInfo;
import model.FileTaskInfo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * by yidi on 3/19/19
 */

public class AllocationPolicy {
    private static final Logger LOGGER = Logger.getLogger(AllocationPolicy.class);
    private static final long SECTION_SIZE = Long.parseLong(ResourceBundle.getBundle("file-config").getString("sectionSize"));

    // 当客户端请求文件的时候，选出发送端
    public static List<FileTaskInfo> allocationPolicy(String filename, long fileLen, Set<String> clients) {
        List<FileTaskInfo> result = new ArrayList<>();
        int sectionNum = 1;
        long offSet = 0L;

        if (clients == null || clients.size() == 0) {
            LOGGER.error("任务分配失败，没有备选的发送端");
            // todo 后面这里要进行处理，如果没有备选的发送端之后，要给请求端提示：资源找不到了哦！
            return null;
        }
        int sectionCount = (int) (fileLen % SECTION_SIZE == 0
                ? fileLen/SECTION_SIZE : fileLen/SECTION_SIZE+1);
        int clientCount = clients.size();

        // 如果该文件大小 <= 分片大小，或者备选客户端中只有一个端可选，那么就只让一个端去执行发送任务
        if (clients.size() == 1 || fileLen <= SECTION_SIZE) {
            FileTaskInfo fileTaskInfo = new FileTaskInfo(clients.iterator().next(), filename, offSet, fileLen, sectionNum);
            result.add(fileTaskInfo);
            return result;
        }
        // 当备选端 > 1
        long sectionSize = SECTION_SIZE;
        long overLen = fileLen;
        if (clientCount < sectionCount) {
            sectionSize = fileLen % clientCount == 0 ? fileLen/clientCount : fileLen/clientCount+1;
            sectionCount = clientCount;
        }
        for (String client : clients) {
            if (sectionCount-- <= 0) {
                break;
            }
            FileTaskInfo fileTaskInfo = new FileTaskInfo(client, filename, (sectionNum-1)*sectionSize
                    , overLen >= sectionSize ? sectionSize : overLen, sectionNum++);
            overLen -= sectionSize;
            result.add(fileTaskInfo);
        }
        return result;
    }
}
