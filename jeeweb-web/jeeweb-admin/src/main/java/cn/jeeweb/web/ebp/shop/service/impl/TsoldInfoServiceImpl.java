package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.exception.MyProcessException;
import cn.jeeweb.web.ebp.seller.entity.TSellerInfo;
import cn.jeeweb.web.ebp.seller.entity.TSellerLevel;
import cn.jeeweb.web.ebp.seller.service.TSellerCommissionPowerService;
import cn.jeeweb.web.ebp.shop.entity.TsoldInfo;
import cn.jeeweb.web.ebp.shop.mapper.TsoldInfoMapper;
import cn.jeeweb.web.ebp.shop.service.TsoldInfoService;
import cn.jeeweb.web.utils.UserUtils;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("TsoldInfoService")
public class TsoldInfoServiceImpl extends CommonServiceImpl<TsoldInfoMapper, TsoldInfo> implements TsoldInfoService {
    @Resource(name = "sellerCommissionPowerService")
    private TSellerCommissionPowerService sellerCommissionPowerService;

    @Override
    public Page<TSellerInfo> selectSellerInfoPageList(Queryable queryable, Wrapper<TSellerInfo> wrapper){
        QueryToWrapper<TSellerInfo> queryToWrapper = new QueryToWrapper<TSellerInfo>();
        queryToWrapper.parseCondition(wrapper, queryable);
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TSellerInfo> page = new com.baomidou.mybatisplus.plugins.Page<TSellerInfo>(pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectSellerInfoPageList(page, wrapper));
        return new PageImpl<TSellerInfo>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }

    @Override
    public TSellerInfo selectSellerInfoByUserId(String sellerUserId){
        return baseMapper.selectSellerInfoBySellerUserId(sellerUserId);
    }

    @Override
    public void updateSellerInfoLevelByUserId(String sellerUserId,String levelId){
        TSellerLevel levelObj=sellerCommissionPowerService.selectSellerLevelById(levelId);
        if(levelObj == null){
            throw  new MyProcessException("参数错误[当前等级信息不存在]");
        }
        TSellerInfo sellerObj=selectSellerInfoByUserId(sellerUserId);
        if(sellerObj == null){
            throw  new MyProcessException("参数错误[当前销售信息不存在]");
        }
        if(levelObj.getId().equals(sellerObj.getAccountlevel())){
            throw  new MyProcessException("参数错误[当前销售等级未发生变化]");
        }
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("sellerUserId",sellerUserId);
        paramMap.put("levelId",levelId);
        paramMap.put("updateBy", UserUtils.getUser().getId());
        paramMap.put("updateTime", DateUtils.getCurrentTime());
        int updateNum=baseMapper.updateSellerInfoLevelByUserId(paramMap);
        if(updateNum != 1){
            throw  new MyProcessException("操作失败");
        }
    }
}
