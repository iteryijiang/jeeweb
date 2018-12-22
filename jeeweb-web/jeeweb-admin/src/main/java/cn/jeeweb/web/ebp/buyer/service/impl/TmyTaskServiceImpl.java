package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.mapper.TmyTaskMapper;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("TshopInfoService")
public class TmyTaskServiceImpl extends CommonServiceImpl<TmyTaskMapper, TmyTask> implements TmyTaskService {

}
