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
	Map sumNumAndPrice(@Param("map") Map m);
	List<TmyTaskDetail> selectMytaskList(@Param("mytaskid") String mytaskid);


	List<Map> listFinanceBuyerReport(@Param("map") Map map);

	List<Map> listTfinanceTaskDoubleReport(@Param("map") Map map);

	List<TmyTaskDetail> listNoSendGood(@Param("map") Map map);


	List<TmyTaskDetail> listDetail(Pagination page, @Param("ew") Wrapper<TmyTaskDetail> wrapper);

	List<TmyTaskDetail> listDetailGroup(Pagination page, @Param("ew") Wrapper<TmyTaskDetail> wrapper);

	List<TmyTaskDetail> listShopBaseDetail(Pagination page, @Param("ew") Wrapper<TmyTaskDetail> wrapper);

	List<TmyTaskDetail> listDetail(@Param("ew") Wrapper<TmyTaskDetail> wrapper);

	List<TmyTaskDetail> listDetailGroup(@Param("ew") Wrapper<TmyTaskDetail> wrapper);

	int sumMyTask(@Param("map") Map map);
	int sumTaskBase(@Param("map") Map map);

	/**
	 * 更改任务单异常状态
	 *
	 * @param paramMap
	 * @return
	 */
	int upTaskErrorStatus(@Param("map") Map<String,Object> paramMap);
}