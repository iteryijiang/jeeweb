package cn.jeeweb.web.ebp.buyer.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerCommission;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerCommissionMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerCommissionService;

/**
 * 买手佣金
 * 
 * @author ytj
 *
 */
@Transactional
@Service("BuyerCommissionService")
public class TBuyerCommissionServiceImpl  extends CommonServiceImpl<TBuyerCommissionMapper,TBuyerCommission> implements TBuyerCommissionService{

}
