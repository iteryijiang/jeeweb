package cn.jeeweb.web.ebp.finance.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.finance.entity.TfinanceTaskDoubleReport;
import cn.jeeweb.web.ebp.finance.mapper.TfinanceTaskDoubleReportMapper;
import cn.jeeweb.web.ebp.finance.service.TfinanceTaskDoubleReportService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
@Service("TfinanceTaskDoubleReportService")
public class TfinanceTaskDoubleReportServiceImpl extends CommonServiceImpl<TfinanceTaskDoubleReportMapper, TfinanceTaskDoubleReport> implements TfinanceTaskDoubleReportService {

    @Autowired
    private TshopInfoService tshopInfoService;

    @Override
    public Page<TfinanceTaskDoubleReport> selectPage(Page<TfinanceTaskDoubleReport> page, Wrapper<TfinanceTaskDoubleReport> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectUserList(page, wrapper));
        return page;
    }

    public Integer sumTfinanceTaskDoubleReport(Map m){
        return baseMapper.sumTfinanceTaskDoubleReport(m);
    }
    public Map sumTaskNum(Map m){
        return baseMapper.sumTaskNum(m);
    }
}
