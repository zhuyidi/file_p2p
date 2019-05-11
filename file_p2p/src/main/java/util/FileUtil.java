package util;

import model.ConfigInfo;
import server.ClientInfoDTO;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * by yidi on 3/22/19
 */

public class FileUtil {
    private static final Logger LOGGER = Logger.getLogger(FileUtil.class);

    public static Set<String> getClientResource(ConfigInfo configInfo) throws IOException {
        String sendPath = configInfo.getSendPath();
        String targetPath = configInfo.getTargetPath();
        Set<String> result = getFileName(sendPath);
        result.containsAll(getFileName(targetPath));
        return result;
    }

    private static Set<String> getFileName(String path) throws IOException {
        File[] files = new File(path).listFiles();
        if (files == null || files.length == 0) {
            return new HashSet<>();
        }
            Set<String> result = new HashSet<>();
        Path start = FileSystems.getDefault().getPath(path);
        Files.walk(start).filter(childpath -> childpath.toFile().isFile())
                .forEach(e -> result.add(e.getFileName().toString()));
        return result;
    }
}
