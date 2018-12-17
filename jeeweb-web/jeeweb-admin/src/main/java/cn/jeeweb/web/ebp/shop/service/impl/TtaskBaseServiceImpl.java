package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.mapper.TtaskBaseMapper;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("ttaskBaseService")
public class TtaskBaseServiceImpl extends CommonServiceImpl<TtaskBaseMapper, TtaskBase> implements TtaskBaseService {

}
