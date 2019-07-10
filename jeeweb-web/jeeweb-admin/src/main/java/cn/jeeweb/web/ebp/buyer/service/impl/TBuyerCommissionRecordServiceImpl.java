package cn.jeeweb.web.ebp.buyer.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerCommissionRecord;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerCommissionRecordMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerCommissionRecordService;
import org.springframework.web.bind.annotation.Mapping;

/**
 * 买手佣金记录
 * 每天定时任务生成
 * 
 * @author ytj
 *
 */
@Transactional
@Service("buyerCommissionRecordService")
public class TBuyerCommissionRecordServiceImpl extends CommonServiceImpl<TBuyerCommissionRecordMapper,TBuyerCommissionRecord> implements TBuyerCommissionRecordService{

	@Override
	public Page<TBuyerCommissionRecord> selectPageList(Queryable queryable, Wrapper<TBuyerCommissionRecord> wrapper) {
		QueryToWrapper<TBuyerCommissionRecord> queryToWrapper = new QueryToWrapper<TBuyerCommissionRecord>();
		queryToWrapper.parseCondition(wrapper, queryable);
		queryToWrapper.parseSort(wrapper, queryable);
		Pageable pageable = queryable.getPageable();
		com.baomidou.mybatisplus.plugins.Page<TBuyerCommissionRecord> page = new com.baomidou.mybatisplus.plugins.Page<TBuyerCommissionRecord>(pageable.getPageNumber(), pageable.getPageSize());
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectPageList(page, wrapper));
		return new PageImpl<TBuyerCommissionRecord>(page.getRecords(), queryable.getPageable(), page.getTotal());
	}

	@Override
	public List<TBuyerCommissionRecord> selectListByBuyerIdMonth(int month, String buyerId){
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("dataMonth",month);
		paramMap.put("buyerId",buyerId);
		return baseMapper.getTBuyerCommissionRecordByBuyerIdMonth(paramMap);
	}

	@Override
	public Page<TBuyerCommissionRecord> selectGroupPageList(Queryable queryable,Wrapper<TBuyerCommissionRecord> wrapper) {
		QueryToWrapper<TBuyerCommissionRecord> queryToWrapper = new QueryToWrapper<TBuyerCommissionRecord>();
		queryToWrapper.parseCondition(wrapper, queryable);
		queryToWrapper.parseSort(wrapper, queryable);
		Pageable pageable = queryable.getPageable();
		com.baomidou.mybatisplus.plugins.Page<TBuyerCommissionRecord> page = new com.baomidou.mybatisplus.plugins.Page<TBuyerCommissionRecord>(pageable.getPageNumber(), pageable.getPageSize());
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectGroupPageList(page, wrapper));
		return new PageImpl<TBuyerCommissionRecord>(page.getRecords(), queryable.getPageable(), page.getTotal());
	}

	@Override
	public void addBuyerCommissionRecord(Date sourceDate) {
		Map<String,Object> paramMap=new HashMap<>();
		String beginDate=DateUtils.formatDate(sourceDate,"yyyy-MM-dd");
		paramMap.put("dataMonth",DateUtils.formatDate(sourceDate,"yyyy-MM").replaceAll("-",""));
		paramMap.put("atime",beginDate);
		paramMap.put("createBy","schule");
		paramMap.put("beginTime",beginDate);
		paramMap.put("endTime",DateUtils.getDateEnd(sourceDate));
		baseMapper.updateBuyerCommissionGroupForTruncateTemp();
		baseMapper.updateBuyerCommissionTaskForTruncateTemp();
		//生成所有买手的佣金信息
		baseMapper.insertBuyerCommissionInfo(paramMap);
		//生成所有组长的佣金提成
		baseMapper.insertBuyerCommissionTaskNumTemp(paramMap);
		baseMapper.insertBuyerCommissionGroupTemp(paramMap);
		//更改组长的佣金信息
		baseMapper.updateBuyerGroupMoney(paramMap);
		baseMapper.updateBuyerTaskNum(paramMap);
		//更新买手佣金信息
		baseMapper.updateBuyerCommission(paramMap);
	}

	@Override
	public void updateBuyerCommissionRecordForBack(TBuyerCommissionRecord obj) {
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("atime",DateUtils.formatDate(obj.getAtime(),"yyyy-MM-dd"));
		paramMap.put("buyerId",obj.getBuyerId());
		//获取原有佣金信息
		TBuyerCommissionRecord buyerCommission=baseMapper.getTBuyerCommissionRecordByBuyerIdAtime(paramMap);
		buyerCommission.setTaskNum(0);
		buyerCommission.setTaskMoney(obj.getTaskMoney());
		buyerCommission.setTaskLinkNum(1);
		buyerCommission.setGroupMoney(BigDecimal.ZERO);
		buyerCommission.setTaskLinkNum(1);
		baseMapper.updateBuyCommissionForBack(buyerCommission);
		//获取买手组对应的组长提成信息??
		paramMap.put("buyerId",buyerCommission.getBuyerGroupLeader());
		TBuyerCommissionRecord groupLeaderCommissionObj=baseMapper.getTBuyerCommissionRecordByBuyerIdAtime(paramMap);
		groupLeaderCommissionObj.setTaskNum(0);
		groupLeaderCommissionObj.setTaskMoney(BigDecimal.ZERO);
		groupLeaderCommissionObj.setTaskLinkNum(0);
		groupLeaderCommissionObj.setGroupMoney(buyerCommission.getBuyerGroupLeaderCommissionValue());
		groupLeaderCommissionObj.setTaskLinkNum(0);
		baseMapper.updateBuyCommissionForBack(groupLeaderCommissionObj);
	}
}
