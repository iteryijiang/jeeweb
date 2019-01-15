package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.mapper.TtaskBaseMapper;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("ttaskBaseService1")
public class TtaskBaseServiceImpl extends CommonServiceImpl<TtaskBaseMapper, TtaskBase> implements TtaskBaseService {

    public List<TtaskBase> selectShopTask(String shopid,int count,String user){
        return baseMapper.selectShopTask(shopid,count,user);
    }

    public Map sumNumAndPrice(String createby, String createDate1, String createDate2){
        return baseMapper.sumNumAndPrice(createby,createDate1,createDate2);
    }


    @Override
    public Page<TtaskBase> selectPage(Page<TtaskBase> page, Wrapper<TtaskBase> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectShopList(page, wrapper));
        return page;
    }

}
