package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;
import cn.jeeweb.web.ebp.buyer.mapper.TbuyerInfoMapper;
import cn.jeeweb.web.ebp.buyer.service.TbuyerInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Wrapper;

@Transactional
@Service("TbuyerInfoService")
public class TbuyerInfoServiceImpl extends CommonServiceImpl<TbuyerInfoMapper, TbuyerInfo> implements TbuyerInfoService {

	@Override
	public TbuyerInfo getTbuyerInfoById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<TbuyerInfo> selectApplyPageList(Queryable queryable, Wrapper<TbuyerInfo> wrapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTbuyerInfoById(TbuyerInfo obj) {
		// TODO Auto-generated method stub
		
	}

}
