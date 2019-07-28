package cn.jeeweb.web.ebp.tsys.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.tsys.entity.TSysConfigParam;
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
public interface TSysConfigParamService extends ICommonService<TSysConfigParam> {

	/**
	 * 列表查询
	 *
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	Page<TSysConfigParam> selectTSysConfigParamPageList(Queryable queryable, Wrapper<TSysConfigParam> wrapper);

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
	void updateTSysConfigParam(TSysConfigParam obj);

	/**
	 * 新增
	 *
	 * @param obj
	 */
	void insertTSysConfigParam(TSysConfigParam obj);
}