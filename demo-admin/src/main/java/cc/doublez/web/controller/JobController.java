package cc.doublez.web.controller;

import cc.doublez.constant.JobConst;
import cc.doublez.domain.pojo.job.ScheduleJob;
import cc.doublez.platform.job.JobFactory;
import cc.doublez.service.job.IJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yz on 2015/12/23
 */
@Controller
@RequestMapping(value = "/x3i9ze7w/job")
public class JobController {
    private static final Logger LOG = LoggerFactory.getLogger(JobController.class);
    @Autowired
    private IJobService jobService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView("job/list");
        mv.addObject("results",jobService.listAll());
        return mv;
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView add(){
        ModelAndView mv = new ModelAndView("job/add");
        return mv;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView create(ScheduleJob job){
        ModelAndView mv = new ModelAndView("redirect:/x3i9ze7w/job/list");
        if(this.jobService.existTaskByJobNameAndJobGroup(job.getJobName(), job.getJobGroup())){
            mv.setViewName("/job/add");
            return mv;
        }
        this.jobService.add(job);
        return mv;
    }

    @RequestMapping(value = "/schedule")
    public ModelAndView scheduleJob(@RequestParam(value = "id")int id){
        ModelAndView mv = new ModelAndView("redirect:/x3i9ze7w/job/list");
        try {
            this.jobService.scheduleJob(id);
        } catch (Exception e) {
            LOG.error("task[" + id + "] scheduled has error.",e);
        }
        return mv;
    }

    @RequestMapping(value = "/run")
    public ModelAndView executeOnce(@RequestParam(value = "id")int id){
        ModelAndView mv = new ModelAndView("redirect:/x3i9ze7w/job/list");
        try {
            this.jobService.runForOnce(id);
        } catch (Exception e) {
            LOG.error("task[" + id + "] run for once has error.",e);
        }
        return mv;
    }

    @RequestMapping(value = "/stop")
    public ModelAndView stop(@RequestParam(value = "id")int id){
        ModelAndView mv = new ModelAndView("redirect:/x3i9ze7w/job/list");
        try {
            this.jobService.stopJob(id);
        } catch (Exception e) {
            LOG.error("task[" + id + "] stop has error.",e);
        }
        return mv;
    }

    @ModelAttribute(value = "jobs")
    public Map<String, String> jobClasses(){
        return JobFactory.getRegisteredJob();
    }
    @ModelAttribute(value = "states")
    public Map<String, String> states(){
        Map<String, String> states = new LinkedHashMap<String, String>();
        states.put(JobConst.JOB_STATE_PENDING, "挂起");
        states.put(JobConst.JOB_STATE_STOP, "暂停");
        return states;
    }

    @ModelAttribute(value = "allStates")
    public Map<String, String> allStates(){
        Map<String, String> states = new LinkedHashMap<String, String>();
        states.put(JobConst.JOB_STATE_PENDING, "挂起");
        states.put(JobConst.JOB_STATE_STOP, "暂停");
        states.put(JobConst.JOB_STATE_HOLD, "正在执行中");
        return states;
    }
}

