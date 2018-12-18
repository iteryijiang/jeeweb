package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TtaskFee;
import cn.jeeweb.web.ebp.shop.mapper.TtaskFeeMapper;
import cn.jeeweb.web.ebp.shop.service.TtaskFeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("ttaskBaseService")
public class TtaskFeeServiceImpl extends CommonServiceImpl<TtaskFeeMapper, TtaskFee> implements TtaskFeeService {

}
