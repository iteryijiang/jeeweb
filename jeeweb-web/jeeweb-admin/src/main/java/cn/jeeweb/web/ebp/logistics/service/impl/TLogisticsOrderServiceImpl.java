package cn.jeeweb.web.ebp.logistics.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.enums.SysConfigParamEnum;
import cn.jeeweb.web.ebp.exception.MyProcessException;
import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrder;
import cn.jeeweb.web.ebp.logistics.mapper.TLogisticsOrderMapper;
import cn.jeeweb.web.ebp.logistics.service.TLogisticsOrderService;
import cn.jeeweb.web.ebp.tsys.entity.TSysConfigParam;
import cn.jeeweb.web.ebp.tsys.service.TSysConfigParamService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统参数设置
 *
 */
@Transactional
@Service("logisticsOrderService")
public class TLogisticsOrderServiceImpl extends CommonServiceImpl<TLogisticsOrderMapper, TLogisticsOrder> implements TLogisticsOrderService {

    @Autowired
    private TSysConfigParamService sysConfigParamService;

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
    }


    @Override
    public void updateTLogisticsOrderStatus(String id,int status) {
        TLogisticsOrder obj=selectTLogisticsOrderById(id);
        if(obj == null ){
            throw  new MyProcessException("参数错误[未获取到订单记录]");
        }
        if(obj == null ){
            throw  new MyProcessException("参数错误[该订单已经出库]");
        }
        baseMapper.updateTLogisticsOrderStatus(obj);
        //获取佣金参数
        TSysConfigParam sysConfigParamObj=sysConfigParamService.selectTSysConfigByConfigParam(SysConfigParamEnum.PLATFORM_LOGISTICS_COMMISSION.configParam);
        //扣除佣金
    }
}