package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  by yidi on 4/24/19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    // 文件名
    private String fileName;
    // 文件长度
    private Long fileLen;
}
