import org.apache.log4j.Logger;

/**
 * by yidi on 3/5/19
 */

public class Log4jTest {
    private static final Logger LOGGER = Logger.getLogger(Log4jTest.class);

    public static void main(String[] args) {
        LOGGER.info("test info");
        LOGGER.debug("test debug");
        LOGGER.error("test error");
        System.out.println("hello");
    }
}
