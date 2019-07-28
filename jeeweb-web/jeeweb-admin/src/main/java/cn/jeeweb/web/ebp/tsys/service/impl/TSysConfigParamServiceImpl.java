package cn.jeeweb.web.ebp.tsys.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.tsys.entity.TSysConfigParam;
import cn.jeeweb.web.ebp.tsys.mapper.TSysConfigParamMapper;
import cn.jeeweb.web.ebp.tsys.service.TSysConfigParamService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统参数设置
 *
 */
@Transactional
@Service("sysConfigParamService")
public class TSysConfigParamServiceImpl extends CommonServiceImpl<TSysConfigParamMapper, TSysConfigParam> implements TSysConfigParamService {

    @Override
    public Page<TSysConfigParam> selectTSysConfigParamPageList(Queryable queryable, Wrapper<TSysConfigParam> wrapper) {
        QueryToWrapper<TSysConfigParam> queryToWrapper = new QueryToWrapper<TSysConfigParam>();
        queryToWrapper.parseCondition(wrapper, queryable);
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TSysConfigParam> page = new com.baomidou.mybatisplus.plugins.Page<TSysConfigParam>(pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectTSysConfigParamPageList(page, wrapper));
        return new PageImpl<TSysConfigParam>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }

    @Override
    public TSysConfigParam selectTSysConfigById(String id) {
        return baseMapper.selectTSysConfigById(id);
    }

    @Override
    public TSysConfigParam selectTSysConfigByConfigParam(String configParam) {
        return baseMapper.selectTSysConfigByConfigParam(configParam);
    }

    @Override
    public void insertTSysConfigParam(TSysConfigParam obj) {
        baseMapper.insertTSysConfigParam(obj)
        ;
    }


    @Override
    public void updateTSysConfigParam(TSysConfigParam obj) {
        baseMapper.insertTSysConfigParam(obj)
        ;
    }
}