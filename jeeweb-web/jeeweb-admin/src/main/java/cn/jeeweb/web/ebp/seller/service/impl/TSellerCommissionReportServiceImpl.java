package cn.jeeweb.web.ebp.seller.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerLevelMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerLevelService;
import cn.jeeweb.web.ebp.exception.MyProcessException;
import cn.jeeweb.web.ebp.seller.entity.TSellerCommissionReport;
import cn.jeeweb.web.ebp.seller.entity.TSellerCommissionReportDetail;
import cn.jeeweb.web.ebp.seller.mapper.TSellerCommissionReportMapper;
import cn.jeeweb.web.ebp.seller.service.TSellerCommissionReportService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Transactional
@Service("sellerCommissionReportService")
public class TSellerCommissionReportServiceImpl extends CommonServiceImpl<TSellerCommissionReportMapper, TSellerCommissionReport> implements TSellerCommissionReportService {

	@Override
	public Page<TSellerCommissionReport> selectSellerCommissionReportPageList(Queryable queryable, Wrapper<TSellerCommissionReport> wrapper){
		QueryToWrapper<TSellerCommissionReport> queryToWrapper = new QueryToWrapper<TSellerCommissionReport>();
		queryToWrapper.parseCondition(wrapper, queryable);
		queryToWrapper.parseSort(wrapper, queryable);
		Pageable pageable = queryable.getPageable();
		com.baomidou.mybatisplus.plugins.Page<TSellerCommissionReport> page = new com.baomidou.mybatisplus.plugins.Page<TSellerCommissionReport>(pageable.getPageNumber(), pageable.getPageSize());
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectPageList(page, wrapper));
		return new PageImpl<TSellerCommissionReport>(page.getRecords(), queryable.getPageable(), page.getTotal());
	}

	@Override
	public Page<TSellerCommissionReport> selectGroupPageList(Queryable queryable, Wrapper<TSellerCommissionReport> wrapper){
		QueryToWrapper<TSellerCommissionReport> queryToWrapper = new QueryToWrapper<TSellerCommissionReport>();
		queryToWrapper.parseCondition(wrapper, queryable);
		queryToWrapper.parseSort(wrapper, queryable);
		Pageable pageable = queryable.getPageable();
		com.baomidou.mybatisplus.plugins.Page<TSellerCommissionReport> page = new com.baomidou.mybatisplus.plugins.Page<TSellerCommissionReport>(pageable.getPageNumber(), pageable.getPageSize());
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectGroupPageList(page, wrapper));
		return new PageImpl<TSellerCommissionReport>(page.getRecords(), queryable.getPageable(), page.getTotal());
	}

	@Override
	public void insertSellerCommissionReportForCreate(Date sourceDate){
		Map<String,Object> paramMap=new HashMap<>();
		String beginDate= DateUtils.formatDate(sourceDate,"yyyy-MM-dd");
		paramMap.put("dataMonth",DateUtils.formatDate(sourceDate,"yyyy-MM").replaceAll("-",""));
		paramMap.put("atime",beginDate);
		paramMap.put("createBy","schule");
		paramMap.put("beginTime",beginDate);
		paramMap.put("endTime",DateUtils.getDateEnd(sourceDate));
		//清空表
		baseMapper.updateSellerCommissionDetailTempForTruncate();
		//生成明细表数据临时表
		baseMapper.insertSellerCommissionDetailTemp(paramMap);
		//生成明细表数据
		baseMapper.insertSellerCommissionDetail(paramMap);
		//汇总明细表数据生成总表数据
		baseMapper.insertSellerCommission(paramMap);
	}

	@Override
	public void insertSellerCommissionReportForBackTask(Date beginDate,Date endDate){
		Map<String,Object> paramMap=new HashMap<>();
		String beginDateStr= DateUtils.formatDate(beginDate,"yyyy-MM-dd");
		paramMap.put("dataMonth",DateUtils.formatDate(beginDate,"yyyyMM"));
		paramMap.put("atime",endDate);
		paramMap.put("createBy","schule");
		paramMap.put("beginTime",beginDateStr);
		paramMap.put("endTime",DateUtils.getDateEnd(endDate));
		//每个月的2号执行汇总数据
		baseMapper.updateSellerCommissionDetailTempForTruncate();
		//生成明细表数据临时表
		baseMapper.insertSellerCommissionDetailTempForBackCommission(paramMap);
		//生成明细表数据
		baseMapper.insertSellerCommissionDetailForBackCommission(paramMap);
		//汇总明细表数据生成总表数据
		baseMapper.insertSellerCommissionForBackCommission(paramMap);


	}

	@Override
	public List<TSellerCommissionReportDetail> selectSellerCommissionReportDetailList(String seller, Date atime, String dateRange){
		List<TSellerCommissionReportDetail> retObj=null;

		return retObj;
	}
}