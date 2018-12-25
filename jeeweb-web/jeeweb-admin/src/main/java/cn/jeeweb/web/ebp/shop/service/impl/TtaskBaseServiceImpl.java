package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.mapper.TtaskBaseMapper;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("ttaskBaseService1")
public class TtaskBaseServiceImpl extends CommonServiceImpl<TtaskBaseMapper, TtaskBase> implements TtaskBaseService {

    public List<TtaskBase> selectShopTask(String shopid,int count){
        return baseMapper.selectShopTask(shopid,count);
    }

}
