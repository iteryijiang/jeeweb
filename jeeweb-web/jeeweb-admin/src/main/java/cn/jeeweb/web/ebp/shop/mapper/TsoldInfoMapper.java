package cn.jeeweb.web.ebp.shop.mapper;

import cn.jeeweb.web.ebp.shop.entity.TsoldInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TsoldInfoMapper extends BaseMapper<TsoldInfo> {

	List<TsoldInfo> selectUserList(Pagination page, @Param("ew") Wrapper<TsoldInfo> wrapper);

	/**
	 *分页查询销售列表数据
	 *
	 * @param page
	 * @param wrapper
	 * @return
	 */
	List<TsoldInfo> selectSellerInfoPageList(Pagination page, @Param("ew") Wrapper<TsoldInfo> wrapper);

	/**
	 * 销售用户ID查询数据
	 *
	 * @param sellerUserId
	 * @return
	 */
	TsoldInfo selectSellerInfoBySellerUserId(String sellerUserId);

	/**
	 * 主键 ID获取数据
	 *
	 * @param id
	 * @return
	 */
	TsoldInfo selectSellerInfoById(String id);

	/***
	 * 更新销售等级信息
	 *
	 * @param paramMap
	 * @return
	 */
	int updateSellerInfoLevelById(@Param("map") Map<String,Object> paramMap);
}