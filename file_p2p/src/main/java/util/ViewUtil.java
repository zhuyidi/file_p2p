package util;

import java.awt.*;

/**
 *  by yidi on 5/7/19
 */

public class ViewUtil {
    public static final int FRAME_WIDTH = 350;
    public static final int FRAME_HEIGHT = 150;

    public static int[] getWidAndHei() {
        int[] result = new int[2];
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int width = (screenWidth - FRAME_WIDTH) / 2;        // x 轴位移
        int height = (screenHeight - FRAME_HEIGHT) / 2; // y 轴位移

        result[0] = width;
        result[1] = height;
        return result;
    }
}
