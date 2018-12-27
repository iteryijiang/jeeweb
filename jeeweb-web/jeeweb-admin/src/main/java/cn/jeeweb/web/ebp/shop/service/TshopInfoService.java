package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;

import java.util.List;

public interface TshopInfoService extends ICommonService<TshopInfo> {

    public List<TshopInfo> findshopInfo();

    public TshopInfo  selectOne(String userid);

}
