package cn.jeeweb.web.ebp.logistics.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.enums.BuyerTaskStatusEnum;
import cn.jeeweb.web.ebp.enums.SysConfigParamEnum;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
import cn.jeeweb.web.ebp.exception.MyProcessException;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeLogService;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrder;
import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrderDetail;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowData;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowTitle;
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
import java.util.*;

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
    public Page<TLogisticsOrder> selectTLogisticsOrderPageList(Queryable queryable, Wrapper<TLogisticsOrder> wrapper) {
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
        //根据京东订单号获取任务单数据
        initProperties(obj);
        //扣除佣金
        TSysConfigParam sysConfigParamObj=sysConfigParamService.selectTSysConfigByConfigParam(SysConfigParamEnum.PLATFORM_LOGISTICS_COMMISSION.configParam);
        BigDecimal money=new BigDecimal(sysConfigParamObj.getParamValue());
        TshopInfo shopInfo = tshopInfoService.selectOne(obj.getShopUserId());
        if(shopInfo.getAvailabledeposit().subtract(money).compareTo(BigDecimal.ZERO)<0){
            throw  new MyProcessException("商户账户余额不足");
        }
        TfinanceRechargeLog log =new TfinanceRechargeLog(shopInfo.getUserid(),shopInfo.getShopname(),obj.getShopTaskId(), TfinanceRechargeService.rechargetype_5,money,shopInfo.getAvailabledeposit().add(money));
        StringBuffer financeLog=new StringBuffer("确认收货=>{总金额:").append(money).append(",佣金:").append(money).append(",实际支付:").append(money).append("}");
        log.setRemarks(financeLog.toString());
        tfinanceRechargeLogService.insert(log);
        tshopInfoService.updateShopMoney(shopInfo.getUserid(), money,BigDecimal.ZERO.subtract(money),UserUtils.getUser().getId());
        //保存记录
        obj.setOutStoreAckPayOrderId(log.getId());
        obj.setOutStoreAckPayMoney(money);
        int num=baseMapper.insert(obj);
        if(num != 1){
            throw  new MyProcessException("数据保存失败");
        }
        //根据任务单获取明细
        List<TLogisticsOrderDetail> detailList=new ArrayList<>();
        List<TShopOrderShowData> buyerTaskList=shopOrderShowService.getTShopOrderShowDataListByJdOrderNo(obj.getJdOrderNo());
        StringBuffer buyerTaskDetailId=new StringBuffer("");
        for(TShopOrderShowData objDataTemp:buyerTaskList){
            TLogisticsOrderDetail insertDetailObj=installTLogisticsOrderDetailObj(obj.getId(),objDataTemp);
            detailList.add(insertDetailObj);
            buyerTaskDetailId.append(",'").append(objDataTemp.getBuyerTaskDetailId()).append("'");
        }
        baseMapper.insertTLogisticsOrderDetail(detailList);
        //买手任务调整为平台出库
        tmyTaskDetailService.updateBuyerTaskDetailOutStoreAck(buyerTaskDetailId.substring(1),UserUtils.getUser().getId());
    }

    /**
     * 初始化填写数据值
     *
     * @param obj
     */
    private void initProperties(TLogisticsOrder obj){
        TShopOrderShowTitle titleObj=shopOrderShowService.getTShopOrderShowTitleByJdOrderNo(obj.getJdOrderNo());
        if(titleObj.getOrderStatus() != BuyerTaskStatusEnum.WAITING_SEND.code){
            throw new MyProcessException("当前订单状态不支持该操作");
        }
        obj.setOrderDtime(titleObj.getOrderDtime());
        obj.setOrderPayTime(titleObj.getOrderPayTime());
        obj.setGoodsTotalNum(titleObj.getGoodsTotalNum());
        obj.setOrderTotalMoney(titleObj.getOrderTotalMoney());
        obj.setOrderCouponTotalMoney(titleObj.getOrderCouponTotalMoney());
        obj.setOrderPayTotalMoney(titleObj.getOrderPayTotalMoney());
        obj.setOrderStatus(YesNoEnum.NO.code);
        obj.setLogisticsInfo("");
        obj.setBuyerMsg("");
        obj.setBuyerJdNickName(titleObj.getBuyerJdLoginNo());
        obj.setBuyerId(titleObj.getBuyerId());
        obj.setShopUserId(titleObj.getShopUserId());
        obj.setShopTaskId(titleObj.getShopTaskId());
        obj.setShopRemark("");
        obj.setOutStoreShouldPayMoney(titleObj.getOutStoreCommissionPrice());
        obj.setOutStoreTime(DateUtils.getCurrentTime());
        obj.setOutStoreOpeMan(UserUtils.getUser().getId());
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
        retObj.setId(StringUtils.randomUUID());
        retObj.setMasterId(masterId);
        retObj.setBuyerTaskDetailId(objDataTemp.getBuyerTaskDetailId());
        retObj.setCreateBy(UserUtils.getUser());
        retObj.setCreateDate(DateUtils.getCurrentTime());
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
    public void updateTLogisticsOrderStatus(String ids,int status) {
        String ackBatchId=StringUtils.randomUUID();
        for(String id:ids.split(",")){
            if(StringUtils.isNotEmpty(id)){
                updateTLogisticsOrderStatusForOutStore(ackBatchId,id,status);
            }
        }
    }

    /**
     * 单个任务出库操作
     *
     * @param id
     * @param status
     */
    private void updateTLogisticsOrderStatusForOutStore(String ackBatchId,String id,int status){
        TLogisticsOrder objDb=selectTLogisticsOrderById(id);
        if(objDb == null ){
            throw  new MyProcessException("参数错误[未获取到订单记录]");
        }
        if(objDb.getOrderStatus() == YesNoEnum.YES.code ){
            throw  new MyProcessException("参数错误[该订单已经出库]");
        }
        User loginUser=UserUtils.getUser();
        //更改买手任务状态
        List<TLogisticsOrderDetail> logisticsOrderDetailList=baseMapper.selectTLogisticsOrderDetailListByMasterid(id);
        for(TLogisticsOrderDetail obj:logisticsOrderDetailList){
            TmyTaskDetail buyerTaskDetailObj=tmyTaskDetailService.selectById(obj.getBuyerTaskDetailId());
            if(Integer.valueOf(buyerTaskDetailObj.getTaskstate()) == BuyerTaskStatusEnum.WAITING_SEND.code){
                tmyTaskDetailService.updateTaskStatusForAckReceive(obj.getBuyerTaskDetailId(),loginUser.getId());
            }
        }
        //更改申请状态
        objDb.setOrderStatus(Integer.valueOf(YesNoEnum.YES.code));
        objDb.setOutStoreAckTime(DateUtils.getCurrentTime());
        objDb.setOutStoreAckPayMoney(objDb.getOutStoreShouldPayMoney());
        objDb.setOutStoreAckBatchId(ackBatchId);
        objDb.setOutStoreAckMan(loginUser.getId());
        objDb.setUpdateBy(loginUser);
        objDb.setUpdateDate(DateUtils.getCurrentTime());
        baseMapper.updateTLogisticsOrderStatus(objDb);
        Map<String,Object> updateDetailMap=new HashMap<>();
        updateDetailMap.put("masterId",id);
        updateDetailMap.put("status",Integer.valueOf(YesNoEnum.YES.code));
        updateDetailMap.put("updateBy",loginUser.getId());
        updateDetailMap.put("updateTime",DateUtils.getCurrentTime());
        baseMapper.updateTLogisticsOrderDetailStatus(updateDetailMap);
    }
}