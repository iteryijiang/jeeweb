package cn.jeeweb.web.ebp.notice.service;

import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.notice.entity.NoticeInfo;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.List;

/**
 * @version v1.0.1
 * @Author ytj
 * @Date 2019/5/14
 * @Description
 */
public interface NoticeService {

  /**
   * 添加消息
   *
   * @param obj
   * @throws RuntimeException
   */
  void addNotice(NoticeInfo obj);

  //void addNoticeShop() throws RuntimeException;

  /***
   * 添加消息列表
   *
   * @param objList
   * @throws RuntimeException
   */
  void addNotice(List<NoticeInfo> objList);

  /**
   * 更改消息的额状态
   *
   * @param noticeId
   * @param noticeStatus
   * @param lastRepair
   * @throws RuntimeException
   */
    void updateNoticeStatus(long noticeId, int noticeStatus, String lastRepair);

  /**
   * 分页查询列表消息
   *
   * @param queryable
   * @param wrapper
   * @return
   * @throws RuntimeException
   */
    Page<NoticeInfo> getPageNoticeList(Queryable queryable, Wrapper<NoticeInfo> wrapper);

  /**
   * 读取单个消息
   *
   * @param id
   * @return
   * @throws RuntimeException
   */
    NoticeInfo getNoticeById(Long id);
}
