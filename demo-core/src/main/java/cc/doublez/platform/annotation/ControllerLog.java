package cc.doublez.platform.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，拦截Controller
 * Created by yz on 2016/7/14
 */

@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerLog {
    String description() default "";
}
