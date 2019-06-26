package cn.jeeweb.web.ebp.buyer.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroup;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerGroupMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerGroupService;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Transactional
@Service("BuyerGroupService")
public class TBuyerGroupServiceImpl extends CommonServiceImpl<TBuyerGroupMapper,TBuyerGroup> implements TBuyerGroupService{
}