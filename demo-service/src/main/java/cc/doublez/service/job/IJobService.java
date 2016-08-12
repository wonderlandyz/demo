package cc.doublez.service.job;

import cc.doublez.domain.pojo.job.ScheduleJob;

import java.util.List;

/**
 * Created by yz on 2016/7/21
 */
public interface IJobService {
    public void add(ScheduleJob scheduleJob);

    public boolean existTaskByJobNameAndJobGroup(String jobName, String jobGroup);

    public void update(ScheduleJob scheduleJob);

    public void delete(int id);

    public ScheduleJob getById(int id);

    public List<ScheduleJob> listAll();

    public List<ScheduleJob> listAll(String state);

    public void updateState(int id, String state);

    public void scheduleJob(ScheduleJob scheduleJob) throws Exception;

    public void scheduleJob(int id) throws Exception;

    public void stopJob(int id) throws Exception;

    public void runForOnce(int id) throws Exception;

    public void incTaskTimes(int id, boolean success);
}
