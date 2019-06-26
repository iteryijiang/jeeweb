package cn.jeeweb.web.ebp.buyer.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerLevelMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerLevelService;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Transactional
@Service("BuyerLevelService")
public class TBuyerLevelServiceImpl extends CommonServiceImpl<TBuyerLevelMapper,TBuyerLevel> implements TBuyerLevelService {
}