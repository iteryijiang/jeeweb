package cn.jeeweb.web.ebp.logistics.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrder;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.catalina.LifecycleState;

import java.util.List;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
public interface TLogisticsOrderService extends ICommonService<TLogisticsOrder> {

	/**
	 * 列表查询
	 *
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	Page<TLogisticsOrder> selectTLogisticsOrderPageList(Queryable queryable, Wrapper<TLogisticsOrder> wrapper);

	/**
     * 主键ID获取数据
	 *
	 * @param id
     * @return
     */
	TLogisticsOrder selectTLogisticsOrderById(String id);

	/**
     * 编辑数据
	 *
	 * @param ids
	 * @param status
	 */
	void updateTLogisticsOrderStatus(String ids,int status);

	/**
     * 新增
	 *
	 * @param obj
	 */
	void insertTLogisticsOrder(TLogisticsOrder obj);

	/***
	 * 新增处理
	 *
	 * @param objList
	 */
	void insertTLogisticsOrder(List<TLogisticsOrder> objList);
}