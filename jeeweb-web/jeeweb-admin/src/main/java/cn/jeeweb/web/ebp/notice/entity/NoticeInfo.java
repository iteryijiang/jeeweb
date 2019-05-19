package cn.jeeweb.web.ebp.notice.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

/**
 * @version v1.0.1
 * @Author ytj
 * @Date 2019/5/14
 * @Description
 */
@TableName("t_notice_info")
@SuppressWarnings("serial")
public class NoticeInfo extends DataEntity<Long> {

  /**
   * 主键 ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  /**
   * 通知范围
   */
  private int noticeRange;
  /**
   * 通知消息内容
   */
  private String noticeInfo;
  /**
   * 消息状态
   * 0未处理1已处理
   * 针对全局的消息通知设置有效期，过了有效字自动失效
   */
  private int noticeStatus;
  /**
   * 消息的业务类型
   */
  private String noticeBiz;
  /**
   * 消息关联的业务编号
   */
  private String noticeBizNo;
  /**
   * 消息通知的等级
   */
  private int noticeLevel;
  /**
   * 消息通知的跳转链接
   */
  private String noticeLink;
  /**
   * 消息接收方
   * 与消息接收范围共同组成消息接收者
   */
  private String noticeReceive;
  /**
   * 消息持有人=>发布者的名字
   */
  private String noticeGroup;
  /**
   * 消息的有效截止时间
   */
  @JSONField(format="yyyy-MM-dd HH:mm:ss")
  private Date effectTime;

  public NoticeInfo(int noticeRange, String noticeInfo, int noticeStatus, String noticeBiz, String noticeBizNo, int noticeLevel, String noticeLink, String noticeReceive, String noticeGroup, Date effectTime) {
    this.noticeRange = noticeRange;
    this.noticeInfo = noticeInfo;
    this.noticeStatus = noticeStatus;
    this.noticeBiz = noticeBiz;
    this.noticeBizNo = noticeBizNo;
    this.noticeLevel = noticeLevel;
    this.noticeLink = noticeLink;
    this.noticeReceive = noticeReceive;
    this.noticeGroup = noticeGroup;
    this.effectTime = effectTime;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }


  public int getNoticeRange() {
    return noticeRange;
  }

  public void setNoticeRange(int noticeRange) {
    this.noticeRange = noticeRange;
  }

  public String getNoticeInfo() {
    return noticeInfo;
  }

  public void setNoticeInfo(String noticeInfo) {
    this.noticeInfo = noticeInfo;
  }

  public int getNoticeStatus() {
    return noticeStatus;
  }

  public void setNoticeStatus(int noticeStatus) {
    this.noticeStatus = noticeStatus;
  }

  public String getNoticeBiz() {
    return noticeBiz;
  }

  public void setNoticeBiz(String noticeBiz) {
    this.noticeBiz = noticeBiz;
  }

  public String getNoticeBizNo() {
    return noticeBizNo;
  }

  public void setNoticeBizNo(String noticeBizNo) {
    this.noticeBizNo = noticeBizNo;
  }

  public int getNoticeLevel() {
    return noticeLevel;
  }

  public void setNoticeLevel(int noticeLevel) {
    this.noticeLevel = noticeLevel;
  }

  public String getNoticeLink() {
    return noticeLink;
  }

  public void setNoticeLink(String noticeLink) {
    this.noticeLink = noticeLink;
  }

  public String getNoticeReceive() {
    return noticeReceive;
  }

  public void setNoticeReceive(String noticeReceive) {
    this.noticeReceive = noticeReceive;
  }

  public Date getEffectTime() {
    return effectTime;
  }

  public void setEffectTime(Date effectTime) {
    this.effectTime = effectTime;
  }

  public String getNoticeGroup() {
    return noticeGroup;
  }

  public void setNoticeGroup(String noticeGroup) {
    this.noticeGroup = noticeGroup;
  }
}
