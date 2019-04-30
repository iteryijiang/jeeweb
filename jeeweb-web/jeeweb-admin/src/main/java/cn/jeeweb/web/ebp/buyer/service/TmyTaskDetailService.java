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
    public List<TmyTaskDetail> listNoSendGood();
    @Transactional
    public void upTaskState(String taskState,TmyTaskDetail td,String id);


    Page<TmyTaskDetail> listDetail(Queryable queryable, Wrapper<TmyTaskDetail> wrapper);

    Page<TmyTaskDetail> listShopBaseDetail(Queryable queryable, Wrapper<TmyTaskDetail> wrapper);

    public List<TmyTaskDetail> listNoPageDetail(Queryable queryable, Wrapper<TmyTaskDetail> wrapper);

    public int sumMyTask(Map map) throws Exception;

    public int sumTaskBase(Map map) throws Exception;

}
