package cn.jeeweb.web.ebp.finance.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRecharge;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import cn.jeeweb.web.ebp.finance.mapper.TfinanceRechargeMapper;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeLogService;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Transactional
@Service("TfinanceRechargeService")
public class TfinanceRechargeServiceImpl extends CommonServiceImpl<TfinanceRechargeMapper, TfinanceRecharge> implements TfinanceRechargeService {

    @Autowired
    private TshopInfoService tshopInfoService;
    @Autowired
    private TfinanceRechargeLogService tfinanceRechargeLogService;

    @Override
    public Page<TfinanceRecharge> selectPage(Page<TfinanceRecharge> page, Wrapper<TfinanceRecharge> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectUserList(page, wrapper));
        return page;
    }

    public boolean addTfinanceRecharge(TshopInfo si,TfinanceRecharge tr){
        insert(tr);
        tshopInfoService.updateById(si);
        TfinanceRechargeLog log = new TfinanceRechargeLog(tr.getShopid(),TfinanceRechargeService.rechargetype_1,tr.getRechargedeposit(),si.getAvailabledeposit());
        tfinanceRechargeLogService.insert(log);
        return true;
    }

    @Override
    public boolean updateTfinanceRechargeForRevoke(TshopInfo si,TfinanceRecharge tr){
        //更改充值记录的撤销状态
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("id",tr.getId());
        paramMap.put("lastRepair",tr.getUpdateBy().getUsername());
        paramMap.put("lastTime",tr.getUpdateDate());
        int updateResult=baseMapper.updateTfinanceRechargeForRevoke(paramMap);
        if(updateResult != 1){
            throw  new RuntimeException("操作失败[更新充值记录状态失败]");
        }
        //更新商户的可用余额信息
        tshopInfoService.updateById(si);
        //添加重置明细信息
        TfinanceRechargeLog log = new TfinanceRechargeLog(tr.getShopid(),TfinanceRechargeService.rechargetype_3,tr.getRechargedeposit(),si.getAvailabledeposit());
        log.setRechargeId(tr.getId());
        tfinanceRechargeLogService.insert(log);
        return true;
    }
}
