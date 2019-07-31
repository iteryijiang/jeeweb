package cn.jeeweb.web.ebp.logistics.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * 商户任务单出库明细表
 *
 */
@TableName("t_logistics_order_detail")
@SuppressWarnings("serial")
@Data
public class TLogisticsOrderDetail extends DataEntity<String> {
    /** id */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 出库订单主表ID
     */
    private String masterId;
    /**
     * 买手任务明细ID
     */
    private String buyerTaskDetailId;

    /**
     * 出库确认状态0未出库1已出库
     *
     */
    private int status=0;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
