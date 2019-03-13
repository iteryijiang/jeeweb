package cn.jeeweb.web.ebp.finance.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.finance.entity.TfinanceBuyerReport;
import cn.jeeweb.web.ebp.finance.mapper.TfinanceBuyerReportMapper;
import cn.jeeweb.web.ebp.finance.service.TfinanceBuyerReportService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("tfinanceBuyerReportService")
public class TfinanceBuyerReportServiceImpl extends CommonServiceImpl<TfinanceBuyerReportMapper, TfinanceBuyerReport> implements TfinanceBuyerReportService {

    @Autowired
    private TshopInfoService tshopInfoService;

    @Override
    public Page<TfinanceBuyerReport> selectPage(Page<TfinanceBuyerReport> page, Wrapper<TfinanceBuyerReport> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectUserList(page, wrapper));
        return page;
    }

    public boolean addTfinanceBuyerReport(TshopInfo si,TfinanceBuyerReport tr){
        insert(tr);
        tshopInfoService.updateById(si);
        return true;
    }

    public boolean delTfinanceBuyerReport(TshopInfo si,TfinanceBuyerReport tr){
        tshopInfoService.updateById(si);
        deleteById(tr);
        return true;
    }
}
