package cn.jeeweb.web.ebp.logistics.mapper;

import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShow;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowData;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowTitle;
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
public interface TShopOrderShowMapper extends BaseMapper<TShopOrderShow> {

	/**
	 * 列表查询
	 *
	 * @param paramMap
	 * @return
	 */
	List<TShopOrderShowTitle> selectTShopOrderShowTitlePageList(@Param("map") Map<String,Object> paramMap);

	/**
	 * 京东单号获取单个任务信息
	 *
	 * @param paramMap
	 * @return
	 */
	TShopOrderShowTitle getTShopOrderShowTitleByJdOrderNo(@Param("map") Map<String,Object> paramMap);

	/**
	 * 查询数量
	 *
	 * @param paramMap
	 * @return
	 */
	int selectTShopOrderShowTitlePageListCount(@Param("map") Map<String,Object> paramMap);

	/***
	 * 条件查询展示的任务信息
	 *
	 * @param paramMap
	 * @return
	 */
	List<TShopOrderShowData> selectTShopOrderShowDataList(@Param("map") Map<String,Object> paramMap);

}