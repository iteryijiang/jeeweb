package cn.jeeweb.web.ebp.shop.util;

import cn.jeeweb.common.query.data.Condition;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.StringUtils;

import java.util.Date;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.ebp.shop.task
 * @title:
 * @description: 任务处理工具类
 * @author: iteryi
 * @date: 2019/3/3 11:00
 */
public class TaskUtils {

    public static String[] whereNewDate(String create1,String create2){
        if(StringUtils.isEmpty(create1)&&StringUtils.isEmpty(create2)){
            Date date = new Date();
            create1 = DateUtils.formatDate(date,"yyyy-MM-dd");
            create2 = DateUtils.formatDate(date,"yyyy-MM-dd");
        }else if(StringUtils.isEmpty(create1)&&StringUtils.isNotEmpty(create2)){
            create1 = create2;
        }else if(StringUtils.isNotEmpty(create1)&&StringUtils.isEmpty(create2)){
            create2 = create1;
        }
        create2 = create2+" 23:59:59";
        String[] retu = {create1,create2};
        return retu;
    }

    public static String[] whereDate(Condition.Filter filter){
        String create1 = "";
        String create2 = "";
        if(filter==null){
            return whereNewDate(create1, create2);
        }
        Object o = filter.getValue();
        if (o instanceof String[]) {
            String[] ss = (String[]) o;
            if (ss != null && ss.length >= 2) {
                create1 = ss[0];
                create2 = ss[1];
            } else if (ss != null && ss.length >= 1) {
                create1 = ss[0];
                create2 = ss[0];
            }
        }
        create2 = create2+" 23:59:59";
        String[] retu = {create1,create2};
        return retu;
    }

}
