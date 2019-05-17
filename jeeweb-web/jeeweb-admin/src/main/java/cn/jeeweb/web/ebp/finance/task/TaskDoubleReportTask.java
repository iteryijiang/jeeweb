package cn.jeeweb.web.ebp.finance.task;

import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.finance.entity.TfinanceTaskDoubleReport;
import cn.jeeweb.web.ebp.finance.service.TfinanceBuyerReportService;
import cn.jeeweb.web.ebp.finance.service.TfinanceTaskDoubleReportService;
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
 * @description: 定时计算单双链接
 * @author: iteryi
 * @date: 2019/2/28 16:52
 */
@Component("taskDoubleReportTask")
public class TaskDoubleReportTask {

    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TfinanceBuyerReportService tfinanceBuyerReportService;
    @Autowired
    private TfinanceTaskDoubleReportService tfinanceTaskDoubleReportService;

    public void run(){
        try{
            // 获取当日需统计汇总数据
            Map par_map = new HashMap();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);    //得到前一天
            Date date =calendar.getTime();
//            date = DateUtils.parseDate("2019-03-11","yyyy-MM-dd");
            String[] creates = TaskUtils.whereNewDate(DateUtils.formatDate(date,"yyyy-MM-dd"),DateUtils.formatDate(date,"yyyy-MM-dd"));
            par_map.put("receivingdate1",creates[0]);
            par_map.put("receivingdate2",creates[1]);
            //判断当日是否统计，如统计则跳出
            int count = tfinanceTaskDoubleReportService.sumTfinanceTaskDoubleReport(par_map);
            if(count>0){
                return;
            }
            //获取当日单数链接
            List<Map> list = tmyTaskDetailService.listTfinanceTaskDoubleReport(par_map);

            List<TfinanceTaskDoubleReport> flist = new ArrayList<TfinanceTaskDoubleReport>();
            long starttime = System.currentTimeMillis();
            for (Map map:list){
                TfinanceTaskDoubleReport ftdr = new TfinanceTaskDoubleReport();
                ftdr.setCountcreatedate(DateUtils.parseDate((map.get("countCreateDate")==null?"":map.get("countCreateDate")).toString()));//	date	0	0	-1	0	0	0	0		0					0	0
                ftdr.setUserid((map.get("userid")==null?"":map.get("userid")).toString());//	varchar	32	0	-1	0	0	0	0		0	用户ID	utf8	utf8_general_ci		0	0
                ftdr.setLoginname((map.get("loginname")==null?"":map.get("loginname")).toString());//	varchar	32	0	-1	0	0	0	0		0	用户号	utf8	utf8_general_ci		0	0
                ftdr.setUsername((map.get("username")==null?"":map.get("username")).toString());//	varchar	32	0	-1	0	0	0	0		0	用户名称	utf8	utf8_general_ci		0	0
                ftdr.setUsertype((map.get("usertype")==null?"":map.get("usertype")).toString());//	varchar	32	0	-1	0	0	0	0		0	用户类型	utf8	utf8_general_ci		0	0
                ftdr.setDoublenum(Integer.parseInt((map.get("doublenum")==null?"0":map.get("doublenum")).toString()));//	int	10	0	-1	0	0	0	0		0	双链接数				0	0
                ftdr.setOnenum(Integer.parseInt((map.get("onenum")==null?"0":map.get("onenum")).toString()));//	int	10	0	-1	0	0	0	0		0	单链接数				0	0
                flist.add(ftdr);
            }
            tfinanceTaskDoubleReportService.insertBatch(flist);
            long endtime = System.currentTimeMillis();
            long usetime = (endtime - starttime)/1000;
            System.out.println("链接统计汇总耗时："+usetime+"秒");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
