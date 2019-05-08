package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyer;
import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyerHandle;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.mapper.TapplyTaskBuyerHandleMapper;
import cn.jeeweb.web.ebp.buyer.service.TapplyTaskBuyerHandleService;
import cn.jeeweb.web.ebp.buyer.service.TapplyTaskBuyerService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailQuestionService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.enums.BuyerTaskStatusRnum;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeLogService;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.jeeweb.web.ebp.enums.UnusualTaskHandleMethodEnum ;

import java.math.BigDecimal;
import java.util.*;

@Transactional
@Service("TapplyTaskBuyerHandleService")
public class TapplyTaskBuyerHandleServiceImpl extends CommonServiceImpl<TapplyTaskBuyerHandleMapper, TapplyTaskBuyerHandle> implements TapplyTaskBuyerHandleService {

    @Autowired
    private TapplyTaskBuyerService tapplyTaskBuyerService;
    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TshopInfoService tshopInfoService1;
    @Autowired
    private TapplyTaskBuyerHandleService tapplyTaskBuyerHandleService;
    @Autowired
    private TmyTaskDetailQuestionService tmyTaskDetailQuestionService;
    @Autowired
    private TfinanceRechargeLogService tfinanceRechargeLogService;
    @Autowired
    private TtaskBaseService ttaskBaseService;

    @Override
    @Transactional
    public void updateTapplyTaskBuyerForHandle(TapplyTaskBuyerHandle obj) throws Exception{
        TapplyTaskBuyer taskBuyerObj=tapplyTaskBuyerService.selectById(obj.getApplyTaskId());
        //已处理订单不作调整
        if(YesNoEnum.YES.code == taskBuyerObj.getApplyStatus()){
            throw new RuntimeException("申请记录已经处理！") ;
        }
        obj.setBuyerTaskId(taskBuyerObj.getBuyerTaskId());
        obj.setShopTaskId(taskBuyerObj.getShopTaskId());
        //更改记录状态
        taskBuyerObj.setHandleMan("admin");
        taskBuyerObj.setHandleTime(Calendar.getInstance().getTime());
        taskBuyerObj.setApplyStatus(YesNoEnum.YES.code);
        taskBuyerObj.setHandleMethod(obj.getHandleMethod());
        tapplyTaskBuyerService.updateTapplyTaskBuyerStatus(taskBuyerObj);
        //判断处理类型
        if(UnusualTaskHandleMethodEnum.SHOPTASK_CANCEL.code == obj.getHandleMethod()){//撤销商家订单
            //获取商家任务
            TtaskBase taskBase=ttaskBaseService.selectById(taskBuyerObj.getShopTaskId());
            //获取商家任务下对应的所有买手未完成的任务
            List<TmyTaskDetail> buyTaskList=tmyTaskDetailService.selBaseIdMyTaskDetailList(taskBuyerObj.getShopTaskId());
            //当前没有买手可用任务
            if(buyTaskList == null || buyTaskList.size()<1){
                return  ;
            }
            //调整买手任务信息
            BigDecimal needBackMoney= updateBuyTaskForHandleUnsualTask(taskBuyerObj,obj,taskBase.getPresentdeposit(),buyTaskList);
            //实付金额+佣金
            //返还商家金额=(已领用+未领用)-已下单的
            tshopInfoService1.updateShopMoney(taskBuyerObj.getShopId(),needBackMoney,BigDecimal.ZERO.subtract(needBackMoney),obj.getCreateBy().getId());
            TshopInfo info = tshopInfoService1.selectById(taskBuyerObj.getShopId());
            TfinanceRechargeLog log = new TfinanceRechargeLog(taskBuyerObj.getShopId(),info.getShopname(),taskBuyerObj.getShopTaskId(),TfinanceRechargeService.rechargetype_4,needBackMoney,info.getAvailabledeposit().add(needBackMoney));
            tfinanceRechargeLogService.insert(log);
            //处理商家status发布状态更改为2
            return;
        }
        //撤销买手订单=>回退申请+买手任务状态
        TmyTaskDetail buyTaskObj=tmyTaskDetailService.selectById(obj.getBuyerTaskId());
        if(Integer.valueOf(buyTaskObj.getTaskstate())>= BuyerTaskStatusRnum.WAITING_SEND.code){
            throw new RuntimeException("当前买手任务不支持该操作！") ;
        }
        List<TmyTaskDetail> buyTaskList=new ArrayList<TmyTaskDetail>();
        buyTaskList.add(buyTaskObj);
        //updateBuyTaskForHandleUnsualTask(taskBuyerObj,obj,buyTaskList);
    }

    /**
     * 执行撤销买手任务
     * 该方法不做参数校验
     *
     * @param taskBuyerObj
     * @param handleObj
     * @param  yongJin
     * @param buyTaskList
     */
    private BigDecimal updateBuyTaskForHandleUnsualTask(TapplyTaskBuyer taskBuyerObj, TapplyTaskBuyerHandle handleObj,BigDecimal yongJin, List<TmyTaskDetail> buyTaskList) throws Exception{
        BigDecimal sumDeposit=BigDecimal.ZERO;
        List<TapplyTaskBuyerHandle> insertLIst=new ArrayList<TapplyTaskBuyerHandle>();
        for(TmyTaskDetail obj:buyTaskList){
            int taskState=Integer.valueOf(obj.getTaskstate());
            if(taskState >= BuyerTaskStatusRnum.WAITING_SEND.code){//不继续操作
                continue;
            }
            //调用更改任务方法
            tmyTaskDetailQuestionService.saveTmyTaskDetailQuestion(null,obj);
            //组装买手ID等待减少任务
            TapplyTaskBuyerHandle insertObj=new TapplyTaskBuyerHandle();
            insertObj.setApplyTaskId(taskBuyerObj.getId());
            insertObj.setHandleMethod(handleObj.getHandleMethod());
            insertObj.setBuyerTaskId(obj.getId());
            insertObj.setShopTaskId(handleObj.getShopTaskId());
            insertLIst.add(insertObj);
            sumDeposit=sumDeposit.add(obj.getPays()).add(yongJin);
        }
        tapplyTaskBuyerHandleService.insertBatch(insertLIst);
        return sumDeposit;
    }
}
