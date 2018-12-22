package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.mapper.TmyTaskDetailMapper;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("TmyTaskDetailService")
public class TmyTaskDetailServiceImpl extends CommonServiceImpl<TmyTaskDetailMapper, TmyTaskDetail> implements TmyTaskDetailService {

}
