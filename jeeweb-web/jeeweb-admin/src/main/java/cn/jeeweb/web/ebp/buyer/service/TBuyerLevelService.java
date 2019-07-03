package cn.jeeweb.web.ebp.buyer.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
public interface TBuyerLevelService extends ICommonService<TBuyerLevel> {


	Page<TBuyerLevel> selectBuyerLevelPageList(Queryable queryable, Wrapper<TBuyerLevel> wrapper);

	/**
	 * 新增买手等级信息
	 * 
	 * @param obj
	 */
	void addBuyerLevel(TBuyerLevel obj);
	
	/**
	 * 编辑保存买手等级信息
	 * 
	 * @param obj
	 */
	void updateBuyerLevel(TBuyerLevel obj);
	
	/**
	 * 主键ID获取买手等级信息
	 * 
	 * @param id
	 * @return
	 */
	TBuyerLevel getBuyerLevelById(String id);
}