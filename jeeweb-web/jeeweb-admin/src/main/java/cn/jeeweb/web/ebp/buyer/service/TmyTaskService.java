package cn.jeeweb.web.ebp.buyer.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;

import java.util.List;
import java.util.Map;

public interface TmyTaskService extends ICommonService<TmyTask> {

    public List<TmyTask> selBaseIdMyTaskList(String taskId);
    public List<Map> groupBytaskstatus(String taskId);
}
