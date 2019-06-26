package cn.jeeweb.web.ebp.buyer.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerCommissionRecord;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerCommissionRecordMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerCommissionRecordService;

/**
 * 买手佣金记录
 * 每天定时任务生成
 * 
 * @author ytj
 *
 */
@Transactional
@Service("BuyerCommissionRecordService")
public class TBuyerCommissionRecordServiceImpl extends CommonServiceImpl<TBuyerCommissionRecordMapper,TBuyerCommissionRecord> implements TBuyerCommissionRecordService{
}
