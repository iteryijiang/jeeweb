package cn.jeeweb.web.ebp.notice.service;

import java.util.List;

/**
 * @version v1.0.1
 * @Author ytj
 * @Date 2019/5/15
 * @Description
 */
public interface NoticePushService {

  /**
   * 推送单个消息
   *
   * @param receiver
   * @param noticeContent
   */
  void pushNotice(String receiver, String noticeContent);

  /**
   * 给某个团体下的成员推送数据
   *
   * @param groupId
   * @param memberList
   * @param noticeContent
   */
  void pushNotice(String groupId, List<String> memberList, String noticeContent);

  /***
   * 给团队下的某个成员发消息
   *
   * @param groupId
   * @param memberNo
   * @param noticeContent
   */
  void pushNotice(String groupId, String memberNo, String noticeContent);

  /**
   * 多人推送同一条消息
   *
   * @param receiverList
   * @param noticeContent
   */
  void pushNotice(List<String> receiverList, String noticeContent);
}
