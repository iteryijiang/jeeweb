package cn.jeeweb.web.ebp.notice.service.impl;

import cn.jeeweb.web.ebp.notice.base.Publisher;
import cn.jeeweb.web.ebp.notice.base.PublisherFactory;
import cn.jeeweb.web.ebp.notice.entity.NoticeInfo;
import cn.jeeweb.web.ebp.notice.service.NoticePushService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version v1.0.1
 * @Author ytj
 * @Date 2019/5/15
 * @Description
 */
@Service("noticePushService")
public class NoticePushServiceImpl implements NoticePushService {

  @Override
  public void pushNotice(String receiver,String noticeContent){
    Publisher publisher = PublisherFactory.getInstance();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("noticeContent", noticeContent);
    publisher.publish(receiver, jsonObject.toString());
  }

  @Override
  public void pushNotice(String groupId,List<String> memberList,String noticeContent) {
    Publisher publisher = PublisherFactory.getInstance();
    for(String memberNo:memberList){
      pushNotice("",memberNo,noticeContent);
    }
  }

  @Override
  public void pushNotice(String groupId,String memberNo,String noticeContent){
    Publisher publisher = PublisherFactory.getInstance();
    publisher.publish("", memberNo, noticeContent);
  }

  @Override
  public void pushNotice(List<String> receiverList, String noticeContent){
    Publisher publisher = PublisherFactory.getInstance();
    for (String receiver:receiverList) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("noticeContent", noticeContent);
      publisher.publish(receiver, jsonObject.toString());
    }
  }
}
