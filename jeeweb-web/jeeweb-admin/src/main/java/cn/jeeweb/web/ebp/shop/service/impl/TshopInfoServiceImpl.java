package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.mapper.TshopInfoMapper;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("TshopInfoService1")
public class TshopInfoServiceImpl extends CommonServiceImpl<TshopInfoMapper, TshopInfo> implements TshopInfoService {

}
