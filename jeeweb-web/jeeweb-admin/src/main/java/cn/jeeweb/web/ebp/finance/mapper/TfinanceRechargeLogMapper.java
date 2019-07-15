package cn.jeeweb.web.ebp.finance.mapper;

import cn.jeeweb.web.ebp.finance.entity.TfinanceRecharge;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TfinanceRechargeLogMapper extends BaseMapper<TfinanceRechargeLog> {
	
	List<TfinanceRechargeLog> selectUserList(Pagination page, @Param("ew") Wrapper<TfinanceRechargeLog> wrapper);



	List<TfinanceRechargeLog> listTfinanceRechargeLog(@Param("ew") Wrapper<TfinanceRechargeLog> wrapper);
}