package cc.doublez.platform.job;

import cc.doublez.constant.JobConst;
import cc.doublez.domain.pojo.job.ScheduleJob;
import cc.doublez.platform.listener.Infrastructure;
import cc.doublez.service.job.IJobService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目启动的时候需要加载
 * Created by yz on 2016/7/21
 */
public class JobInit {
    private static final Logger LOG = LoggerFactory.getLogger(JobInit.class);
    private static IJobService jobService;
    public static void initialJob(){
        if(jobService == null){
            jobService = (IJobService) Infrastructure.getBean("jobService");
        }
        List<ScheduleJob> allTaskOnRunning = new ArrayList<ScheduleJob>();
        List<ScheduleJob> tasksOnPending = jobService.listAll(JobConst.JOB_STATE_PENDING);
        List<ScheduleJob> tasksOnHold = jobService.listAll(JobConst.JOB_STATE_HOLD);
        if(CollectionUtils.isNotEmpty(tasksOnPending)){
            allTaskOnRunning.addAll(tasksOnPending);
        }
        if(CollectionUtils.isNotEmpty(tasksOnHold)){
            allTaskOnRunning.addAll(tasksOnHold);
        }
        if(allTaskOnRunning == null || allTaskOnRunning.isEmpty()){
            return ;
        }
        for(ScheduleJob job : allTaskOnRunning){
            try {
                jobService.scheduleJob(job);
            } catch (Exception e) {
                LOG.error("schedule task[" + JSON.toJSONString(job) + "] has error.",e);
            }
        }
    }
}
