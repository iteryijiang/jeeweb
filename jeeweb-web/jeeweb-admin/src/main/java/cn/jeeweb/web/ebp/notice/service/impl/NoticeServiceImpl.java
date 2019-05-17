package cn.jeeweb.web.ebp.notice.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyer;
import cn.jeeweb.web.ebp.notice.entity.NoticeInfo;
import cn.jeeweb.web.ebp.notice.enums.NoticeStatusEnum;
import cn.jeeweb.web.ebp.notice.mapper.NoticeMapper;
import cn.jeeweb.web.ebp.notice.service.NoticePushService;
import cn.jeeweb.web.ebp.notice.service.NoticeService;
import cn.jeeweb.web.utils.UserUtils;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0.1
 * @Author ytj
 * @Date 2019/5/14
 * @Description
 */
@Transactional
@Service("noticeService")
public class NoticeServiceImpl extends CommonServiceImpl<NoticeMapper, NoticeInfo> implements NoticeService{

  @Autowired
  private NoticePushService noticePushService;

  @Override
  public void addNoticeForBuyerUnusualTask(String buyer,String shopId,String admin, String taskNo) throws RuntimeException {
    String msgContent="买手[]对任务单号[]有异常反馈申请";
    NoticeInfo obj=new NoticeInfo();
    insert(obj);
    noticePushService.pushNotice(shopId,msgContent);
    noticePushService.pushNotice(admin,msgContent);
  }

  @Override
  public void addNoticeForAdminRevokeTask(String shopId, List<String> buyerList, String taskNo, String msgContent) throws RuntimeException {
    List<NoticeInfo> insertList=new ArrayList<NoticeInfo>();
    for(String buyer:buyerList){
      NoticeInfo obj=new NoticeInfo();
      insertList.add(obj);
    }
    insertBatch(insertList);
    buyerList.add(shopId);
    noticePushService.pushNotice(buyerList,msgContent);
  }

  @Override
  public void addNoticeForAdminTurnDownTask(String buyer, String taskNo, String msgContent) throws RuntimeException {
    NoticeInfo obj=new NoticeInfo();
    insert(obj);
    noticePushService.pushNotice(buyer,msgContent);
  }

  @Override
  public void updateNoticeStatus(long noticeId,int noticeStatus, String lastRepair) throws RuntimeException {
    Map<String,Object> paramMap=new HashMap<String,Object>();
    paramMap.put("noticeId",noticeId);
    paramMap.put("noticeStatus",noticeStatus);
    paramMap.put("lastRepair",lastRepair);
    paramMap.put("lastTime", DateUtils.getDateTime());
    baseMapper.updateNoticeStatus(paramMap);
    //不需要使用返回值校验状态
  }

  @Override
  public Page<NoticeInfo> getPageNoticeList(Queryable queryable, Wrapper<NoticeInfo> wrapper) throws RuntimeException{
    QueryToWrapper<NoticeInfo> queryToWrapper = new QueryToWrapper<NoticeInfo>();
    queryToWrapper.parseCondition(wrapper, queryable);
    queryToWrapper.parseSort(wrapper, queryable);
    Pageable pageable = queryable.getPageable();
    com.baomidou.mybatisplus.plugins.Page<NoticeInfo> page = new com.baomidou.mybatisplus.plugins.Page<NoticeInfo>(pageable.getPageNumber(), pageable.getPageSize());
    wrapper.eq("1", "1");
    page.setRecords(baseMapper.selectNoticePageList(page, wrapper));
    return new PageImpl<NoticeInfo>(page.getRecords(), queryable.getPageable(), page.getTotal());
  }

  @Override
  public NoticeInfo getNoticeById(Long id) throws RuntimeException{
    NoticeInfo obj=selectById(id);
    //消息未读取,需要标记为已经读取
    if(NoticeStatusEnum.UNREAD.code == obj.getNoticeStatus()){
      updateNoticeStatus(id,NoticeStatusEnum.READ.code, UserUtils.getUser().getId());
    }
    return obj;
  }
}
