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
     * 开始日期
     */
    private String beginDate;
    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 京东单号
     */
    private String jdOrderNo;
    /**
     * 买手任务单号
     */
    private String buyerTaskNo;
    /**
     * 买手编号
     */
    private String buyerNo;
    /**
     * 当前页码
     */
    private int currentPage;
    /**
     * 页码大小
     */
    private int pageSize;
    /**
     * 检索状态
     */
    private int taskStatus;
    /**
     * 商户编号
     */
    private String shopUserId;

}
