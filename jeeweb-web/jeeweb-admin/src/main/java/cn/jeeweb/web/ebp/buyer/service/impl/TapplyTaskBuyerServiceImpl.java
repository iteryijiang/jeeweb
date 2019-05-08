package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyer;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.mapper.TapplyTaskBuyerMapper;
import cn.jeeweb.web.ebp.buyer.service.TapplyTaskBuyerService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.enums.BuyerTaskStatusRnum;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Calendar;

@Transactional
@Service("TapplyTaskBuyerService")
public class TapplyTaskBuyerServiceImpl extends CommonServiceImpl<TapplyTaskBuyerMapper, TapplyTaskBuyer> implements TapplyTaskBuyerService {

    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TtaskBaseService ttaskBaseService1;


    @Transactional
    public void insertTapplyTaskBuyer(TapplyTaskBuyer obj) {
        //获取任务信息
        TmyTaskDetail taskObj=tmyTaskDetailService.selectById(obj.getBuyerTaskId());
        if (taskObj == null || Integer.valueOf(taskObj.getTaskstate()) >= BuyerTaskStatusRnum.WAITING_SEND.code || taskObj.getErrorStatus() == YesNoEnum.YES.code) {
            throw new RuntimeException("未获取到任务信息或是任务当前状态不允许执行该操作");
        }
        obj.setApplyStatus(YesNoEnum.NO.code);
        obj.setBuyerId(taskObj.getBuyerid());
        obj.setShopTaskId(taskObj.getTaskid());
        TtaskBase taskBaseObj=ttaskBaseService1.selectById(taskObj.getTaskid());
        obj.setShopId(taskBaseObj.getShopid());
        obj.setApplyTime(Calendar.getInstance().getTime());
        baseMapper.insert(obj);
        //更改任务单异常
        tmyTaskDetailService.upTaskErrorStatus(obj.getBuyerTaskId(),YesNoEnum.YES.code,obj.getCreateBy().getId());
    }

    @Override
    public void updateTapplyTaskBuyerStatus(TapplyTaskBuyer obj){
        int num=baseMapper.updateTapplyTaskBuyerStatus(obj);
        if(num!=1){
            throw  new RuntimeException("更改申请状态失败");
        }
    }

    @Override
    public Page<TapplyTaskBuyer> selectApplyPageList(Queryable queryable, Wrapper<TapplyTaskBuyer> wrapper){
        QueryToWrapper<TapplyTaskBuyer> queryToWrapper = new QueryToWrapper<TapplyTaskBuyer>();
        queryToWrapper.parseCondition(wrapper, queryable);
        queryToWrapper.parseSort(wrapper, queryable);
        Pageable pageable = queryable.getPageable();
        com.baomidou.mybatisplus.plugins.Page<TapplyTaskBuyer> page = new com.baomidou.mybatisplus.plugins.Page<TapplyTaskBuyer>(pageable.getPageNumber(), pageable.getPageSize());
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectApplyPageList(page, wrapper));
        return new PageImpl<TapplyTaskBuyer>(page.getRecords(), queryable.getPageable(), page.getTotal());
    }
}
