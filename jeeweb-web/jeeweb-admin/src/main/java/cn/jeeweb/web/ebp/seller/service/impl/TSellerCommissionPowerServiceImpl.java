package cn.jeeweb.web.ebp.seller.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerLevelMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerLevelService;
import cn.jeeweb.web.ebp.exception.MyProcessException;
import cn.jeeweb.web.ebp.seller.entity.TSellerLevel;
import cn.jeeweb.web.ebp.seller.mapper.TSellerCommissionPowerMapper;
import cn.jeeweb.web.ebp.seller.service.TSellerCommissionPowerService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Transactional
@Service("sellerCommissionPowerService")
public class TSellerCommissionPowerServiceImpl extends CommonServiceImpl<TSellerCommissionPowerMapper, TSellerLevel> implements TSellerCommissionPowerService {


}