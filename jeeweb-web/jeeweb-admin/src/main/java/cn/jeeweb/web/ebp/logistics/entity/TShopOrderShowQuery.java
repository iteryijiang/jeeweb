package cn.jeeweb.web.ebp.logistics.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/***
 * 商户显示的订单列表字段
 *
 */
@Data
public class TShopOrderShowQuery implements Serializable {
    /**
     *
     * 商户展示订单头部信息
     */
    private TShopOrderShowTitle shopOrderShowTitle;

    /**
     * 商户展示订单明细
     *
     */
    List<TShopOrderShowData> shopOrderShowDataList;

}
