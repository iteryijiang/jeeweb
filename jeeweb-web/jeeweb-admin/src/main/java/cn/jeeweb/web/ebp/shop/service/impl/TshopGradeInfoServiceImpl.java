package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TshopGradeInfo;
import cn.jeeweb.web.ebp.shop.mapper.TshopGradeInfoMapper;
import cn.jeeweb.web.ebp.shop.service.TshopGradeInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("TshopGradeInfoService")
public class TshopGradeInfoServiceImpl extends CommonServiceImpl<TshopGradeInfoMapper, TshopGradeInfo> implements TshopGradeInfoService {

}
