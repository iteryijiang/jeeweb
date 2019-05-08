package cn.jeeweb.web.ebp.buyer.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyer;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 买手提出异常申请
 *
 */
public interface TapplyTaskBuyerService extends ICommonService<TapplyTaskBuyer> {

  @Transactional
  void insertTapplyTaskBuyer(TapplyTaskBuyer obj);

  /**
   * 更改申请状态
   *
   * @param obj
   */
  @Transactional
  void updateTapplyTaskBuyerStatus(TapplyTaskBuyer obj);

  /**
   * 分业查询申请数据
   *
   * @param queryable
   * @param wrapper
   * @return
   */
  Page<TapplyTaskBuyer> selectApplyPageList(Queryable queryable, Wrapper<TapplyTaskBuyer> wrapper);

}
