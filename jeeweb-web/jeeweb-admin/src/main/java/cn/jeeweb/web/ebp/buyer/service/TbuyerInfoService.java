package cn.jeeweb.web.ebp.buyer.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;

public interface TbuyerInfoService extends ICommonService<TbuyerInfo> {

	/**
	 * 主键ID查询买手信息
	 * 
	 * @param id
	 * @return
	 */
	TbuyerInfo getTbuyerInfoById(String id);
	
	/**
	 * 分页查询买手信息
	 * 
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	 Page<TbuyerInfo> selectApplyPageList(Queryable queryable, Wrapper<TbuyerInfo> wrapper);

	/**
	 * 编辑买手信息
	 * 
	 * @param obj
	 */
	void updateTbuyerInfoById(TbuyerInfo obj);
}
