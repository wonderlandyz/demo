package cc.doublez.platform.aop;

import cc.doublez.platform.annotation.ControllerLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切点类
 * Created by yz on 2016/7/14
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    //定义切点
    @Pointcut(value = "@annotation(cc.doublez.platform.annotation.ServiceLog)&& args(param)",argNames = "param")
    public void serviceAspect(String param){

    }

    @After("serviceAspect(param)")
    public void afterService(JoinPoint joinPoint,String param){
        System.out.println("====service后置通知===="+param);
    }
    @Before("serviceAspect(param)")
    public void beforeService(JoinPoint joinPoint,String param){
        System.out.println("====service前置通知====");
    }

    @Pointcut("execution(* cc.doublez..*.*(..)) && @annotation(cc.doublez.platform.annotation.ControllerLog)")
    public void controllerAspect(){
        //方法只是一个标记，方法体不会被执行
    }
    //引用切点
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        try {
            //*========控制台输出=========*//
            System.out.println("====前置通知开始====");
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));
            //*========数据库日志=========*//
           /* Log log = SpringContextHolder.getBean("logxx");
            log.setDescription(getControllerMethodDescription(joinPoint));
            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setType("0");
            log.setRequestIp(ip);
            log.setExceptionCode( null);
            log.setExceptionDetail( null);
            log.setParams( null);
            log.setCreateBy(user);
            log.setCreateDate(DateUtil.getCurrentDate());
            //保存数据库
            logService.add(log);*/
            System.out.println("=====前置通知结束=====");
        }  catch (Exception e) {
            //记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", e.getMessage());
        }
    }
    public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ControllerLog. class).description();
                    break;
                }
            }
        }
        return description;
    }

    @After("controllerAspect()")
    public void after(JoinPoint joinPoint){
        System.out.println("====后置通知====");
    }
    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint joinPoint){
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start+"ms");
        return result;
    }
}
