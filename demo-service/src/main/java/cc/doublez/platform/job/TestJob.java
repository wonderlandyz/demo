package cc.doublez.platform.job;

/**
 * Created by yz on 2016/7/22
 */
public class TestJob implements JobBaseInterface{
    @Override
    public void execute(JobParam jobParam) {
        System.out.println("测试某个特定的任务===="+jobParam.getRow());
    }
}
