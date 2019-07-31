package cn.jeeweb.web.ebp.logistics.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.enums.SysConfigParamEnum;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
import cn.jeeweb.web.ebp.exception.MyProcessException;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeLogService;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrder;
import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrderDetail;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowData;
import cn.jeeweb.web.ebp.logistics.mapper.TLogisticsOrderMapper;
import cn.jeeweb.web.ebp.logistics.service.TLogisticsOrderService;
import cn.jeeweb.web.ebp.logistics.service.TShopOrderShowService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.tsys.entity.TSysConfigParam;
import cn.jeeweb.web.ebp.tsys.service.TSysConfigParamService;
import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.utils.UserUtils;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统参数设置
 *
 */
@Transactional
@Service("logisticsOrderService")
public class TLogisticsOrderServiceImpl extends CommonServiceImpl<TLogisticsOrderMapper, TLogisticsOrder> implements TLogisticsOrderService {

    @Autowired
    private TSysConfigParamService sysConfigParamService;
    @Autowired
    private TShopOrderShowService shopOrderShowService;
    @Autowired
    private TshopInfoService tshopInfoService;
    @Autowired
    private TfinanceRechargeLogService tfinanceRechargeLogService;
    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;

    @Override
    public Page<TLogisticsOrder> selectTSysConfigParamPageList(Queryable queryable, Wrapper<TLogisticsOrder> wrapper) {
        QueryToWrapper<TLogisticsOrder> queryToWrapper = new QueryToWrapper<TLogisticsOrder>();
        queryToWrapper.parseCondition(wrapper, queryable);
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TLogisticsOrder> page = new com.baomidou.mybatisplus.plugins.Page<TLogisticsOrder>(pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectTLogisticsOrderPageList(page, wrapper));
        return new PageImpl<TLogisticsOrder>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }

    @Override
    public TLogisticsOrder selectTLogisticsOrderById(String id) {
        return baseMapper.selectTLogisticsOrderById(id);
    }

    @Override
    public void insertTLogisticsOrder(TLogisticsOrder obj) {
        baseMapper.insertTLogisticsOrder(obj);
        //根据任务单获取明细
        List<TLogisticsOrderDetail> detailList=new ArrayList<>();
        List<TShopOrderShowData> buyerTaskList=shopOrderShowService.getTShopOrderShowDataListByJdOrderNo(obj.getJdOrderNo());
        for(TShopOrderShowData objDataTemp:buyerTaskList){
            TLogisticsOrderDetail insertDetailObj=installTLogisticsOrderDetailObj(obj.getId(),objDataTemp);
            detailList.add(insertDetailObj);
        }
        baseMapper.insertTLogisticsOrderDetail(detailList);
    }

    /***
     * 组装明细数据
     *
     * @param masterId
     * @param objDataTemp
     * @return
     */
    private TLogisticsOrderDetail installTLogisticsOrderDetailObj(String masterId,TShopOrderShowData objDataTemp){
        TLogisticsOrderDetail retObj=new TLogisticsOrderDetail();
        retObj.setMasterId(masterId);
        retObj.setBuyerTaskDetailId(objDataTemp.getBuyerTaskDetailId());
        retObj.setStatus(YesNoEnum.NO.code);
        retObj.setDelFlag(YesNoEnum.NO.code+"");
        return retObj;
    }


    @Override
    public void insertTLogisticsOrder(List<TLogisticsOrder> objList){
        for(TLogisticsOrder obj : objList){
            insertTLogisticsOrder(obj);
        }
    }


    @Override
    public void updateTLogisticsOrderStatus(String id,int status) {
        TLogisticsOrder objDb=selectTLogisticsOrderById(id);
        if(objDb == null ){
            throw  new MyProcessException("参数错误[未获取到订单记录]");
        }
        if(objDb.getOrderStatus() == YesNoEnum.YES.code ){
            throw  new MyProcessException("参数错误[该订单已经出库]");
        }
        User loginUser=UserUtils.getUser();
        //扣除佣金
        TSysConfigParam sysConfigParamObj=sysConfigParamService.selectTSysConfigByConfigParam(SysConfigParamEnum.PLATFORM_LOGISTICS_COMMISSION.configParam);
        BigDecimal money=new BigDecimal(sysConfigParamObj.getParamValue());
        TshopInfo shopInfo = tshopInfoService.selectOne(objDb.getShopUserId());
        if(shopInfo.getAvailabledeposit().subtract(money).compareTo(BigDecimal.ZERO)<0){
            throw  new MyProcessException("商户账户余额不足");
        }
        TfinanceRechargeLog log =new TfinanceRechargeLog(shopInfo.getUserid(),shopInfo.getShopname(),objDb.getShopTaskId(), TfinanceRechargeService.rechargetype_5,money,shopInfo.getAvailabledeposit().add(money));
        StringBuffer financeLog=new StringBuffer("确认收货=>{总金额:").append(money).append(",佣金:").append(money).append(",实际支付:").append(money).append("}");
        log.setRemarks(financeLog.toString());
        tfinanceRechargeLogService.insert(log);
        tshopInfoService.updateShopMoney(shopInfo.getUserid(), money,BigDecimal.ZERO.subtract(money),loginUser.getCreateBy().getId());
        //更改买手任务状态
        List<TLogisticsOrderDetail> logisticsOrderDetailList=baseMapper.selectTLogisticsOrderDetailListByMasterid(id);
        for(TLogisticsOrderDetail obj:logisticsOrderDetailList){
            tmyTaskDetailService.updateTaskStatusForAckReceive(obj.getBuyerTaskDetailId(),loginUser.getCreateBy().getId());
        }
        //更改申请状态
        objDb.setOrderStatus(Integer.valueOf(YesNoEnum.YES.code));
        objDb.setOutStoreAckPayOrderId(log.getId());
        objDb.setOutStoreAckPayTime(DateUtils.getCurrentTime());
        objDb.setOutStoreAckPayMoney(money);
        baseMapper.updateTLogisticsOrderStatus(objDb);
        Map<String,Object> updateDetailMap=new HashMap<>();
        updateDetailMap.put("masterId",id);
        updateDetailMap.put("status",Integer.valueOf(YesNoEnum.YES.code));
        updateDetailMap.put("updateBy",loginUser.getId());
        updateDetailMap.put("updateTime",DateUtils.getCurrentTime());
        baseMapper.updateTLogisticsOrderDetailStatus(updateDetailMap);
    }
}