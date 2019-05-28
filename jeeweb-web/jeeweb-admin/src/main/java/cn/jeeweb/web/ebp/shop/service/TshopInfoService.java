package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TshopInfoService extends ICommonService<TshopInfo> {

    public List<TshopInfo> findshopInfo();

    /**
     * 关键字查询商户信息
     *
     * @param keyWord
     * @return
     */
    List<TshopInfo> findShopInfoByKeyWord(String keyWord);

    public TshopInfo  selectOne(String userid);

    public Map selectSumOne(Map m);
    /**
     * 更改商户可用金额信息
     *
     * @param shopId
     * @param taskDeposit
     * @param AvailableDeposit
     * @param lastRepair
     */
    @Transactional
    void updateShopMoney(String shopId, BigDecimal taskDeposit, BigDecimal AvailableDeposit, String lastRepair);
}
