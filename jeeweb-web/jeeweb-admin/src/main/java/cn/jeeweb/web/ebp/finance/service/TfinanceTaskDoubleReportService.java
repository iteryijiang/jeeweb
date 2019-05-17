package cn.jeeweb.web.ebp.finance.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.finance.entity.TfinanceTaskDoubleReport;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;

import java.util.Map;

public interface TfinanceTaskDoubleReportService extends ICommonService<TfinanceTaskDoubleReport> {

    public Integer sumTfinanceTaskDoubleReport(Map m);

    public Map sumTaskNum(Map m);

}
