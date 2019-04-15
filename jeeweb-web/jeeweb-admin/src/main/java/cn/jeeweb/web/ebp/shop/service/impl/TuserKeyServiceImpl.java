package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TuserKey;
import cn.jeeweb.web.ebp.shop.mapper.TuserKeyMapper;
import cn.jeeweb.web.ebp.shop.service.TuserKeyService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Transactional
@Service("TuserKeyService")
public class TuserKeyServiceImpl extends CommonServiceImpl<TuserKeyMapper, TuserKey> implements TuserKeyService {


    @Override
    public Page<TuserKey> selectPage(Page<TuserKey> page, Wrapper<TuserKey> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectUserList(page, wrapper));
        return page;
    }

    public Integer sumTtaskBase(Map m){
        return baseMapper.sumTuserKey(m);
    }
}
