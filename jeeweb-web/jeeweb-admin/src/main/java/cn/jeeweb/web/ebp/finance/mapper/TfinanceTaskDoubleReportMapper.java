package cn.jeeweb.web.ebp.finance.mapper;

import cn.jeeweb.web.ebp.finance.entity.TfinanceTaskDoubleReport;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TfinanceTaskDoubleReportMapper extends BaseMapper<TfinanceTaskDoubleReport> {
	
	List<TfinanceTaskDoubleReport> selectUserList(Pagination page, @Param("ew") Wrapper<TfinanceTaskDoubleReport> wrapper);

	Integer sumTfinanceTaskDoubleReport(@Param("map") Map map);

	public Map sumTaskNum(@Param("map") Map m);
}