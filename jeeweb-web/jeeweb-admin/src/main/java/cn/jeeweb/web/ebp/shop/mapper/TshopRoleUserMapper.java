package cn.jeeweb.web.ebp.shop.mapper;

import cn.jeeweb.web.ebp.shop.entity.TshopRoleUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TshopRoleUserMapper extends BaseMapper<TshopRoleUser> {
	
	List<TshopRoleUser> selectUserList(Pagination page, @Param("ew") Wrapper<TshopRoleUser> wrapper);

	List<Map> listSoldFinance(@Param("map") Map map);
	Map sumListSoldFinance(@Param("map") Map map);

	List<TshopRoleUser> selectShopList(Pagination page, @Param("ew") Wrapper<TshopRoleUser> wrapper);
}