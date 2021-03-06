package cn.jeeweb.web.ebp.shop.spider;

import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.shop.service.TsequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class TsequenceSpider {

    private static TsequenceService tsequenceService;

    @Autowired
    public TsequenceSpider(TsequenceService tsequenceService){
        TsequenceSpider.tsequenceService = tsequenceService;
    }

    /**
     * 商品流水号
     * */
    public static String getShopNo()throws Exception{
        String bhkey = "S"+DateUtils.formatDate(new Date(),"yyyyMMdd");
        long st =  tsequenceService.nextNum(bhkey);
        return  bhkey+String.format("%04d", st);
    }

    /**
     * 任务流水号
     * */
    public static String getTaskNo()throws Exception{
        String bhkey = "T"+DateUtils.formatDate(new Date(),"yyyyMMdd");
        long st =  tsequenceService.nextNum(bhkey);
        return  bhkey+String.format("%04d", st);
    }
    /**
     * 充值流水号
     * */
    public static String getRechargeNo()throws Exception{
        String bhkey = "C"+DateUtils.formatDate(new Date(),"yyyyMMdd");
        long st =  tsequenceService.nextNum(bhkey);
        return  bhkey+String.format("%04d", st);
    }
    /**
     * 任务详情流水号
     * */
    public static String getTaskDetailNo()throws Exception{
        String bhkey = "D"+DateUtils.formatDate(new Date(),"yyyyMMdd");
        long st =  tsequenceService.nextNum(bhkey);
        return  bhkey+String.format("%05d", st);
    }
}