package cn.jeeweb.web.ebp.buyer.mapper;

import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TbuyerInfoMapper extends BaseMapper<TbuyerInfo> {
	
	List<TbuyerInfo> selectBuyerInfoList(Pagination page, @Param("ew") Wrapper<TbuyerInfo> wrapper);

	/**
	 * g根据主键ID查询
	 *
	 * @param id
	 * @return
	 */
	TbuyerInfo selectBuyerInfoById(String id);

	/**
	 * 根据用户id查询数据
	 *
	 * @param userId
	 * @return
	 */
	TbuyerInfo selectBuyerInfoByUserId(String userId);

	/**
	 * 根据买手用户id+分组ID获取买手信息
	 *
	 * @param map
	 * @return
	 */
	List<TbuyerInfo> selectBuyerInfoListByUserIdGroupId(@Param("map") Map<String,Object> map);

	/**
	 * 编辑买手信息
	 *
	 * @param obj
	 * @return
	 */
	int updateBuyerInfoById(TbuyerInfo obj);

	/**
	 * 更改买手对应的分组信息
	 *
	 * @param map
	 * @return
	 */
	int updateForSetBuyerGroupId(@Param("map") Map<String,Object> map);

	/**
	 * 批量更改买手记录对应的分组信息
	 *
	 * @param map
	 * @return
	 */
	int updateForBatchSetBuyerGroupId(@Param("map") Map<String,Object> map);

	/**
	 * 批量清空买手分组信息
	 *
	 * @param map
	 * @return
	 */
	int updateForBatchDeleteBuyerGroupId(@Param("map") Map<String,Object> map);

	/**
	 * 买手ID+分组ID=>清空买手分组信息
	 *
	 * @param map
	 * @return
	 */
	int updateForDeleteBuyerGroupId(@Param("map") Map<String,Object> map);
}