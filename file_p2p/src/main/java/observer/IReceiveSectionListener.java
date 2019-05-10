package observer;

/**
 *  by yidi on 5/4/19
 */

public interface IReceiveSectionListener {
    // 接收完一个分片文件
    void onGetOneSection(long sectionLen);
}
