package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *  by yidi on 4/24/19
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SectionInfo {
    // 文件名
    @NonNull
    private String FileName;
    // 分片临时文件名
    private String tempFileName;
    // 起始偏移量
    private Long offSet;
    // 分片长度
    private Long sectionLen;
    // 是否接收过标志
    private Boolean receiveMark;
    // 已接收的大小
    private Long receiveLen;
    // 保存完成标志
    private Boolean saveMark;
    // 已保存的大小
    private Long saveLen;
}
