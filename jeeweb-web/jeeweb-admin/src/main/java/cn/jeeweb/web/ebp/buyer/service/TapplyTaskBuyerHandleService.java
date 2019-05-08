package cn.jeeweb.web.ebp.buyer.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyerHandle;
import org.springframework.transaction.annotation.Transactional;

/**
 * 买手提出异常申请
 *
 */
public interface TapplyTaskBuyerHandleService extends ICommonService<TapplyTaskBuyerHandle> {
    /**
     * 买手异常订单处理
     *
     * @param obj
     */
    @Transactional
    void updateTapplyTaskBuyerForHandle(TapplyTaskBuyerHandle obj) throws Exception;
}
