package cc.doublez.platform.job;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 所有任务需要在这里添加
 * Created by yz on 2016/7/22
 */
public class JobFactory {
    private static Map<String,String> jobs = new LinkedHashMap<String, String>();
    static {
        jobs.put(TestJob.class.getName(),"测试定时任务");
        jobs.put(TestJob2.class.getName(),"测试定时任务2");
    }
    public static Map<String, String> getRegisteredJob(){
        return jobs;
    }
}
