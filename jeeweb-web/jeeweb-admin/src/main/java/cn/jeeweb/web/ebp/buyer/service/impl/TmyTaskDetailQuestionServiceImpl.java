package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.utils.BeanUtils;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailQuestion;
import cn.jeeweb.web.ebp.buyer.mapper.TmyTaskDetailQuestionMapper;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailQuestionService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("TmyTaskDetailQuestionService")
public class TmyTaskDetailQuestionServiceImpl extends CommonServiceImpl<TmyTaskDetailQuestionMapper, TmyTaskDetailQuestion> implements TmyTaskDetailQuestionService {

    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TtaskBaseService ttaskBaseService;


    public boolean saveTmyTaskDetailQuestion(TtaskBase ttaskBase,TmyTaskDetail tmyTaskDetail) throws Exception{
        if(ttaskBase!=null){
            List<TmyTaskDetail> list = tmyTaskDetailService.selBaseIdMyTaskDetailList(ttaskBase.getId());
            if(list!=null&&!list.isEmpty()){
                for (TmyTaskDetail ttd:list) {
                    if("1".equals(ttd.getTaskstate())||"2".equals(ttd.getTaskstate())){
                        TmyTaskDetailQuestion tmyTaskDetailQuestion = new TmyTaskDetailQuestion();
                        BeanUtils.copyProperties(tmyTaskDetail, tmyTaskDetailQuestion);
                        insert(tmyTaskDetailQuestion);
                        tmyTaskDetailService.deleteById(tmyTaskDetail.getId());
                    }
                }
            }
            ttaskBase.setStatus("2");
            ttaskBaseService.updateById(ttaskBase);
        }
        if(tmyTaskDetail!=null){
            if("1".equals(tmyTaskDetail.getTaskstate())||"2".equals(tmyTaskDetail.getTaskstate())){
                TmyTaskDetailQuestion tmyTaskDetailQuestion = new TmyTaskDetailQuestion();
                BeanUtils.copyProperties(tmyTaskDetail, tmyTaskDetailQuestion);
                insert(tmyTaskDetailQuestion);
                tmyTaskDetailService.deleteById(tmyTaskDetail.getId());
            }
        }
        return true;
    }
}
