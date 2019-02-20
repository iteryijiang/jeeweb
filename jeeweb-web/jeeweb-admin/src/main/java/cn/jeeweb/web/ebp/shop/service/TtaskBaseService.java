package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;

import java.util.List;
import java.util.Map;

public interface TtaskBaseService extends ICommonService<TtaskBase> {

    public List<TtaskBase> selectShopTask(String shopid,int count,String user,int minute);

    public Map sumNumAndPrice(String createby, String createDate1, String createDate2,String shopname,String tTitle, String status);

    public List<Map> selectFinanceList( String createDate1, String createDate2);

    public List<Map> selectWithdrawalMoneyList( String createDate1, String createDate2,int multiple);
    public List<Map> listFinanceShopReport(Map map);
}
