package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.mapper.TmyTaskDetailMapper;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import cn.jeeweb.web.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Service("TmyTaskDetailService")
public class TmyTaskDetailServiceImpl extends CommonServiceImpl<TmyTaskDetailMapper, TmyTaskDetail> implements TmyTaskDetailService {

    @Autowired
    private TmyTaskService tmyTaskService;
    @Autowired
    private TtaskBaseService ttaskBaseService;
    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    public List<TmyTaskDetail> selBaseIdMyTaskDetailList(String taskId){
        return baseMapper.selBaseIdMyTaskDetailList(taskId);
    }
    public List<Map> groupBytaskstatus(String taskId){
        return baseMapper.groupBytaskstatus(taskId);
    }

    public List<Map> groupBytaskstate(String taskId){
        return baseMapper.groupBytaskstate(taskId);
    }

    public Map  sumNumAndPrice(Map m){
        return baseMapper.sumNumAndPrice(m);
    }

    public List<TmyTaskDetail> selectMytaskList(String mytaskid){
        return baseMapper.selectMytaskList(mytaskid);
    }
    public List<Map> listFinanceBuyerReport(Map map){
        return baseMapper.listFinanceBuyerReport(map);
    }

    @Transactional
    public void upTaskState(String taskState,TmyTaskDetail td){
        if(UserUtils.getPrincipal().getId().equals(td.getCreateBy().getId())||"3".equals(taskState)){
            TmyTask tt = tmyTaskService.selectById(td.getMytaskid());
            if("2".equals(taskState)&&"1".equals(td.getTaskstate())){
                td.setOrderdate(new Date());
                //确定下单，任务单下单金额增加
                if(tt.getOrderprice()==null){
                    tt.setOrderprice(td.getPays());
                }else {
                    tt.setOrderprice(tt.getOrderprice().add(td.getPays()));
                }
            }else if("3".equals(taskState)&&"2".equals(td.getTaskstate())) {
                td.setDeliverydate(new Date());
                //确定下单，任务单发货金额增加
                if(tt.getDeliveryprice()==null){
                    tt.setDeliveryprice(td.getPays());
                }else {
                    tt.setDeliveryprice(tt.getDeliveryprice().add(td.getPays()));
                }
            }else if("4".equals(taskState)&&"3".equals(td.getTaskstate())) {
                td.setConfirmdate(new Date());
                td.setTaskstatus("1");//修改订单为已完成状态

                //计算我的任务单是否完成
                List<TmyTaskDetail> ttList = tmyTaskDetailService.selectMytaskList(td.getMytaskid());
                boolean ttbool = true;
                for (TmyTaskDetail ttd:ttList) {
                    if(!td.getId().equals(ttd.getId())&&!"1".equals(ttd.getTaskstatus())){
                        ttbool = false;
                    }
                }
                if(ttbool){
                    tt.setState("1");
                }

                //计算商家任务单是否完成
                List<TmyTaskDetail> tsList = tmyTaskDetailService.selBaseIdMyTaskDetailList(td.getTaskid());
                boolean tsbool = true;
                for (TmyTaskDetail ttd:tsList) {
                    if(!td.getId().equals(ttd.getId())&&!"1".equals(ttd.getTaskstatus())){
                        tsbool = false;
                    }
                }
                TtaskBase tb = ttaskBaseService.selectById(td.getTaskid());
                if(tsbool&&!"2".equals(tb.getStatus())){
                    tb.setStatus("1");
                    ttaskBaseService.insertOrUpdate(tb);
                }
            }
            td.setTaskstate(taskState);
            tmyTaskService.insertOrUpdate(tt);
            tmyTaskDetailService.insertOrUpdate(td);
        }
    }
}
