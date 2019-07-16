package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.seller.entity.TSellerInfo;
import cn.jeeweb.web.ebp.shop.entity.TsoldInfo;
import com.baomidou.mybatisplus.mapper.Wrapper;

public interface TsoldInfoService extends ICommonService<TsoldInfo> {

    /***
     * 分页查询销售人员信息
     *
     * @param queryable
     * @param wrapper
     * @return
     */
    Page<TSellerInfo> selectSellerInfoPageList(Queryable queryable, Wrapper<TSellerInfo> wrapper);

    /**
     * 根据销售人员的用户编号获取销售人员信息
     *
     * @param sellerUserId
     * @return
     */
    TSellerInfo selectSellerInfoByUserId(String sellerUserId);

    /**
     * 更改用户等级信息
     *
     * @param sellerUserId
     * @param levelId
     */
    void updateSellerInfoLevelByUserId(String sellerUserId,String levelId);
}
