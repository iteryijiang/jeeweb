package cn.jeeweb.web.ebp.finance.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.List;

public interface TfinanceRechargeLogService extends ICommonService<TfinanceRechargeLog> {


    public List<TfinanceRechargeLog> listAll(Queryable queryable, Wrapper<TfinanceRechargeLog> wrapper);
}
