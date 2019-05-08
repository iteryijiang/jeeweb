package cn.jeeweb.web.ebp.buyer.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailQuestion;

public interface TmyTaskDetailQuestionService extends ICommonService<TmyTaskDetailQuestion> {

    public boolean saveTmyTaskDetailQuestion(TmyTaskDetail tmyTaskDetail) throws Exception;
}
