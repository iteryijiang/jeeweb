package cn.jeeweb.web.ebp.logistics.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShow;
import cn.jeeweb.web.ebp.logistics.mapper.TShopOrderShowMapper;
import cn.jeeweb.web.ebp.logistics.service.TShopOrderShowService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统参数设置
 *
 */
@Transactional
@Service("shopOrderShowService")
public class TShopOrderShowServiceImpl extends CommonServiceImpl<TShopOrderShowMapper, TShopOrderShow> implements TShopOrderShowService {

    @Override
    public Page<TShopOrderShow> selectTShopOrderShowPageList(Queryable queryable, Wrapper<TShopOrderShow> wrapper) {
        QueryToWrapper<TShopOrderShow> queryToWrapper = new QueryToWrapper<TShopOrderShow>();
        queryToWrapper.parseCondition(wrapper, queryable);
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TShopOrderShow> page = new com.baomidou.mybatisplus.plugins.Page<TShopOrderShow>(pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectTShopOrderShowPageList(page, wrapper));
        return new PageImpl<TShopOrderShow>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }
}