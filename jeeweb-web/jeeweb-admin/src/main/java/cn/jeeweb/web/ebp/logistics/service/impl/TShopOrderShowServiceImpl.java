package cn.jeeweb.web.ebp.logistics.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.enums.SysConfigParamEnum;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShow;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowData;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowQuery;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowTitle;
import cn.jeeweb.web.ebp.logistics.mapper.TShopOrderShowMapper;
import cn.jeeweb.web.ebp.logistics.service.TShopOrderShowService;
import cn.jeeweb.web.ebp.tsys.entity.TSysConfigParam;
import cn.jeeweb.web.ebp.tsys.service.TSysConfigParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 系统参数设置
 *
 */
@Transactional
@Service("shopOrderShowService")
public class TShopOrderShowServiceImpl extends CommonServiceImpl<TShopOrderShowMapper, TShopOrderShow> implements TShopOrderShowService {
    @Autowired
    private TSysConfigParamService sysConfigParamService;
    @Override
    public Page<TShopOrderShow> selectTShopOrderShowPageList(Queryable queryable, TShopOrderShowQuery queryParam) {
        Map<String,Object> paramMap=installQueryParamMap(queryParam);
        int total=getTShopOrderShowTitleCount(paramMap);
        if(total < 1){
            return null;
        }
        TSysConfigParam sysConfigParamObj=sysConfigParamService.selectTSysConfigByConfigParam(SysConfigParamEnum.PLATFORM_LOGISTICS_COMMISSION.configParam);
        List<TShopOrderShowTitle> shopOrderShowTitleList=getTShopOrderShowTitleList(queryable.getPageable().getPageNumber(),queryable.getPageable().getPageSize(),paramMap,new BigDecimal(sysConfigParamObj.getParamValue()));
        List<TShopOrderShowData> shopOrderShowDataList=getTShopOrderShowDataList(shopOrderShowTitleList);
        List<TShopOrderShow> retRecords=installTShopOrderShow(shopOrderShowTitleList,shopOrderShowDataList);
        return new PageImpl<TShopOrderShow>(retRecords, queryable.getPageable(),total);
    }

    /**
     * 组装检索参数
     *
     * @param queryParam
     * @return
     */
    private Map<String,Object> installQueryParamMap(TShopOrderShowQuery queryParam){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("beginTime",queryParam.getBeginDate());
        paramMap.put("endTime", queryParam.getEndDate()+" 23:59:59");
        paramMap.put("jdOrderNo", queryParam.getJdOrderNo());
        paramMap.put("buyerNo", queryParam.getBuyerNo());
        paramMap.put("taskStatus", queryParam.getTaskStatus());
        paramMap.put("shopUserId", queryParam.getShopUserId());
        return paramMap;
    }

    /**
     * 获取列表数量
     *
     * @param paramMap
     * @return
     */
    private int getTShopOrderShowTitleCount(Map<String,Object> paramMap){
        return baseMapper.selectTShopOrderShowTitlePageListCount(paramMap);
    }

    /**
     * 查询列表数据
     *
     * @param current
     * @param pageSize
     * @param paramMap
     * @return
     */
    private List<TShopOrderShowTitle> getTShopOrderShowTitleList(int current, int pageSize, Map<String,Object> paramMap, BigDecimal outStoreCommissionPrice){
        if(current>1){
            paramMap.put("start",(current-1)*pageSize);
        }else{
            paramMap.put("start",0);
        }
        paramMap.put("pageSize",pageSize);
        List<TShopOrderShowTitle> retList=baseMapper.selectTShopOrderShowTitlePageList(paramMap);
        for(TShopOrderShowTitle obj:retList){
            obj.setOutStoreCommissionPrice(outStoreCommissionPrice);
        }
        return retList;
    }

    /**
     * 查询展示的额任务明细数据
     *
     * @param shopOrderShowTitleListList
     * @return
     */
    private List<TShopOrderShowData> getTShopOrderShowDataList(List<TShopOrderShowTitle> shopOrderShowTitleListList){
        StringBuffer jdOrderNo=new StringBuffer("");
        for(TShopOrderShowTitle obj:shopOrderShowTitleListList){
            jdOrderNo.append(",'").append(obj.getJdOrderNo()).append("'");
        }
        //列表数据查询
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("jdOrderNo",jdOrderNo.substring(1));
        return baseMapper.selectTShopOrderShowDataList(paramMap);
    }

    /**
     * 组装返回的对象信息
     *
     * @param shopOrderShowTitleListList
     * @param shopOrderShowDataList
     * @return
     */
    private List<TShopOrderShow> installTShopOrderShow(List<TShopOrderShowTitle> shopOrderShowTitleListList,List<TShopOrderShowData> shopOrderShowDataList){
        List<TShopOrderShow> retObj=new ArrayList<>();
        for(TShopOrderShowTitle obj:shopOrderShowTitleListList){
            List<TShopOrderShowData> shopOrderShowDataListTemp=new ArrayList<>();
            for (TShopOrderShowData objShowDataTemp:shopOrderShowDataList){
                if(obj.getJdOrderNo().equals(objShowDataTemp.getJdOrderNo())){
                    shopOrderShowDataListTemp.add(objShowDataTemp);
                    obj.setOrderTotalMoney(obj.getOrderTotalMoney().add(objShowDataTemp.getGoodsPrice()));
                }
            }
            //组装返回对象
            TShopOrderShow objTemp=new TShopOrderShow();
            objTemp.setShopOrderShowTitle(obj);
            objTemp.setShopOrderShowDataList(shopOrderShowDataListTemp);
            retObj.add(objTemp);
        }
        return retObj;
    }

    @Override
    public List<TShopOrderShowData> getTShopOrderShowDataListByJdOrderNo(String jdOrderNo){
        //列表数据查询
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("jdOrderNo","'"+jdOrderNo+"'");
        return baseMapper.selectTShopOrderShowDataList(paramMap);
    }
}