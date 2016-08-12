package cc.doublez.platform.job;

import cc.doublez.domain.pojo.Row;
import cc.doublez.domain.pojo.job.ScheduleJob;
import cc.doublez.util.StringUtil;
import com.alibaba.fastjson.JSON;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yz on 2016/7/21
 */
public class QuartzJob implements Job{
    private static final Logger LOG = LoggerFactory.getLogger(QuartzJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            ScheduleJob job = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
            LOG.info("任务开始运行，任务名称: [" + job.getJobName() + "]");
            //任务的参数
            JobParam jobParam = new JobParam();
            jobParam.setId(job.getId());
            if(StringUtil.isNotEmpty(job.getContext())){
                jobParam.setRow(JSON.parseObject(job.getContext(), Row.class));
            }
            Object object = Class.forName(job.getJobClass()).newInstance();
            if (object instanceof JobBaseInterface) {
                JobBaseInterface work = (JobBaseInterface) object;
                work.execute(jobParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
