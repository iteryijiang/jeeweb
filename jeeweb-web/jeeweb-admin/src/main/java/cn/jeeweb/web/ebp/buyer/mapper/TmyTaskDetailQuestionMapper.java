package cn.jeeweb.web.ebp.buyer.mapper;

import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailQuestion;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TmyTaskDetailQuestionMapper extends BaseMapper<TmyTaskDetailQuestion> {
	
	List<TmyTaskDetailQuestion> selectUserList(Pagination page, @Param("ew") Wrapper<TmyTaskDetailQuestion> wrapper);


	List<Map> listFinanceBuyerReport(@Param("map") Map map);

}