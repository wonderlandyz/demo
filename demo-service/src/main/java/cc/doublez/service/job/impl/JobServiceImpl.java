package cc.doublez.service.job.impl;

import cc.doublez.constant.JobConst;
import cc.doublez.dao.JobDao;
import cc.doublez.domain.pojo.job.ScheduleJob;
import cc.doublez.platform.job.QuartzJob;
import cc.doublez.service.job.IJobService;
import cc.doublez.util.DateUtil;
import com.alibaba.fastjson.JSON;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by yz on 2016/7/21
 */
@Service("jobService")
public class JobServiceImpl implements IJobService{
    private static final Logger LOG = LoggerFactory.getLogger(JobServiceImpl.class);
    private static Scheduler scheduler;
    private Set<Integer> jobIds = new HashSet<Integer>();
    @Autowired
    private JobDao jobDao;

    @Override
    public void add(ScheduleJob scheduleJob) {
        jobDao.insert(scheduleJob);
    }

    @Override
    public boolean existTaskByJobNameAndJobGroup(String jobName, String jobGroup) {
        return false;
    }

    @Override
    public void update(ScheduleJob scheduleJob) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public ScheduleJob getById(int id) {
        return jobDao.getById(id);
    }

    @Override
    public List<ScheduleJob> listAll() {
        return this.jobDao.list();
    }

    @Override
    public List<ScheduleJob> listAll(String state) {
        return null;
    }

    @Override
    public void updateState(int id, String state) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("state",state);
        this.jobDao.update(map);
    }

    @Override
    public void scheduleJob(ScheduleJob job) throws Exception {
        if(jobIds.contains(job.getId())){
            LOG.warn("task[" + JSON.toJSONString(job) + "] is running now.");
            return ;
        }
        jobIds.add(job.getId());
        this.updateState(job.getId(), JobConst.JOB_STATE_PENDING);
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                .withIdentity(job.getJobName(), job.getJobGroup()).build();
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(job.getTriggerName(), job.getTriggerGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                .build();
        trigger.getJobDataMap().put("scheduleJob", job);

        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void scheduleJob(int id) throws Exception {
        ScheduleJob job = this.getById(id);
        if(job == null){
            throw new Exception("task[" + id + "] has not found.");
        }
        this.scheduleJob(job);
    }

    @Override
    public void stopJob(int id) throws Exception {
        ScheduleJob job = this.getById(id);
        if(job == null){
            throw new Exception("task[" + id + "] has not found.");
        }
        jobIds.remove(job.getId());
        try {
            scheduler.deleteJob(new JobKey(job.getJobName(), job.getJobGroup()));
        } catch (SchedulerException e) {
            LOG.error("StdSchedulerFactory unscheduleJob has error",e);
        }
        this.updateState(id, JobConst.JOB_STATE_STOP);
    }

    @Override
    public void runForOnce(int id) throws Exception {
        ScheduleJob job = this.getById(id);
        if(job == null){
            throw new Exception("task[" + id + "] has not found.");
        }
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                .withIdentity(job.getJobName(), job.getJobGroup()).build();
        SimpleTrigger trigger =(SimpleTrigger)TriggerBuilder.newTrigger()
                .withIdentity(job.getTriggerName(), job.getTriggerGroup()).startNow().forJob(jobDetail).build();
        trigger.getJobDataMap().put("scheduleJob", job);
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void incTaskTimes(int id, boolean success) {

    }

    @PostConstruct
    public void init(){
        if(scheduler == null){
            try {
                scheduler = StdSchedulerFactory.getDefaultScheduler();
            } catch (SchedulerException e) {
                LOG.error("getDefaultScheduler has error.",e);
            }
        }
    }
}
