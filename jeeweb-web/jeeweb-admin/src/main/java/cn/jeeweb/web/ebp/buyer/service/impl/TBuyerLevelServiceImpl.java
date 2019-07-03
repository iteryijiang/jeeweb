package cn.jeeweb.web.ebp.buyer.service.impl;

import java.util.HashMap;
import java.util.Map;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroup;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerLevelMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerLevelService;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Transactional
@Service("buyerLevelService")
public class TBuyerLevelServiceImpl extends CommonServiceImpl<TBuyerLevelMapper,TBuyerLevel> implements TBuyerLevelService {

	@Override
	public Page<TBuyerLevel> selectBuyerLevelPageList(Queryable queryable, Wrapper<TBuyerLevel> wrapper){
		QueryToWrapper<TBuyerLevel> queryToWrapper = new QueryToWrapper<TBuyerLevel>();
		queryToWrapper.parseCondition(wrapper, queryable);
		queryToWrapper.parseSort(wrapper, queryable);
		Pageable pageable = queryable.getPageable();
		com.baomidou.mybatisplus.plugins.Page<TBuyerLevel> page = new com.baomidou.mybatisplus.plugins.Page<TBuyerLevel>(pageable.getPageNumber(), pageable.getPageSize());
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectBuyerLevelPageList(page, wrapper));
		return new PageImpl<TBuyerLevel>(page.getRecords(), queryable.getPageable(), page.getTotal());
	}

	@Override
	public void addBuyerLevel(TBuyerLevel obj) {
		int sameBuyerLevelNameCount=getBuyerLevelByName(obj.getId(),obj.getLevelName());
		if(sameBuyerLevelNameCount>0) {
			throw new RuntimeException("保存数据失败[买手等级名称已经存在]");
		}
		int sameBuyerLevelCodeCount=getBuyerLevelByCode(obj.getId(),obj.getLevelCode());
		if(sameBuyerLevelCodeCount>0) {
			throw new RuntimeException("保存数据失败[买手等级编号已经存在]");
		}
		int num=baseMapper.insert(obj);
		if(num!=1) {
			throw new RuntimeException("保存数据失败");
		}
	}
	
	private int getBuyerLevelByName(String levelId,String buyerLevelName) {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("levelName", buyerLevelName);
		paramMap.put("levelId", levelId);
		return baseMapper.getBuyerLevelByName(paramMap);
	}
	
	private int getBuyerLevelByCode(String levelId,String buyerLevelCode) {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("levelCode", buyerLevelCode);
		paramMap.put("levelId", levelId);
		return baseMapper.getBuyerLevelByCode(paramMap);
	}

	@Override
	public void updateBuyerLevel(TBuyerLevel obj) {
		int sameBuyerLevelNameCount=getBuyerLevelByName(obj.getId(),obj.getLevelName());
		if(sameBuyerLevelNameCount>0) {
			throw new RuntimeException("保存数据失败[买手等级名称已经存在]");
		}
		int sameBuyerLevelCodeCount=getBuyerLevelByCode(obj.getId(),obj.getLevelCode());
		if(sameBuyerLevelCodeCount>0) {
			throw new RuntimeException("保存数据失败[买手等级编号已经存在]");
		}
		int num=baseMapper.updateBuyerLevel(obj);
		if(num!=1) {
			throw new RuntimeException("保存数据失败");
		}
		
	}

	@Override
	public TBuyerLevel getBuyerLevelById(long id) {
		return baseMapper.selectById(id);
	}
	
	
}