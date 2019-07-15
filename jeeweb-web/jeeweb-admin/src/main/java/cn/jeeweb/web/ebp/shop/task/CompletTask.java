package cn.jeeweb.web.ebp.shop.task;

import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Component("completTask")
public class CompletTask {

    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;

    public void run(){
        try{
            System.out.println("自动收货！");
            // 获取  已发货 超过24小时 ，还未收货 的订单
            Map map = new HashMap<>();
            map.put("taskstate",3);
            List<TmyTaskDetail> list = tmyTaskDetailService.listNoSendGood(map);
            System.out.println("开始自动收货完成："+list.size()+"条");
            long starttime = System.currentTimeMillis();
            List yesPictureList = new ArrayList();
            for (TmyTaskDetail tmyTaskDetail:list){
                if("1".equals(tmyTaskDetail.getIspicture())){
                    yesPictureList.add(tmyTaskDetail.getBuyerno());
                }
            }

            for (TmyTaskDetail tmyTaskDetail:list){
                if("1".equals(tmyTaskDetail.getIspicture())||yesPictureList.contains(tmyTaskDetail.getBuyerno())){
                    continue;
                }
                tmyTaskDetailService.upTaskState("4", tmyTaskDetail,"");
            }
            long endtime = System.currentTimeMillis();
            long usetime = (endtime - starttime)/1000;
            System.out.println("自动收货总耗时："+usetime+"秒");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
