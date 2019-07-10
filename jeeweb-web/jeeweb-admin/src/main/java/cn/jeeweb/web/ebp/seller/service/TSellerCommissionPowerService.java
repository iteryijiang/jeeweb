package cn.jeeweb.web.ebp.seller.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
import cn.jeeweb.web.ebp.seller.entity.TSellerCommissionDateRange;
import cn.jeeweb.web.ebp.seller.entity.TSellerLevel;
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
public interface TSellerCommissionPowerService extends ICommonService<TSellerLevel> {

    /**
     * 获取销售等级
     *
     * @param queryable
     * @param wrapper
     * @return
     */
    Page<TSellerLevel> selectSellerLevelPageList(Queryable queryable, Wrapper<TSellerLevel> wrapper);

    /***
     * 销售佣金区间
     *
     * @param queryable
     * @param wrapper
     * @return
     */
    Page<TSellerCommissionDateRange> selectSellerCommissionDateRangePageList(Queryable queryable, Wrapper<TSellerCommissionDateRange> wrapper);

}