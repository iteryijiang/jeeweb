package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TshopBase;
import cn.jeeweb.web.ebp.shop.entity.TtaskFee;
import cn.jeeweb.web.ebp.shop.mapper.TshopBaseMapper;
import cn.jeeweb.web.ebp.shop.mapper.TtaskFeeMapper;
import cn.jeeweb.web.ebp.shop.service.TshopBaseService;
import cn.jeeweb.web.ebp.shop.service.TtaskFeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("tshopBaseService")
public class TshopBaseServiceImpl extends CommonServiceImpl<TshopBaseMapper, TshopBase> implements TshopBaseService {

}
