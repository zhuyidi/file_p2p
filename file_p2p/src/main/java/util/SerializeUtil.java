package util;

import org.apache.log4j.Logger;

import java.io.*;


/**
 *  by yidi on 5/12/19
 */

public class SerializeUtil {
    private static final Logger LOGGER =  Logger.getLogger(SerializeUtil.class);

    // 序列化
    public static byte[] serialize(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            byte[] result = byteArrayOutputStream.toByteArray();
            return result;
        } catch (Exception e) {
            LOGGER.error("对象序列化失败，message：" + e.getMessage());
        } finally {
            try {
                byteArrayOutputStream.close();
                objectOutputStream.close();
            } catch (Exception e) {
                LOGGER.error("字节输出流或对象输出流关闭失败，message：" + e.getMessage());
            }
        }
        return null;
    }

    // 反序列化
    public static Object unSerialize(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object result = objectInputStream.readObject();
            return result;
        } catch (Exception e) {
            LOGGER.error("对象反序列化失败， message：" + e.getMessage());
        } finally {
            try {
                byteArrayInputStream.close();
                objectInputStream.close();
            } catch (Exception e) {
                LOGGER.error("字节输入流或对象输入流关闭失败， message：" + e.getMessage());
            }
        }
        return null;
    }
}
