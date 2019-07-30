package cn.jeeweb.web.ebp.logistics.mapper;

import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrder;
import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrderDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

	/**
	 * 明细信息
	 *
	 * @param objList
	 * @return
	 */
	int insertTLogisticsOrderDetail(List<TLogisticsOrderDetail> objList);

    /**
     * 根据主表ID获取明细数据
     *
     * @param masterId
     * @return
     */
    List<TLogisticsOrderDetail> selectTLogisticsOrderDetailListByMasterid(String masterId);

	/**
	 * 更改明细状态
	 *
	 * @param paramMap
	 * @return
	 */
	int updateTLogisticsOrderDetailStatus(Map<String,Object> paramMap);
}