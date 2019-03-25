package cn.jeeweb.web.ebp.finance.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRecharge;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;

public interface TfinanceRechargeService extends ICommonService<TfinanceRecharge> {

    public final static String rechargetype_1 = "1";//支付充值
    public final static String rechargetype_2 = "2";//任务发布
    public final static String rechargetype_3 = "3";//支付撤销
    public final static String rechargetype_4 = "4";//任务撤销

    public boolean addTfinanceRecharge(TshopInfo si, TfinanceRecharge tr);

    public boolean delTfinanceRecharge(TshopInfo si,TfinanceRecharge tr);
}
