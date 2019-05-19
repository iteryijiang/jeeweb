package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.mapper.TshopInfoMapper;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("TshopInfoService")
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

    @Override
    public void updateShopMoney(String shopId, BigDecimal taskDeposit, BigDecimal AvailableDeposit, String lastRepair){
        Map<String,Object> paramMap=new HashMap<String,Object>();
        paramMap.put("shopId",shopId);
        paramMap.put("taskDeposit",taskDeposit);
        paramMap.put("AvailableDeposit",AvailableDeposit);
        paramMap.put("lastRepair",lastRepair);
        paramMap.put("lastTime", Calendar.getInstance().getTime());
        int num= baseMapper.updateShopMoney(paramMap);
        if(num !=1){
            throw  new RuntimeException("更改商户余额失败!");
        }
    }

    @Override
    public Page<TshopInfo> selectPage(Page<TshopInfo> page, Wrapper<TshopInfo> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectUserList(page, wrapper));
        return page;
    }
}
