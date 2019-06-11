package cn.jeeweb.web.ebp.finance.task;

import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailQuestionService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.finance.entity.TfinanceBuyerReport;
import cn.jeeweb.web.ebp.finance.service.TfinanceBuyerReportService;
import cn.jeeweb.web.ebp.shop.util.TaskUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.ebp.shop.task
 * @title:
 * @description: 定时计算财务金额
 * @author: iteryi
 * @date: 2019/2/28 16:52
 */
@Component("buyerReportTask")
public class BuyerReportTask {

    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TfinanceBuyerReportService tfinanceBuyerReportService;
    @Autowired
    private TmyTaskDetailQuestionService tmyTaskDetailQuestionService;

    public void run(){
        try{
            System.out.println("买手统计汇总！");
            Double multiple = 2.0;
            try{
                multiple = Double.parseDouble(DictUtils.getDictValue("一个任务单佣金","tasknum",multiple+""));
            }catch (Exception e){

            }
            // 获取当日需统计汇总数据
            Map par_map = new HashMap();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);    //得到前一天
            Date date =calendar.getTime();
//            date = DateUtils.parseDate("2019-03-11","yyyy-MM-dd");
            String[] creates = TaskUtils.whereNewDate(DateUtils.formatDate(date,"yyyy-MM-dd"),DateUtils.formatDate(date,"yyyy-MM-dd"));
            par_map.put("createDate1",creates[0]);
            par_map.put("createDate2",creates[1]);
            //判断当日是否统计，如统计则跳出
            int count = tfinanceBuyerReportService.sumTfinanceBuyerReport(par_map);
            if(count>0){
                return;
            }
            //获取当日用户订单数据
            List<Map> list = tmyTaskDetailService.listFinanceBuyerReport(par_map);
            //获取当日用户问题单数据
            List<Map> list_Question = tmyTaskDetailQuestionService.listFinanceBuyerReport(par_map);

            List<TfinanceBuyerReport> flist = new ArrayList<TfinanceBuyerReport>();
            long starttime = System.currentTimeMillis();
            for (Map map:list){
                TfinanceBuyerReport fbr = new TfinanceBuyerReport();
                fbr.setCountcreatedate(DateUtils.parseDate((map.get("countCreateDate")==null?"":map.get("countCreateDate")).toString()));
                fbr.setBuyerid((map.get("userid")==null?"":map.get("userid")).toString());
                fbr.setBuyername((map.get("buyerName")==null?"":map.get("buyerName")).toString());
                fbr.setSumprice(Double.parseDouble((map.get("sumPrice")==null?"0":map.get("sumPrice")).toString()));
                fbr.setSumorderprice(Double.parseDouble((map.get("sumOrderPrice")==null?"0":map.get("sumOrderPrice")).toString()));
                fbr.setSumdeliveryprice(Double.parseDouble((map.get("sumDeliveryPrice")==null?"0":map.get("sumDeliveryPrice")).toString()));
                fbr.setSumfinishprice(Double.parseDouble((map.get("sumFinishPrice")==null?"0":map.get("sumFinishPrice")).toString()));
                fbr.setSumnum(Long.parseLong((map.get("sumNum")==null?"0":map.get("sumNum")).toString()));
                fbr.setSumfinishnum(Long.parseLong((map.get("sumFinishNum")==null?"0":map.get("sumFinishNum")).toString()));
                fbr.setBrokerage(multiple);
                fbr.setStatus("0");
                for (Map map_Question:list_Question){
                    if(fbr.getBuyerid().equals(map_Question.get("buyerid"))){
                        fbr.setErrornum(Long.parseLong((map_Question.get("errornum")==null?"0":map_Question.get("errornum")).toString()));
                        fbr.setErrorprice(Double.parseDouble((map_Question.get("errorprice")==null?"0":map_Question.get("errorprice")).toString()));
                        break;
                    }
                }
                flist.add(fbr);
            }
            tfinanceBuyerReportService.insertBatch(flist);
            long endtime = System.currentTimeMillis();
            long usetime = (endtime - starttime)/1000;
            System.out.println("买手统计汇总耗时："+usetime+"秒");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
