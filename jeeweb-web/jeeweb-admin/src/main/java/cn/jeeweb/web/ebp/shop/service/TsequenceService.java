package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.shop.entity.Tsequence;

public interface TsequenceService extends ICommonService<Tsequence> {
    public long nextNum(String bhkey) throws Exception ;
}
