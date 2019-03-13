package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.shop.entity.TshopRoleUser;

import java.util.List;
import java.util.Map;

public interface TshopRoleUserService extends ICommonService<TshopRoleUser> {
    public List<Map> listSoldFinance(Map map);
    public Map sumListSoldFinance(Map map);
}
