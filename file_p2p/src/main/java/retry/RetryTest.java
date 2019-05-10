package retry;

import spring.ApplicationContextBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * by yidi on 4/19/19
 */

@Component
public class RetryTest {
    public static boolean doSomething() {
        int times = 1;
        System.out.println(1);

        while (times < 3) {
            try {
                if (times  < 2) {
                    System.out.println(1 / 0);
                } else {
                    System.out.println(20 / 1);
                }
                System.out.println("第" + times + "次成功");
                return true;
            } catch (Exception e) {
                times++;
                if (times >= 3) {
                    System.out.println("error");
                    return false;
                }
            }
        }
        return true;
    }

    @Retryable(retryCount = 2)
    public void test() {
        System.out.println(1/1);
    }

    public static void main(String[] args) {
//        System.out.println(doSomething());
        ApplicationContext context = ApplicationContextBean.getContext();
        RetryTest test = new RetryTest();
        test.test();
    }
}
