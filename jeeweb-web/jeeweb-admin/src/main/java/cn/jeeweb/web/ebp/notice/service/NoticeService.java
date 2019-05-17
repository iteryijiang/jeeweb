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
   * 买手发布异常任务订单提醒
   *
   * @param buyer
   * @param shopId
   * @param admin
   * @param taskNo
   * @throws RuntimeException
   */
  void addNoticeForBuyerUnusualTask(String buyer, String shopId, String admin, String taskNo) throws RuntimeException;

  //void addNoticeShop() throws RuntimeException;

  /***
   * 管理员做撤销订单提醒
   *
   * @param shopId
   * @param buyerList
   * @param taskNo
   * @param msgContent
   * @throws RuntimeException
   */
  void addNoticeForAdminRevokeTask(String shopId, List<String> buyerList, String taskNo, String msgContent) throws RuntimeException;

  /**
   * 管理员做申请驳回提醒
   *
   * @param buyer
   * @param taskNo
   * @param msgContent
   * @throws RuntimeException
   */
  void addNoticeForAdminTurnDownTask(String buyer, String taskNo, String msgContent) throws RuntimeException;

  /**
   * 更改消息的额状态
   *
   * @param noticeId
   * @param noticeStatus
   * @param lastRepair
   * @throws RuntimeException
   */
    void updateNoticeStatus(long noticeId, int noticeStatus, String lastRepair) throws RuntimeException;

  /**
   * 分页查询列表消息
   *
   * @param queryable
   * @param wrapper
   * @return
   * @throws RuntimeException
   */
    Page<NoticeInfo> getPageNoticeList(Queryable queryable, Wrapper<NoticeInfo> wrapper) throws RuntimeException;

  /**
   * 读取单个消息
   *
   * @param id
   * @return
   * @throws RuntimeException
   */
    NoticeInfo getNoticeById(Long id) throws RuntimeException;
}
