package cn.jeeweb.web.ebp.finance.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRecharge;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;

public interface TfinanceRechargeService extends ICommonService<TfinanceRecharge> {

    public final static String rechargetype_1 = "1";//支付充值
    public final static String rechargetype_2 = "2";//任务发布
    public final static String rechargetype_3 = "3";//支付撤销
    public final static String rechargetype_4 = "4";//任务撤销
    public final static String rechargetype_5 = "5";//平台手动确认收货

    public boolean addTfinanceRecharge(TshopInfo si, TfinanceRecharge tr);

    /**
     * 撤销充值操作
     *
     * @param si：商户对象
     * @param tr：原始记录的充值对象
     * @return
     */
    public boolean updateTfinanceRechargeForRevoke(TshopInfo si,TfinanceRecharge tr);
}
