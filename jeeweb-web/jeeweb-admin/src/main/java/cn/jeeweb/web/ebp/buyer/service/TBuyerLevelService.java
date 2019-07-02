package cn.jeeweb.web.ebp.buyer.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
public interface TBuyerLevelService extends ICommonService<TBuyerLevel> {
	
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
	TBuyerLevel getBuyerLevelById(long id);
}