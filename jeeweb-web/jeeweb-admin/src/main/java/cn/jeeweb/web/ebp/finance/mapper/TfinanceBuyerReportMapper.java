package cn.jeeweb.web.ebp.finance.mapper;

import cn.jeeweb.web.ebp.finance.entity.TfinanceBuyerReport;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TfinanceBuyerReportMapper extends BaseMapper<TfinanceBuyerReport> {
	
	List<TfinanceBuyerReport> selectUserList(Pagination page, @Param("ew") Wrapper<TfinanceBuyerReport> wrapper);

	Map showBuyerReportLoad(@Param("map") Map map);

	Integer sumTfinanceBuyerReport(@Param("map") Map map);

}