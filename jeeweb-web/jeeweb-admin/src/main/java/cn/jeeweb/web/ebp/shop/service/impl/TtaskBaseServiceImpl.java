package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.mapper.TtaskBaseMapper;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
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

    public Map sumNumAndPrice(String createby, String createDate1, String createDate2,String shopname,String tTitle, String status){
        return baseMapper.sumNumAndPrice(createby,createDate1,createDate2,shopname,tTitle,status);
    }


    @Override
    public Page<TtaskBase> selectPage(Page<TtaskBase> page, Wrapper<TtaskBase> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectShopList(page, wrapper));
        return page;
    }

    public List<Map> selectFinanceList( String createDate1, String createDate2) {
        return baseMapper.selectFinanceList(createDate1,createDate2);
    }
    public List<Map> selectWithdrawalMoneyList( String createDate1, String createDate2,int multiple) {
        return baseMapper.selectWithdrawalMoneyList(createDate1,createDate2,multiple);
    }
}
