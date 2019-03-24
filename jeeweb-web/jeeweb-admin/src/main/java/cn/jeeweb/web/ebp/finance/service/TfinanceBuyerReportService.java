package cn.jeeweb.web.ebp.finance.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.finance.entity.TfinanceBuyerReport;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;

import java.util.Map;

public interface TfinanceBuyerReportService extends ICommonService<TfinanceBuyerReport> {


    public boolean addTfinanceBuyerReport(TshopInfo si, TfinanceBuyerReport tr);

    public boolean delTfinanceBuyerReport(TshopInfo si, TfinanceBuyerReport tr);

    public Map showBuyerReportLoad(Map m);
}
