package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TshopBase;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.entity.TtaskFee;
import cn.jeeweb.web.ebp.shop.mapper.TshopBaseMapper;
import cn.jeeweb.web.ebp.shop.mapper.TtaskFeeMapper;
import cn.jeeweb.web.ebp.shop.service.TshopBaseService;
import cn.jeeweb.web.ebp.shop.service.TtaskFeeService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("tshopBaseService")
public class TshopBaseServiceImpl extends CommonServiceImpl<TshopBaseMapper, TshopBase> implements TshopBaseService {

    @Override
    public Page<TshopBase> selectPage(Page<TshopBase> page, Wrapper<TshopBase> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectShopList(page, wrapper));
        return page;
    }
}
