package observer;

import java.util.List;

/**
 *  by yidi on 5/4/19
 */

public interface IReceiveSectionSpeaker {
    void setListener(List<IReceiveSectionListener> listeners);
    void addListener(IReceiveSectionListener listener);
}
