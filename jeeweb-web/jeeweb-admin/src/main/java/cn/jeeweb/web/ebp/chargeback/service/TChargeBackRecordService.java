package cn.jeeweb.web.ebp.chargeback.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyer;
import cn.jeeweb.web.ebp.chargeback.entity.CanChargeBackTask;
import cn.jeeweb.web.ebp.chargeback.entity.TChargeBackRecord;
import com.baomidou.mybatisplus.mapper.Wrapper;

public interface TChargeBackRecordService extends ICommonService<TChargeBackRecord> {

    /**
     * 增加退单记录
     *
     * @param obj
     * @throws RuntimeException
     */
    void addTChargeBackRecord(TChargeBackRecord obj)throws RuntimeException;

    /**
     * 分页获取允许退单的额任务单列表
     *
     * @param queryable
     * @param wrapper
     * @return
     */
    Page<CanChargeBackTask> selectCanChargeBackTaskPageList(Queryable queryable, Wrapper<CanChargeBackTask> wrapper);

    /**
     * 列表查询已经退单的任务单数据
     *
     * @param queryable
     * @param wrapper
     * @return
     */
    Page<TChargeBackRecord> selectChargeBackRecordPageList(Queryable queryable, Wrapper<TChargeBackRecord> wrapper);
}
