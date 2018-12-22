package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;
import cn.jeeweb.web.ebp.buyer.mapper.TbuyerInfoMapper;
import cn.jeeweb.web.ebp.buyer.service.TbuyerInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("TbuyerInfoService")
public class TbuyerInfoServiceImpl extends CommonServiceImpl<TbuyerInfoMapper, TbuyerInfo> implements TbuyerInfoService {

}
