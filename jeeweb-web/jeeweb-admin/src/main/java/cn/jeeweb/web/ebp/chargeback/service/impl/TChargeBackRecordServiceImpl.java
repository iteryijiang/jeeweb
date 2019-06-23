package cn.jeeweb.web.ebp.chargeback.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
import cn.jeeweb.web.ebp.chargeback.entity.TChargeBackRecord;
import cn.jeeweb.web.ebp.chargeback.mapper.TChargeBackRecordMapper;
import cn.jeeweb.web.ebp.chargeback.service.TChargeBackRecordService;
import cn.jeeweb.web.ebp.enums.BuyerTaskStatusEnum;
import cn.jeeweb.web.ebp.enums.EcommerceEnum;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeLogService;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.shop.entity.TshopBase;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TshopBaseService;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@Service("tchargeBackRecordService")
public class TChargeBackRecordServiceImpl extends CommonServiceImpl<TChargeBackRecordMapper, TChargeBackRecord> implements TChargeBackRecordService {

    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TmyTaskService tmyTaskService;
    @Autowired
    private TshopInfoService tshopInfoService;
    @Autowired
    private TshopBaseService tshopBaseService;
    @Autowired
    private TtaskBaseService ttaskBaseService;
    @Autowired
    private TfinanceRechargeLogService tfinanceRechargeLogService;

    @Override
    public void addTChargeBackRecord(TChargeBackRecord obj)throws RuntimeException{
        //获取任务单记录
        TmyTaskDetail buyerTaskObj=tmyTaskDetailService.selectById(obj.getBuyerTaskId());
        if(buyerTaskObj == null){
            throw  new RuntimeException("未获取到原任务单数据");
        }
        if(BuyerTaskStatusEnum.CHARGEBACK.code == Integer.valueOf(buyerTaskObj.getTaskstate())){
            throw  new RuntimeException("当前任务单状态不支持退单操作");
        }
        TmyTask tmTask=tmyTaskService.selectById(buyerTaskObj.getMytaskid());
        //更改任务单状态//更改商户余额//添加记录
        TtaskBase taskBaseObj=ttaskBaseService.selectById(buyerTaskObj.getTaskid());
        TshopBase shopbaseObj=tshopBaseService.selectById(buyerTaskObj.getStorename());
        obj.setShopId(taskBaseObj.getShopid());
        obj.setShopName(shopbaseObj.getShopname());
        obj.setGoodsName(taskBaseObj.gettTitle());
        obj.setShopTaskNo(taskBaseObj.getTaskno());
        obj.setShopTaskId(buyerTaskObj.getTaskid());
        obj.setDtime(DateUtils.getCurrentTime());
        obj.setChargeBackMoney(buyerTaskObj.getPays().add(taskBaseObj.getPresentdeposit()));
        obj.setBuyerTaskNo(tmTask.getMytaskno());
        obj.setBuyerId(buyerTaskObj.getBuyerid());
        obj.setBuyerNo(buyerTaskObj.getBuyeridLogin());
        obj.setEcommerceType(EcommerceEnum.JD.code);
        obj.setEcommerceOrderNo(buyerTaskObj.getJdorderno());
        baseMapper.insert(obj);
        TshopInfo shopInfo = tshopInfoService.selectOne(buyerTaskObj.getStorename());
        TfinanceRechargeLog log =new TfinanceRechargeLog(shopInfo.getUserid(),taskBaseObj.getStorename(),obj.getShopTaskId(), TfinanceRechargeService.rechargetype_4,obj.getChargeBackMoney(),shopInfo.getAvailabledeposit().add(obj.getChargeBackMoney()));
        StringBuffer financeLog=new StringBuffer("退单=>{总金额:").append(obj.getChargeBackMoney()).append(",佣金:").append(taskBaseObj.getPresentdeposit()).append(",实际支付:").append(buyerTaskObj.getPays()).append("}");
        log.setRemarks(financeLog.toString());
        tfinanceRechargeLogService.insert(log);
        tshopInfoService.updateShopMoney(shopInfo.getUserid(), BigDecimal.ZERO.subtract(obj.getChargeBackMoney()),obj.getChargeBackMoney(),obj.getCreateBy().getId());
    }
}
