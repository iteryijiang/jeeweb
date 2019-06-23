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
import cn.jeeweb.web.ebp.comm.Constant;
import cn.jeeweb.web.ebp.enums.BuyerTaskStatusEnum;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeLogService;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.notice.entity.NoticeInfo;
import cn.jeeweb.web.ebp.notice.enums.NoticeBizEnum;
import cn.jeeweb.web.ebp.notice.enums.NoticeLevelEnum;
import cn.jeeweb.web.ebp.notice.enums.NoticeRangeEnum;
import cn.jeeweb.web.ebp.notice.enums.NoticeStatusEnum;
import cn.jeeweb.web.ebp.notice.service.NoticeService;
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
    @Autowired
    private NoticeService noticeService;

    @Override
    public void updateTapplyTaskBuyerForHandle(TapplyTaskBuyerHandle obj) throws Exception{
        //获取买手申请记录
        TapplyTaskBuyer taskBuyerObj=tapplyTaskBuyerService.selectById(obj.getApplyTaskId());
        //已处理订单不作调整
        if(YesNoEnum.YES.code == taskBuyerObj.getApplyStatus()){
            throw new RuntimeException("申请记录已经处理！") ;
        }
        obj.setBuyerTaskId(taskBuyerObj.getBuyerTaskId());
        obj.setShopTaskId(taskBuyerObj.getShopTaskId());
        //更改记录状态
        taskBuyerObj.setHandleMan(obj.getCreateBy().getUsername());
        taskBuyerObj.setHandleTime(Calendar.getInstance().getTime());
        taskBuyerObj.setApplyStatus(YesNoEnum.YES.code);
        taskBuyerObj.setHandleMethod(obj.getHandleMethod());
        taskBuyerObj.setUpdateBy(obj.getCreateBy());
        taskBuyerObj.setUpdateDate(Calendar.getInstance().getTime());
        tapplyTaskBuyerService.updateTapplyTaskBuyerStatus(taskBuyerObj);
        //判断处理类型
        if(UnusualTaskHandleMethodEnum.SHOPTASK_CANCEL.code == obj.getHandleMethod()){//撤销商家订单
            //获取商家任务
            TtaskBase taskBase=ttaskBaseService.selectById(taskBuyerObj.getShopTaskId());
            //获取商家任务下对应的所有买手未完成的任务
            TmyTaskDetail buyTaskObj=tmyTaskDetailService.selectById(obj.getBuyerTaskId());
            List<TmyTaskDetail> buyTaskList=new ArrayList<>();
            buyTaskList.add(buyTaskObj);
            BigDecimal needBackMoney=BigDecimal.ZERO;
            List<String> receiveBuyerList=new ArrayList<String>();
            //当前存在买手任务
            StringBuffer financeLog=new StringBuffer("撤销商户任务=>");
            if(buyTaskList != null && buyTaskList.size()>0){
                needBackMoney= updateBuyTaskForHandleUnsualTask(taskBuyerObj,obj,taskBase.getPresentdeposit(),buyTaskList,receiveBuyerList);
                financeLog.append("买手任务[{id:").append(buyTaskObj.getId()).append(",money:").append(needBackMoney).append("}],");
            }
            //剩余的买手任务
            if(taskBase.getCanreceivenum() > 0 && YesNoEnum.NO.code == Integer.valueOf(taskBase.getStatus())){//剩余接单次数
                //剩余接单的金额=剩余单数*（每单实付金额+佣金）
                BigDecimal unreceiveMoney=new BigDecimal(taskBase.getCanreceivenum()).multiply(taskBase.getPresentdeposit().add(taskBase.getActualprice()));
                needBackMoney=needBackMoney.add(unreceiveMoney);
                financeLog.append("未领取任务[{num:").append(taskBase.getCanreceivenum()).append(",money:").append(unreceiveMoney).append("}]");
            }
            TshopInfo info = tshopInfoService1.selectOne(taskBuyerObj.getShopId());
            TfinanceRechargeLog log =new TfinanceRechargeLog(taskBuyerObj.getShopId(),taskBase.getStorename(),taskBuyerObj.getShopTaskId(),TfinanceRechargeService.rechargetype_4,needBackMoney,info.getAvailabledeposit().add(needBackMoney));
            log.setRemarks(financeLog.toString());
            tfinanceRechargeLogService.insert(log);
            tshopInfoService1.updateShopMoney(taskBuyerObj.getShopId(),BigDecimal.ZERO.subtract(needBackMoney),needBackMoney,obj.getCreateBy().getId());
            //更改商家任务状态
            taskBase.setStatus("2");
            ttaskBaseService.updateById(taskBase);
            //商户消息|买手消息
            pushNoticeForCancelShopTask(taskBuyerObj,receiveBuyerList);
            return;
        }
        //撤销买手订单=>回退申请+买手任务异常申请状态
        TmyTaskDetail buyTaskObj=tmyTaskDetailService.selectById(obj.getBuyerTaskId());
        if(buyTaskObj==null){
            throw new RuntimeException("未获取到买手申请对应的任务记录！") ;
        }
        if(Integer.valueOf(buyTaskObj.getTaskstate())>= BuyerTaskStatusEnum.WAITING_SEND.code){
            throw new RuntimeException("当前买手任务不支持该操作！") ;
        }
        buyTaskObj.setErrorStatus(YesNoEnum.NO.code);
        tmyTaskDetailService.updateById(buyTaskObj);
        List<TmyTaskDetail> buyTaskList=new ArrayList<TmyTaskDetail>();
        buyTaskList.add(buyTaskObj);
        pushNoticeForTurnDownTask(taskBuyerObj);
    }

    /**
     * 取消商家任务推送消息
     *
     * @param taskBuyerObj
     * @param receiveBuyerList
     */
    private void pushNoticeForCancelShopTask(TapplyTaskBuyer taskBuyerObj,List<String> receiveBuyerList){
        List<NoticeInfo> noticeList=new ArrayList<NoticeInfo>();
        String shopNoticeInfo="商家任务["+taskBuyerObj.getShopTaskNo()+ "]已被取消";
        String shopNoticeLink="";
        String shopNoticeReceive=taskBuyerObj.getShopId();
        NoticeInfo shopNoticeObj=new NoticeInfo(NoticeRangeEnum.SINGLE.rangeValue,shopNoticeInfo, NoticeStatusEnum.UNREAD.code, NoticeBizEnum.BUYER.bizCode,taskBuyerObj.getShopTaskId(), NoticeLevelEnum.THREE.levelValue, shopNoticeLink, shopNoticeReceive,Constant.SysGroup,taskBuyerObj.getHandleTime());
        noticeList.add(shopNoticeObj);
        if(!receiveBuyerList.isEmpty()){
            for(String buyerIdTemp:receiveBuyerList){
                NoticeInfo buyNoticeObjTemp=new NoticeInfo(NoticeRangeEnum.SINGLE.rangeValue,shopNoticeInfo, NoticeStatusEnum.UNREAD.code, NoticeBizEnum.BUYER.bizCode,taskBuyerObj.getBuyerTaskId(), NoticeLevelEnum.THREE.levelValue, shopNoticeLink, buyerIdTemp,Constant.SysGroup,taskBuyerObj.getHandleTime());
                noticeList.add(buyNoticeObjTemp);
            }
        }
        String buyerNoticeInfo="任务单["+taskBuyerObj.getBuyerTaskNo()+"]异常申请已有了新的状态变动";
        String buyerNoticeLink="";
        String buyerNoticeReceive=taskBuyerObj.getBuyerId();
        NoticeInfo buyerNoticeObj=new NoticeInfo(NoticeRangeEnum.SINGLE.rangeValue,buyerNoticeInfo, NoticeStatusEnum.UNREAD.code, NoticeBizEnum.BUYER.bizCode,taskBuyerObj.getBuyerTaskId(), NoticeLevelEnum.THREE.levelValue, buyerNoticeLink, buyerNoticeReceive,Constant.SysGroup,taskBuyerObj.getHandleTime());
        noticeList.add(buyerNoticeObj);
        noticeService.addNotice(noticeList);
    }

    /**
     * 驳回任务单消息
     *
     * @param taskBuyerObj
     */
    private void pushNoticeForTurnDownTask(TapplyTaskBuyer taskBuyerObj){
        String buyerNoticeInfo="任务单["+taskBuyerObj.getBuyerTaskNo()+"]异常申请已有了新的状态变动";
        String buyerNoticeLink="";
        NoticeInfo buyerNoticeObj=new NoticeInfo(NoticeRangeEnum.SINGLE.rangeValue,buyerNoticeInfo, NoticeStatusEnum.UNREAD.code, NoticeBizEnum.BUYER.bizCode,taskBuyerObj.getBuyerTaskId(), NoticeLevelEnum.THREE.levelValue, buyerNoticeLink, taskBuyerObj.getBuyerId(),Constant.SysGroup,taskBuyerObj.getHandleTime());
        noticeService.addNotice(buyerNoticeObj);
    }

    /**
     * 执行撤销买手任务
     * 该方法不做参数校验
     *
     * @param taskBuyerObj
     * @param handleObj
     * @param  yongJin
     * @param buyTaskList
     * @param  receiveBuyerList
     */
    private BigDecimal updateBuyTaskForHandleUnsualTask(TapplyTaskBuyer taskBuyerObj, TapplyTaskBuyerHandle handleObj,BigDecimal yongJin, List<TmyTaskDetail> buyTaskList,List<String> receiveBuyerList) throws Exception{
        BigDecimal sumDeposit=BigDecimal.ZERO;
        List<TapplyTaskBuyerHandle> insertLIst=new ArrayList<TapplyTaskBuyerHandle>();
        for(TmyTaskDetail obj:buyTaskList){
            int taskState=Integer.valueOf(obj.getTaskstate());
            if(taskState >= BuyerTaskStatusEnum.WAITING_SEND.code){//不继续操作
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
            insertObj.setRemarks(handleObj.getRemarks());
            insertLIst.add(insertObj);
            sumDeposit=sumDeposit.add(obj.getPays()).add(yongJin);
            receiveBuyerList.add(obj.getBuyerid());
        }
        tapplyTaskBuyerHandleService.insertBatch(insertLIst);
        return sumDeposit;
    }
}
