package retry;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 *  by yidi on 4/19/19
 */

@Aspect
@Component
public class RetryAspect {
    private static final Logger LOGGER = Logger.getLogger(RetryAspect.class);
    @Pointcut("@annotation(retry.Retryable)")
    public void retry() {}

    @Around("retry()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Retryable retryable = method.getAnnotation(Retryable.class);

        System.out.println("aspect");
        int retryCount = retryable.retryCount();
        if (retryCount <= 1) {
            return point.proceed();
        }

        int times = 1;
        final Class<? extends Exception> exceptionClass = retryable.exceptionClass();
        while (times < retryCount) {
            try {
                return point.proceed();
            } catch (Exception e) {
                times++;
                if (times >= retryCount) {
                    LOGGER.error(method.getName() + "retry失败！");
                    return null;
                }
            }
        }
        return null;
    }
}
