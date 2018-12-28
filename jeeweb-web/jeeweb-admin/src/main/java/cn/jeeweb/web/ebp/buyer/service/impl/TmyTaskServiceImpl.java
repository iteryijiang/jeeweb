package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.mapper.TmyTaskMapper;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("TshopInfoService")
public class TmyTaskServiceImpl extends CommonServiceImpl<TmyTaskMapper, TmyTask> implements TmyTaskService {

    public List<TmyTask> selBaseIdMyTaskList(String taskId){
        return baseMapper.selBaseIdMyTaskList(taskId);
    }
    public List<Map> groupBytaskstatus(String taskId){
        return baseMapper.groupBytaskstatus(taskId);
    }
}
