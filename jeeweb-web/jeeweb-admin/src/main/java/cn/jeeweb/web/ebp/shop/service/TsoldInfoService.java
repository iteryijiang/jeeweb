package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
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
    Page<TsoldInfo> selectSellerInfoPageList(Queryable queryable, Wrapper<TsoldInfo> wrapper);

    /**
     * 根据销售人员的用户编号获取销售人员信息
     *
     * @param sellerUserId
     * @return
     */
    TsoldInfo selectSellerInfoByUserId(String sellerUserId);

    /***
     * 主键ID获取数据
     *
     * @param id
     * @return
     */
    TsoldInfo selectSellerInfoById(String id);

    /**
     * 更改用户等级信息
     *
     * @param id
     * @param levelId
     */
    void updateSellerInfoLevelById(String id,String levelId);
}
