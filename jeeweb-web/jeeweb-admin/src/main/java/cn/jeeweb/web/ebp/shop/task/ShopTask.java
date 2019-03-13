package cn.jeeweb.web.ebp.shop.task;

import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.ebp.shop.task
 * @title:
 * @description: 邮件任务，主要检查发送失败的
 * @author: iteryi
 * @date: 2019/2/28 16:52
 */
@Component("shopTask")
public class ShopTask {

    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;

    public void run(){
        try{
            System.out.println("商户自动发货！");
            // 获取  已经确认下单 超过24小时 ，还未发货的订单
            //SELECT * from t_my_task_detail where taskstate='2' and timestampdiff(hour,orderdate,now()) >= 24;

            //upTaskState('3',)
            List<TmyTaskDetail> list = tmyTaskDetailService.listNoSendGood();
            System.out.println("开始自动发货："+list.size()+"条");
            long starttime = System.currentTimeMillis();
            for (TmyTaskDetail tmyTaskDetail:list){
                tmyTaskDetailService.upTaskState("3", tmyTaskDetail,"");
            }
            long endtime = System.currentTimeMillis();
            long usetime = (endtime - starttime)/1000;
            System.out.println("自动发货总耗时："+usetime+"秒");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
