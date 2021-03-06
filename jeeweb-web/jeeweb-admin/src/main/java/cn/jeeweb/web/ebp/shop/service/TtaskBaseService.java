package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TtaskAmount;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.entity.TtaskPictureComment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TtaskBaseService extends ICommonService<TtaskBase> {

    public List<TtaskBase> selectShopTask(String shopid,int count,String user,int minute);
    public List<TtaskBase> selectShopTaskNew(int minute);

    public Map sumNumAndPrice(Map m);

    public Map showTaskBaseLoadFinance(Map m);

    public Integer sumTtaskBase(Map m);

    public List<Map> selectFinanceList( String createDate1, String createDate2);

    public List<Map> selectWithdrawalMoneyList(Map map);
    public List<Map> listFinanceShopReport(Map map);

    public Map shopMap(List<TtaskBase> list_tb,int count,int countSum,Map<Integer,List<TtaskBase>> m,int i);
    /*历史随机领取任务单**/
    public void myTaskCreate();

    public Map myTaskMap(int count,int minute);

    /**
     * 新的根据店铺组单后，领取任务单
     * */
    public boolean createMyTask() throws Exception;

    /**
     * 任务发布
     * */
    public boolean addTask(TtaskBase ttaskBase, Map<String,Object> paramMap, List<TtaskAmount> list, List<TtaskPictureComment> busLineList);

    /**
     * 任务状态修改
     * */
    public boolean upTask(TtaskBase ttaskBase, TshopInfo si,String rechargetype,BigDecimal price,Map<String,Object> paramMap);

}
