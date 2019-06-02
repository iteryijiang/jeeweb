package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.mapper.TmyTaskDetailMapper;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import cn.jeeweb.web.utils.UserUtils;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Transactional
@Service("TmyTaskDetailService")
public class TmyTaskDetailServiceImpl extends CommonServiceImpl<TmyTaskDetailMapper, TmyTaskDetail> implements TmyTaskDetailService {

    @Autowired
    private TmyTaskService tmyTaskService;
    @Autowired
    private TtaskBaseService ttaskBaseService;
    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TshopInfoService tshopInfoService;

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

    public  List<TmyTaskDetail> listNoSendGood(Map m){
        return baseMapper.listNoSendGood(m);
    }

    public List<Map> listFinanceBuyerReport(Map map){
        return baseMapper.listFinanceBuyerReport(map);
    }

    public List<Map> listTfinanceTaskDoubleReport(Map map){
        return baseMapper.listTfinanceTaskDoubleReport(map);
    }

    @Transactional
    public void upTaskState(String taskState,TmyTaskDetail td,String id){
        if(td==null){
            td = selectById(id);
        }
        if ("3".equals(taskState) || "4".equals(taskState) || UserUtils.getPrincipal().getId().equals(td.getCreateBy().getId())) {
            TmyTask tt = tmyTaskService.selectById(td.getMytaskid());
            if ("2".equals(taskState) && "1".equals(td.getTaskstate())) {
                td.setOrderdate(new Date());
                //确定下单，任务单下单金额增加
                if (tt.getOrderprice() == null) {
                    tt.setOrderprice(td.getPays());
                } else {
                    tt.setOrderprice(tt.getOrderprice().add(td.getPays()));
                    if (tt.getTotalprice().compareTo(tt.getOrderprice()) < 0) {
                        tt.setOrderprice(tt.getTotalprice());
                    }
                }
            } else if ("3".equals(taskState) && "2".equals(td.getTaskstate())) {
                td.setDeliverydate(new Date());
                //确定下单，任务单发货金额增加
                if (tt.getDeliveryprice() == null) {
                    tt.setDeliveryprice(td.getPays());
                } else {
                    tt.setDeliveryprice(tt.getDeliveryprice().add(td.getPays()));
                    if (tt.getTotalprice().compareTo(tt.getDeliveryprice()) < 0) {
                        tt.setDeliveryprice(tt.getTotalprice());
                    }
                }
                // job 任务，自动发货
                if (UserUtils.getPrincipal() == null) {
                    td.setRemarks("平台自动发货!");
                }
            } else if ("4".equals(taskState) && "3".equals(td.getTaskstate())) {
                td.setConfirmdate(new Date());
                td.setTaskstatus("1");//修改订单为已完成状态

                //计算我的任务单是否完成
                List<TmyTaskDetail> ttList = tmyTaskDetailService.selectMytaskList(td.getMytaskid());
                boolean ttbool = true;
                for (TmyTaskDetail ttd : ttList) {
                    if (!td.getId().equals(ttd.getId()) && !"1".equals(ttd.getTaskstatus())) {
                        ttbool = false;
                    }
                }
                if (ttbool) {
                    tt.setState("1");
                }

                //计算商家任务单是否完成
                List<TmyTaskDetail> tsList = tmyTaskDetailService.selBaseIdMyTaskDetailList(td.getTaskid());
                boolean tsbool = true;
                for (TmyTaskDetail ttd : tsList) {
                    if (!td.getId().equals(ttd.getId()) && !"1".equals(ttd.getTaskstatus())) {
                        tsbool = false;
                    }
                }
                TtaskBase tb = ttaskBaseService.selectById(td.getTaskid());
                if (tsbool && !"2".equals(tb.getStatus())) {
                    tb.setStatus("1");
                    ttaskBaseService.insertOrUpdate(tb);
                }
                if (tb.getPresentdeposit() != null) {
                    TshopInfo si = tshopInfoService.selectOne(tb.getShopid());
                    BigDecimal price = tb.getPresentdeposit().add(tb.getActualprice());
//                    si.setTotaldeposit(si.getTotaldeposit().add(price));
                    si.setTaskdeposit(si.getTaskdeposit().subtract(price));
                    tshopInfoService.updateById(si);
//                    ttaskBaseService.upTask(tb, si, TfinanceRechargeService.rechargetype_4,price);
                }
            }
            td.setTaskstate(taskState);
            tmyTaskService.updateById(tt);
//            System.out.println("确定下单和确定收货操作:"+td.getId()+",传入状态："+taskState+",状态为："+td.getTaskstate()+"");
            updateById(td);
            TmyTaskDetail ttd = selectById(td.getId());
//            System.out.println("修改完成状态:"+td.getId()+",传入状态："+taskState+",状态为："+ttd.getTaskstate()+"");

        }
    }

    public Page<TmyTaskDetail> listDetail(Queryable queryable, Wrapper<TmyTaskDetail> wrapper) {
        QueryToWrapper<TmyTaskDetail> queryToWrapper = new QueryToWrapper<TmyTaskDetail>();
        queryToWrapper.parseCondition(wrapper, queryable);
        // 排序问题
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TmyTaskDetail> page = new com.baomidou.mybatisplus.plugins.Page<TmyTaskDetail>(
                pageable.getPageNumber(), pageable.getPageSize());
//        com.baomidou.mybatisplus.plugins.Page<TmyTaskDetail> content = baseMapper.listDetail(page, wrapper);
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.listDetail(page, wrapper));
        return new PageImpl<TmyTaskDetail>(page.getRecords(), queryable.getPageable(), page.getTotal());
//        return page;
    }

    public Page<TmyTaskDetail> listDetailGroup(Queryable queryable, Wrapper<TmyTaskDetail> wrapper) {
        QueryToWrapper<TmyTaskDetail> queryToWrapper = new QueryToWrapper<TmyTaskDetail>();
        queryToWrapper.parseCondition(wrapper, queryable);
        // 排序问题
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TmyTaskDetail> page = new com.baomidou.mybatisplus.plugins.Page<TmyTaskDetail>(
                pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.listDetailGroup(page, wrapper));
        return new PageImpl<TmyTaskDetail>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }

    public Page<TmyTaskDetail> listShopBaseDetail(Queryable queryable, Wrapper<TmyTaskDetail> wrapper) {
        QueryToWrapper<TmyTaskDetail> queryToWrapper = new QueryToWrapper<TmyTaskDetail>();
        queryToWrapper.parseCondition(wrapper, queryable);
        // 排序问题
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TmyTaskDetail> page = new com.baomidou.mybatisplus.plugins.Page<TmyTaskDetail>(
                pageable.getPageNumber(), pageable.getPageSize());
//        com.baomidou.mybatisplus.plugins.Page<TmyTaskDetail> content = baseMapper.listDetail(page, wrapper);
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.listShopBaseDetail(page, wrapper));
        return new PageImpl<TmyTaskDetail>(page.getRecords(), queryable.getPageable(), page.getTotal());
//        return page;
    }

    public List<TmyTaskDetail> listNoPageDetail(Queryable queryable, Wrapper<TmyTaskDetail> wrapper) {
        QueryToWrapper<TmyTaskDetail> queryToWrapper = new QueryToWrapper<TmyTaskDetail>();

        queryToWrapper.parseCondition(wrapper, queryable);
        // 排序问题
        queryToWrapper.parseSort(wrapper, queryable);
        return baseMapper.listDetail(wrapper);

    }

    public List<TmyTaskDetail> listNoPageDetailGroup(Queryable queryable, Wrapper<TmyTaskDetail> wrapper) {
        QueryToWrapper<TmyTaskDetail> queryToWrapper = new QueryToWrapper<TmyTaskDetail>();

        queryToWrapper.parseCondition(wrapper, queryable);
        // 排序问题
        queryToWrapper.parseSort(wrapper, queryable);
        return baseMapper.listDetailGroup(wrapper);

    }

    public int sumMyTask(Map map) throws Exception{
        return baseMapper.sumMyTask(map);
    }
    public int sumTaskBase(Map map) throws Exception{
        return baseMapper.sumTaskBase(map);
    }

    @Transactional
    public void upTaskErrorStatus(String taskId,int status,String lastRepair){
        Map<String,Object> paramMap=new HashMap<String,Object>();
        paramMap.put("taskId",taskId);
        paramMap.put("status",status);
        paramMap.put("lastTime", Calendar.getInstance().getTime());
        paramMap.put("lastRepair",lastRepair);
        baseMapper.upTaskErrorStatus(paramMap);
    }
}
