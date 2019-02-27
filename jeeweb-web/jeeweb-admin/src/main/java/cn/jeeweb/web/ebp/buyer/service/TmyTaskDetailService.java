package cn.jeeweb.web.ebp.buyer.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface TmyTaskDetailService extends ICommonService<TmyTaskDetail> {

    public List<TmyTaskDetail> selBaseIdMyTaskDetailList(String taskId);
    public List<Map> groupBytaskstatus(String taskId);
    public List<Map> groupBytaskstate(String taskId);
    public List<TmyTaskDetail> selectMytaskList(String mytaskid);
    public Map sumNumAndPrice(Map m);

    public List<Map> listFinanceBuyerReport(Map map);

    @Transactional
    public void upTaskState(String taskState,TmyTaskDetail td);
}
