package cn.jeeweb.web.ebp.buyer.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerCommissionRecord;

/**
 * 买手佣金记录
 * 每天定时任务生成
 * 
 * @author ytj
 *
 */
@Mapper
public interface TBuyerCommissionRecordMapper extends BaseMapper<TBuyerCommissionRecord> {
}
