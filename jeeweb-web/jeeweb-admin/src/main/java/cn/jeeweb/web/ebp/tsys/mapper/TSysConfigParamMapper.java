package cn.jeeweb.web.ebp.tsys.mapper;

import cn.jeeweb.web.ebp.tsys.entity.TSysConfigParam;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 系统参数设置
 * 
 * @author ytj
 *
 */
@Mapper
public interface TSysConfigParamMapper extends BaseMapper<TSysConfigParam> {

	/**
	 * 列表查询
	 *
	 * @param page
	 * @param wrapper
	 * @return
	 */
	List<TSysConfigParam> selectTSysConfigParamPageList(Pagination page, @Param("ew") Wrapper<TSysConfigParam> wrapper);

	/**
	 * 主键ID获取数据
	 *
	 * @param id
	 * @return
	 */
	TSysConfigParam selectTSysConfigById(String id);

	/**
	 * 参数名称获取数据
	 *
	 * @param configParam
	 * @return
	 */
	TSysConfigParam selectTSysConfigByConfigParam(String configParam);

	/**
	 * 编辑数据
	 *
	 * @param obj
	 */
	int updateTSysConfigParam(TSysConfigParam obj);

	/**
	 * 新增
	 *
	 * @param obj
	 */
	int insertTSysConfigParam(TSysConfigParam obj);
}