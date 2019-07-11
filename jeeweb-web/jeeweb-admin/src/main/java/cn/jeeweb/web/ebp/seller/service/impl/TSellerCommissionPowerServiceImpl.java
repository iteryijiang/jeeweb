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
import cn.jeeweb.web.ebp.seller.entity.TSellerCommissionDateRange;
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

    @Override
    public Page<TSellerLevel> selectSellerLevelPageList(Queryable queryable, Wrapper<TSellerLevel> wrapper){
        QueryToWrapper<TSellerLevel> queryToWrapper = new QueryToWrapper<TSellerLevel>();
        queryToWrapper.parseCondition(wrapper, queryable);
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TSellerLevel> page = new com.baomidou.mybatisplus.plugins.Page<TSellerLevel>(pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectSellerLevelPageList(page, wrapper));
        return new PageImpl<TSellerLevel>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }

    @Override
    public Page<TSellerCommissionDateRange> selectSellerCommissionDateRangePageList(Queryable queryable, Wrapper<TSellerCommissionDateRange> wrapper){
        QueryToWrapper<TSellerCommissionDateRange> queryToWrapper = new QueryToWrapper<TSellerCommissionDateRange>();
        queryToWrapper.parseCondition(wrapper, queryable);
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TSellerCommissionDateRange> page = new com.baomidou.mybatisplus.plugins.Page<TSellerCommissionDateRange>(pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectSellerCommissionRangePageList(page, wrapper));
        return new PageImpl<TSellerCommissionDateRange>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }

    @Override
    public TSellerCommissionDateRange selectSellerCommissionDateRangeById(String id){
        return baseMapper.selectSellerCommissionRangeById(id);
    }

    @Override
    public void updateTSellerCommissionDateRange(TSellerCommissionDateRange obj){
       int updateNum= baseMapper.updateSellerCommissionRangeById(obj);
       if(updateNum != 1){
            throw new MyProcessException("数据更新失败");
       }
    }

    @Override
    public void addTSellerCommissionDateRange(TSellerCommissionDateRange obj){
        int updateNum= baseMapper.updateSellerCommissionRangeById(obj);
        if(updateNum != 1){
            throw new MyProcessException("数据保存失败");
        }
    }
}