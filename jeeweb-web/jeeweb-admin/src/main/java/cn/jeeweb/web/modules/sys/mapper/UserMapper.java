package cn.jeeweb.web.modules.sys.mapper;

import cn.jeeweb.web.modules.sys.entity.User;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

@Mapper
public interface UserMapper extends BaseMapper<User> {
	
	/**
	 * 
	 * @title: selectUserList
	 * @description: 查找用户列表
	 * @param wrapper
	 * @return
	 * @return: List<User>
	 */
	List<User> selectUserList(Pagination page, @Param("ew") Wrapper<User> wrapper);

	/**
	 * 调整冻结状态的数据
	 *
	 * @param map
	 * @return
	 */
	int updateUserFreezeStatus(@Param("map") Map map);

	/**
	 * 调整领取任务状态
	 *
	 * @param map
	 * @return
	 */
	int updateUserReceiveTaskStatus(@Param("map") Map map);
}