package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TshopRoleUser;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.mapper.TshopRoleUserMapper;
import cn.jeeweb.web.ebp.shop.service.TshopRoleUserService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("TshopRoleUserService")
public class TshopRoleUserServiceImpl extends CommonServiceImpl<TshopRoleUserMapper, TshopRoleUser> implements TshopRoleUserService {


    public List<Map> listSoldFinance(Map map) {
        return baseMapper.listSoldFinance(map);
    }
    public Map sumListSoldFinance(Map map) {
        return baseMapper.sumListSoldFinance(map);
    }

    @Override
    public Page<TshopRoleUser> selectPage(Page<TshopRoleUser> page, Wrapper<TshopRoleUser> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectShopList(page, wrapper));
        return page;
    }
}
