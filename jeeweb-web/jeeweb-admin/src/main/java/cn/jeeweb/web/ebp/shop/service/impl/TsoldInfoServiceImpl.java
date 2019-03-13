package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TsoldInfo;
import cn.jeeweb.web.ebp.shop.mapper.TsoldInfoMapper;
import cn.jeeweb.web.ebp.shop.service.TsoldInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("TsoldInfoService")
public class TsoldInfoServiceImpl extends CommonServiceImpl<TsoldInfoMapper, TsoldInfo> implements TsoldInfoService {

}
