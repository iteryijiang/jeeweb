package cn.jeeweb.web.modules.oa.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
import cn.jeeweb.web.modules.oa.entity.OaNotification;
import cn.jeeweb.web.modules.oa.mapper.OaNotificationMapper;
import cn.jeeweb.web.modules.oa.service.IOaNotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**   
 * @Title: 通知公告
 * @Description: 通知公告
 * @author jeeweb
 * @date 2017-06-10 17:15:17
 * @version V1.0   
 *
 */
@Transactional
@Service("oaNotificationService")
public class OaNotificationServiceImpl  extends CommonServiceImpl<OaNotificationMapper,OaNotification> implements  IOaNotificationService {

	@Override
	public boolean insert(OaNotification obj) {
		baseMapper.insert(obj);
		pushNotice(obj);
		return true;
	}
	
	/***
	 * 公告数据保存
	 * 
	 */
	@Override
	public boolean updateById(OaNotification obj) {
		OaNotification dbObj=baseMapper.selectById(obj.getId());
		if(dbObj == null) {
			throw new RuntimeException("更新失败[未获到原公告信息]");
		}
		int updateNum=baseMapper.updateById(obj);
		if(updateNum != 1) {
			throw new RuntimeException("更新失败[更新记录错误]");
		}
		pushNotice(obj);
		return true;
	}
	
	/**
	 * 将小推送给当前在线用户
	 * 
	 * @param obj
	 */
	private void pushNotice(OaNotification obj) {
		if((YesNoEnum.YES.code+"").equals(obj.getStatus())) {
			System.out.println("开始推送数据");
		}
	}
}
