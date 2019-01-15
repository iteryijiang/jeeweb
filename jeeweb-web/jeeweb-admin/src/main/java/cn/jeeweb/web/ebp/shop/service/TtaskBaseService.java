package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;

import java.util.List;
import java.util.Map;

public interface TtaskBaseService extends ICommonService<TtaskBase> {

    public List<TtaskBase> selectShopTask(String shopid,int count,String user);

    public Map sumNumAndPrice(String createby, String createDate1, String createDate2);
}
