package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.utils.BeanUtils;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailQuestion;
import cn.jeeweb.web.ebp.buyer.mapper.TmyTaskDetailQuestionMapper;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailQuestionService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service("TmyTaskDetailQuestionService")
public class TmyTaskDetailQuestionServiceImpl extends CommonServiceImpl<TmyTaskDetailQuestionMapper, TmyTaskDetailQuestion> implements TmyTaskDetailQuestionService {

    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TmyTaskService tmyTaskService;
    @Autowired
    private TtaskBaseService ttaskBaseService;


    public boolean saveTmyTaskDetailQuestion(TtaskBase ttaskBase,TmyTaskDetail tmyTaskDetail) throws Exception{
        List<TmyTaskDetail> list = new ArrayList<TmyTaskDetail>();
        if(ttaskBase!=null){
            list = tmyTaskDetailService.selBaseIdMyTaskDetailList(ttaskBase.getId());
        }
        if(tmyTaskDetail!=null){
            list.add(tmyTaskDetail);
        }
        if(list!=null&&!list.isEmpty()){
            for (TmyTaskDetail ttd:list) {
                if("1".equals(ttd.getTaskstate())||"2".equals(ttd.getTaskstate())){
                    TmyTaskDetailQuestion tmyTaskDetailQuestion = new TmyTaskDetailQuestion();
                    BeanUtils.copyProperties(tmyTaskDetail, tmyTaskDetailQuestion);
                    TmyTask tt = tmyTaskService.selectById(ttd.getMytaskid());
                    if(tt!=null){
                        tt.setTotalprice(tt.getTotalprice().subtract(ttd.getPays()));
                        tmyTaskService.updateById(tt);
                    }
                    insert(tmyTaskDetailQuestion);
                    tmyTaskDetailService.deleteById(tmyTaskDetail.getId());
                }
            }
        }
        return true;
    }

    public List<Map> listFinanceBuyerReport(Map map){
        return baseMapper.listFinanceBuyerReport(map);
    }
}
