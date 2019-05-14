package model;

import lombok.*;

/**
 * by yidi on 5/13/19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class FileTaskInfo {
    @NonNull
    String clientId;
    @NonNull
    String targetFilename;
    long offSet;
    long sectionLen;
    int sectionNum;
}
