package cn.jeeweb.web.ebp.finance.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import cn.jeeweb.web.ebp.finance.mapper.TfinanceRechargeLogMapper;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeLogService;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("TfinanceRechargeLogService")
public class TfinanceRechargeLogServiceImpl extends CommonServiceImpl<TfinanceRechargeLogMapper, TfinanceRechargeLog> implements TfinanceRechargeLogService {

    @Override
    public Page<TfinanceRechargeLog> selectPage(Page<TfinanceRechargeLog> page, Wrapper<TfinanceRechargeLog> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectUserList(page, wrapper));
        return page;
    }
}
