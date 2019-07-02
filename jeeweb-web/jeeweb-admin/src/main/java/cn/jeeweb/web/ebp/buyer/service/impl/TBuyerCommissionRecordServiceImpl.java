package cn.jeeweb.web.ebp.buyer.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerCommissionRecord;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerCommissionRecordMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerCommissionRecordService;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<TBuyerCommissionRecord> selectGroupPageList(Queryable queryable,
			Wrapper<TBuyerCommissionRecord> wrapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBuyerCommissionRecord(Date sourceDate) {
		//生成所有买手的佣金信息
		//生成所有组长的佣金提成
		//更改组长的佣金信息
	}

	@Override
	public void updateBuyerCommissionRecordForBack(TBuyerCommissionRecord obj) {
		//获取原有佣金信息
		//获取买手组对应的组长提成信息
		TBuyerCommissionRecord groupLeaderCommissionObj=baseMapper.getTBuyerCommissionRecordByBuyerIdAtime(null);
	}
}
