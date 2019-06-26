package cn.jeeweb.web.ebp.chargeback.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.chargeback.entity.CanChargeBackTask;
import cn.jeeweb.web.ebp.chargeback.entity.TChargeBackRecord;
import cn.jeeweb.web.ebp.chargeback.mapper.TChargeBackRecordMapper;
import cn.jeeweb.web.ebp.chargeback.service.TChargeBackRecordService;
import cn.jeeweb.web.ebp.enums.BuyerTaskStatusEnum;
import cn.jeeweb.web.ebp.enums.EcommerceEnum;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeLogService;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@Service("tchargeBackRecordService")
public class TChargeBackRecordServiceImpl extends CommonServiceImpl<TChargeBackRecordMapper, TChargeBackRecord> implements TChargeBackRecordService {

    @Autowired
    private TshopInfoService tshopInfoService;
    @Autowired
    private TfinanceRechargeLogService tfinanceRechargeLogService;
    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;

    @Override
    public void addTChargeBackRecord(TChargeBackRecord obj)throws RuntimeException{
    	CanChargeBackTask chargeBackTaskObj=baseMapper.selectCanChargeBackTaskByTaskId(obj.getBuyerTaskId());
        if(chargeBackTaskObj == null){
            throw  new RuntimeException("未获取到原任务单数据");
        }
        if(BuyerTaskStatusEnum.CHARGEBACK.code == Integer.valueOf(chargeBackTaskObj.getBuyTaskStatus())){
            throw  new RuntimeException("当前任务单状态不支持退单操作");
        }
        obj.setShopId(chargeBackTaskObj.getShopId());
        obj.setShopName(chargeBackTaskObj.getShopName());
        obj.setGoodsName(chargeBackTaskObj.getGoodsName());
        obj.setShopTaskNo(chargeBackTaskObj.getShopTaskNo());
        obj.setShopTaskId(chargeBackTaskObj.getShopTaskId());
        obj.setReceivingdate(chargeBackTaskObj.getReceivingdate());
        obj.setDtime(DateUtils.getCurrentTime());
        obj.setChargeBackMoney(chargeBackTaskObj.getTaskPayMoney().add(chargeBackTaskObj.getTaskCommission()));
        obj.setBuyerTaskNo(chargeBackTaskObj.getBuyerTaskNo());
        obj.setBuyerId(chargeBackTaskObj.getBuyerId());
        obj.setBuyerNo(chargeBackTaskObj.getBuyerNo());
        obj.setEcommerceType(EcommerceEnum.JD.code);
        obj.setEcommerceOrderNo(chargeBackTaskObj.getEcommerceOrderNo());
        baseMapper.insert(obj);
        tmyTaskDetailService.updateTaskStatusForChargeBack(chargeBackTaskObj.getBuyerTaskId(), obj.getCreateBy().getId());
        TshopInfo shopInfo = tshopInfoService.selectOne(chargeBackTaskObj.getShopUserId());
        TfinanceRechargeLog log =new TfinanceRechargeLog(shopInfo.getUserid(),chargeBackTaskObj.getShopName(),obj.getShopTaskId(), TfinanceRechargeService.rechargetype_4,obj.getChargeBackMoney(),shopInfo.getAvailabledeposit().add(obj.getChargeBackMoney()));
        StringBuffer financeLog=new StringBuffer("退单=>{总金额:").append(obj.getChargeBackMoney()).append(",佣金:").append(chargeBackTaskObj.getTaskCommission()).append(",实际支付:").append(chargeBackTaskObj.getTaskPayMoney()).append("}");
        log.setRemarks(financeLog.toString());
        tfinanceRechargeLogService.insert(log);
        tshopInfoService.updateShopMoney(shopInfo.getUserid(), BigDecimal.ZERO.subtract(obj.getChargeBackMoney()),obj.getChargeBackMoney(),obj.getCreateBy().getId());
    }

    @Override
    public Page<CanChargeBackTask> selectCanChargeBackTaskPageList(Queryable queryable, Wrapper<CanChargeBackTask> wrapper){
        QueryToWrapper<CanChargeBackTask> queryToWrapper = new QueryToWrapper<CanChargeBackTask>();
        queryToWrapper.parseCondition(wrapper, queryable);
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<CanChargeBackTask> page = new com.baomidou.mybatisplus.plugins.Page<CanChargeBackTask>(pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.getCanChargeBackTaskList(page, wrapper));
        return new PageImpl<CanChargeBackTask>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }
    
    @Override
    public CanChargeBackTask selectCanChargeBackTaskByTaskId(String taskId) {
    	return baseMapper.selectCanChargeBackTaskByTaskId(taskId);
    }

    @Override
    public Page<TChargeBackRecord> selectChargeBackRecordPageList(Queryable queryable, Wrapper<TChargeBackRecord> wrapper){
        QueryToWrapper<TChargeBackRecord> queryToWrapper = new QueryToWrapper<TChargeBackRecord>();
        queryToWrapper.parseCondition(wrapper, queryable);
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TChargeBackRecord> page = new com.baomidou.mybatisplus.plugins.Page<TChargeBackRecord>(pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.getChargeBackRecordList(page, wrapper));
        return new PageImpl<TChargeBackRecord>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }
    
    @Override
    public TChargeBackRecord getChargeBackRecordById(String id) {
    	return baseMapper.getChargeBackRecordById(id);
    }
    
}
