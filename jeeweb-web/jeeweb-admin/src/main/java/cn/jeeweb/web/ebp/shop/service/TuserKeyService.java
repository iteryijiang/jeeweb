package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.shop.entity.TuserKey;

import java.util.List;
import java.util.Map;

public interface TuserKeyService extends ICommonService<TuserKey> {
    public Integer sumTtaskBase(Map m);
}
