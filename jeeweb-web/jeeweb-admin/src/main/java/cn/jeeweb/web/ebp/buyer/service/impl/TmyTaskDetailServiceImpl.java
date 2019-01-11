package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.mapper.TmyTaskDetailMapper;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("TmyTaskDetailService")
public class TmyTaskDetailServiceImpl extends CommonServiceImpl<TmyTaskDetailMapper, TmyTaskDetail> implements TmyTaskDetailService {

    public List<TmyTaskDetail> selBaseIdMyTaskDetailList(String taskId){
        return baseMapper.selBaseIdMyTaskDetailList(taskId);
    }
    public List<Map> groupBytaskstatus(String taskId){
        return baseMapper.groupBytaskstatus(taskId);
    }

    public List<Map> groupBytaskstate(String taskId){
        return baseMapper.groupBytaskstate(taskId);
    }

    public List<TmyTaskDetail> selectMytaskList(String mytaskid){
        return baseMapper.selectMytaskList(mytaskid);

    }
}
