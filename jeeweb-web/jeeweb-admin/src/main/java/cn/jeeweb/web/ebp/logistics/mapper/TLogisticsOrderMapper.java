package cn.jeeweb.web.ebp.logistics.mapper;

import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统参数设置
 * 
 * @author ytj
 *
 */
@Mapper
public interface TLogisticsOrderMapper extends BaseMapper<TLogisticsOrder> {

	/**
	 * 列表查询
	 *
	 * @param page
	 * @param wrapper
	 * @return
	 */
	List<TLogisticsOrder> selectTLogisticsOrderPageList(Pagination page, @Param("ew") Wrapper<TLogisticsOrder> wrapper);

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
	 * @param obj
	 */
	int updateTLogisticsOrderStatus(TLogisticsOrder obj);

	/**
	 * 新增
	 *
	 * @param obj
	 */
	int insertTLogisticsOrder(TLogisticsOrder obj);
}