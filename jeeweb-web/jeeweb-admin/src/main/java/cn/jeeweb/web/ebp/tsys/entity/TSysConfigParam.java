package cn.jeeweb.web.ebp.tsys.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * 销售佣金对应的时间区段
 *
 * @author ytj
 */
@Data
@TableName("t_sys_config_param")
@SuppressWarnings("serial")
public class TSysConfigParam extends DataEntity<Long> {

    @TableId(value = "id", type = IdType.AUTO)
    private long id;


    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 参数数值
     */
    private String paramValue;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
