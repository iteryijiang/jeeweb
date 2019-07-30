package cn.jeeweb.web.ebp.buyer.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
    public List<TmyTaskDetail> listNoSendGood(Map map);
    @Transactional
    public void upTaskState(String taskState,TmyTaskDetail td,String id);
    /***
     * 更改任务单状态
     * 退单操作
     * 
     * @param id
     * @param lastRepair
     */
    void updateTaskStatusForChargeBack(String id,String lastRepair);

    /**
     * 更改买手任务单用于确认收货操作
     *
     * @param id
     * @param lastRepair
     */
    void updateTaskStatusForAckReceive(String id,String lastRepair);


    Page<TmyTaskDetail> listDetail(Queryable queryable, Wrapper<TmyTaskDetail> wrapper);

    Page<TmyTaskDetail> listDetailGroup(Queryable queryable, Wrapper<TmyTaskDetail> wrapper);

    Page<TmyTaskDetail> listShopBaseDetail(Queryable queryable, Wrapper<TmyTaskDetail> wrapper);

    /**
     * 获取需要确认完成的任务单
     *
     * @param queryable
     * @param wrapper
     * @return
     */
    Page<TmyTaskDetail> listNeedFinishTask(Queryable queryable, Wrapper<TmyTaskDetail> wrapper);

    public List<TmyTaskDetail> listNoPageDetail(Queryable queryable, Wrapper<TmyTaskDetail> wrapper);

    public List<TmyTaskDetail> listNoPageDetailGroup(Queryable queryable, Wrapper<TmyTaskDetail> wrapper);

    public List<Map> listTfinanceTaskDoubleReport(Map map);

    public int sumMyTask(Map map) throws Exception;

    public int sumTaskBase(Map map) throws Exception;

    /**
     * 更改任务单状态
     *
     * @param taskId
     * @param status
     * @param lastRepair
     */
    @Transactional
    void upTaskErrorStatus(String taskId, int status, String lastRepair);

}
