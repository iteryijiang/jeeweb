package cn.jeeweb.web.ebp.chargeback.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.chargeback.entity.TChargeBackRecord;

public interface TChargeBackRecordService extends ICommonService<TChargeBackRecord> {

    /**
     * 增加退单记录
     *
     * @param obj
     * @throws RuntimeException
     */
    void addTChargeBackRecord(TChargeBackRecord obj)throws RuntimeException;
}
