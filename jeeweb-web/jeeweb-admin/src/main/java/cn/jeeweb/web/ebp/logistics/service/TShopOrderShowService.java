package cn.jeeweb.web.ebp.logistics.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShow;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowData;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowQuery;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowTitle;
import cn.jeeweb.web.ebp.tsys.entity.TSysConfigParam;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.List;

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
	 * @param queryParam
	 * @return
	 */
	Page<TShopOrderShow> selectTShopOrderShowPageList(Queryable queryable, TShopOrderShowQuery queryParam);

	/**
	 * 京东订单号获取数据
	 *
	 * @param jdOrderNo
	 * @return
	 */
	TShopOrderShowTitle getTShopOrderShowTitleByJdOrderNo(String jdOrderNo);
	/**
	 * 根据单个京东订单编号获取任务数据
	 *
	 * @param jdOrderNo
	 * @return
	 */
	List<TShopOrderShowData> getTShopOrderShowDataListByJdOrderNo(String jdOrderNo);

}