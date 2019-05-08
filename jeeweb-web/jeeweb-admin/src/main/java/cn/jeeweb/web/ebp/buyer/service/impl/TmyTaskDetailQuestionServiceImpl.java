package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.utils.BeanUtils;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailQuestion;
import cn.jeeweb.web.ebp.buyer.mapper.TmyTaskDetailQuestionMapper;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailQuestionService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("TmyTaskDetailQuestionService")
public class TmyTaskDetailQuestionServiceImpl extends CommonServiceImpl<TmyTaskDetailQuestionMapper, TmyTaskDetailQuestion> implements TmyTaskDetailQuestionService {

    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;


    public boolean saveTmyTaskDetailQuestion(TmyTaskDetail tmyTaskDetail) throws Exception{
        if(tmyTaskDetail!=null){
            TmyTaskDetailQuestion tmyTaskDetailQuestion = new TmyTaskDetailQuestion();
            BeanUtils.copyProperties(tmyTaskDetail, tmyTaskDetailQuestion);
            insert(tmyTaskDetailQuestion);
        }
        return true;
    }
}
