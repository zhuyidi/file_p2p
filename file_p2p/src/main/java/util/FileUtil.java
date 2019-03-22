package util;

import client.ClientInfoDTO;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * by yidi on 3/22/19
 */

public class FileUtil {
    private static final Logger LOGGER = Logger.getLogger(FileUtil.class);

    public static Set<String> getClientResource(ClientInfoDTO clientInfoDTO) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("file-config");
        String sendPath = resourceBundle.getString("sendPath");
        String targetPath = resourceBundle.getString("targetPath");
        Set<String> result = null;
        try {
            result = getFileName(sendPath);
            result.containsAll(getFileName(targetPath));
        } catch (IOException e) {
            LOGGER.error("client: " + clientInfoDTO.getHost() + " | " + clientInfoDTO.getPort() + " 获取资源信息失败");
        }
        return result;
    }

    public static Set<String> getFileName(String path) throws IOException {
        File[] files = new File(path).listFiles();
        if (files == null || files.length == 0) {
            return new HashSet<>();
        }
        Set<String> result = new HashSet<>();
        Path start = FileSystems.getDefault().getPath(path);
        Files.walk(start).filter(childpath -> childpath.toFile().isFile())
                .forEach(e -> result.add(e.toString()));
        return result;
    }
}
