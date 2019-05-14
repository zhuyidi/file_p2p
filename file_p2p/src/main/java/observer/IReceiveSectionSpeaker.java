package observer;

/**
 *  by yidi on 5/4/19
 */

public interface IReceiveSectionSpeaker {
    void addReceiveSectionListener(IReceiveSectionListener listener);
    void sendGetOneSection(String fileName);
}
