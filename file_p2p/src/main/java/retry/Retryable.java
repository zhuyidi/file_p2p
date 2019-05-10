package retry;

import java.lang.annotation.*;

/**
 * by yidi on 4/19/19
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Retryable {
    // 处理的异常
    Class<? extends Exception> exceptionClass() default Exception.class;

    // retry count
    int retryCount() default 3;
}
