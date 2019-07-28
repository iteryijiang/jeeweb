package cn.jeeweb.web.ebp.logistics.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShow;
import cn.jeeweb.web.ebp.tsys.entity.TSysConfigParam;
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
public interface TShopOrderShowService extends ICommonService<TShopOrderShow> {

	/**
	 * 列表查询
	 *
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	Page<TShopOrderShow> selectTShopOrderShowPageList(Queryable queryable, Wrapper<TShopOrderShow> wrapper);


}