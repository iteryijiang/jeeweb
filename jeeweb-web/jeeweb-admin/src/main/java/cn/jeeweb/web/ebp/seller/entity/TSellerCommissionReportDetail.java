package cn.jeeweb.web.ebp.seller.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售佣金每个区间数量
 *
 * @author ytj
 */
@TableName("t_seller_commission_report_detail")
@SuppressWarnings("serial")
public class TSellerCommissionReportDetail extends DataEntity<String> {

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 佣金账期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date atime;
    /**
     * 数据来源
     * 1每天佣金生成2月底退单佣金
     */
    private int dataType=1;
    /**
     * 所属月份
     */
    private int dataMonth;
    /**
     * 销售ID
     */
    private String sellerId;
    /**
     * 生成佣金记录时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date dtime;

    /**
     * 生成佣金记录时销售等级id
     */
    private String sellerLevel;
    /**
     * 总的销售佣金
     */
    private String dateRangeId;
    /**
     * 相差天数
     */
    private long date_diff = 0;
    /**
     * 任务数量
     */
    private long taskNum = 0;

    /***
     * 生成记录时的任务数量
     *
     */
    private long taskNumInit = 0;
    /**
     * 销售佣金金额
     */
    private BigDecimal totalCommission = BigDecimal.ZERO;
    /**
     * 初始销售佣金金额
     */
    private BigDecimal totalCommissionInit = BigDecimal.ZERO;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Date getAtime() {
        return atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }

    public int getDataMonth() {
        return dataMonth;
    }

    public void setDataMonth(int dataMonth) {
        this.dataMonth = dataMonth;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Date getDtime() {
        return dtime;
    }

    public void setDtime(Date dtime) {
        this.dtime = dtime;
    }

    public String getSellerLevel() {
        return sellerLevel;
    }

    public void setSellerLevel(String sellerLevel) {
        this.sellerLevel = sellerLevel;
    }

    public String getDateRangeId() {
        return dateRangeId;
    }

    public void setDateRangeId(String dateRangeId) {
        this.dateRangeId = dateRangeId;
    }

    public long getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(long taskNum) {
        this.taskNum = taskNum;
    }

    public long getTaskNumInit() {
        return taskNumInit;
    }

    public void setTaskNumInit(long taskNumInit) {
        this.taskNumInit = taskNumInit;
    }

    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }

    public BigDecimal getTotalCommissionInit() {
        return totalCommissionInit;
    }

    public void setTotalCommissionInit(BigDecimal totalCommissionInit) {
        this.totalCommissionInit = totalCommissionInit;
    }

    public long getDate_diff() {
        return date_diff;
    }

    public void setDate_diff(long date_diff) {
        this.date_diff = date_diff;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
