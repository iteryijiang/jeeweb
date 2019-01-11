package cn.jeeweb.web.ebp.buyer.mapper;

import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TmyTaskDetailMapper extends BaseMapper<TmyTaskDetail> {
	
	List<TmyTaskDetail> selectUserList(Pagination page, @Param("ew") Wrapper<TmyTaskDetail> wrapper);

	List<TmyTaskDetail> selBaseIdMyTaskDetailList(@Param("taskId") String taskId);
	List<Map> groupBytaskstatus(@Param("taskId") String taskId);
	List<Map> groupBytaskstate(@Param("taskId") String taskId);
	List<TmyTaskDetail> selectMytaskList(@Param("mytaskid") String mytaskid);
}