package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;
import cn.jeeweb.web.ebp.buyer.mapper.TbuyerInfoMapper;
import cn.jeeweb.web.ebp.buyer.service.TbuyerInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.mapper.Wrapper;

@Transactional
@Service("tbuyerInfoService")
public class TbuyerInfoServiceImpl extends CommonServiceImpl<TbuyerInfoMapper, TbuyerInfo> implements TbuyerInfoService {

	@Override
	public TbuyerInfo getTbuyerInfoById(String id) {
		return baseMapper.selectBuyerInfoById(id);
	}

	@Override
	public Page<TbuyerInfo> selectBuyerInfoPageList(Queryable queryable, Wrapper<TbuyerInfo> wrapper) {
		QueryToWrapper<TbuyerInfo> queryToWrapper = new QueryToWrapper<TbuyerInfo>();
		queryToWrapper.parseCondition(wrapper, queryable);
		queryToWrapper.parseSort(wrapper, queryable);
		Pageable pageable = queryable.getPageable();
		com.baomidou.mybatisplus.plugins.Page<TbuyerInfo> page = new com.baomidou.mybatisplus.plugins.Page<TbuyerInfo>(pageable.getPageNumber(), pageable.getPageSize());
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectBuyerInfoList(page, wrapper));
		return new PageImpl<TbuyerInfo>(page.getRecords(), queryable.getPageable(), page.getTotal());
	}

	@Override
	public void updateTbuyerInfoById(TbuyerInfo obj) {
		// TODO Auto-generated method stub
		
	}

}
