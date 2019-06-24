package cn.jeeweb.web.ebp.chargeback.mapper;

import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyer;
import cn.jeeweb.web.ebp.chargeback.entity.CanChargeBackTask;
import cn.jeeweb.web.ebp.chargeback.entity.TChargeBackRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TChargeBackRecordMapper extends BaseMapper<TChargeBackRecord> {

    /**
     * 获取允许退单的任务单列表
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<CanChargeBackTask> getCanChargeBackTaskList(Pagination page, @Param("ew") Wrapper<CanChargeBackTask> wrapper);

    /**
     *获得已经退单的额任务单数据列表
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<TChargeBackRecord> getChargeBackRecordList(Pagination page, @Param("ew") Wrapper<TChargeBackRecord> wrapper);

}

