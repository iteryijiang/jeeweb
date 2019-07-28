package cn.jeeweb.web.ebp.logistics.mapper;

import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShow;
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
public interface TShopOrderShowMapper extends BaseMapper<TShopOrderShow> {

	/**
	 * 列表查询
	 *
	 * @param page
	 * @param wrapper
	 * @return
	 */
	List<TShopOrderShow> selectTShopOrderShowPageList(Pagination page, @Param("ew") Wrapper<TShopOrderShow> wrapper);

}