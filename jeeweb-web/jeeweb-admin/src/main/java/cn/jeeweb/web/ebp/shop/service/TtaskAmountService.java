package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.shop.entity.TtaskAmount;

import java.util.List;
import java.util.Map;

public interface TtaskAmountService extends ICommonService<TtaskAmount> {

    public List<TtaskAmount> selectPictureList(Map map);
}
