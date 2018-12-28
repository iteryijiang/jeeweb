package cn.jeeweb.web.ebp.buyer.mapper;

import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TmyTaskMapper extends BaseMapper<TmyTask> {
	
	List<TmyTask> selectUserList(Pagination page, @Param("ew") Wrapper<TmyTask> wrapper);

	List<TmyTask> selBaseIdMyTaskList(@Param("taskId") String taskId);
	List<Map> groupBytaskstatus(@Param("taskId") String taskId);
}