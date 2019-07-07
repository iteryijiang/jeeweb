package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TtaskAmount;
import cn.jeeweb.web.ebp.shop.mapper.TtaskAmountMapper;
import cn.jeeweb.web.ebp.shop.service.TtaskAmountService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Transactional
@Service("TtaskAmountService")
public class TtaskAmountServiceImpl extends CommonServiceImpl<TtaskAmountMapper, TtaskAmount> implements TtaskAmountService {

    @Override
    public Page<TtaskAmount> selectPage(Page<TtaskAmount> page, Wrapper<TtaskAmount> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selecTtaskAmountList(page, wrapper));
        return page;
    }


    public List<TtaskAmount> selectPictureList(Map map) {
        return baseMapper.selectPictureList(map);
    }
}
