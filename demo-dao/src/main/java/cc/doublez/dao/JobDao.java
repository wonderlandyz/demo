package cc.doublez.dao;

import cc.doublez.domain.pojo.job.ScheduleJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by yz on 2016/7/22
 */
public interface JobDao {
    void insert(ScheduleJob job);
    List<ScheduleJob> list();
    ScheduleJob getById(@Param("id") int id);
    void update(Map<String, Object> map);
}
