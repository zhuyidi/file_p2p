package observer;

/**
 *  by yidi on 5/13/19
 */

public interface IJoinFileSpeaker {
    void addJoinFileListener(IJoinFileListner iJoinFileListner);
    void sendStartJoin(String fileName, int sendCount);
}
