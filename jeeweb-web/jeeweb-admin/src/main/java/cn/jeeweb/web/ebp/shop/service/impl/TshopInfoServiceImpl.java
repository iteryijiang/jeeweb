package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.mapper.TshopInfoMapper;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("TshopInfoService1")
public class TshopInfoServiceImpl extends CommonServiceImpl<TshopInfoMapper, TshopInfo> implements TshopInfoService {

    public List<TshopInfo> findshopInfo(){
        return baseMapper.findshopInfo();
    }

    public TshopInfo  selectOne(String userid){
        return baseMapper.selectOne(userid);
    }

    public Map selectSumOne(Map m){
        return baseMapper.selectSumOne(m);
    }
}
