package cc.doublez.platform.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，拦截Service
 * Created by yz on 2016/7/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLog {
    String description() default "";
}
