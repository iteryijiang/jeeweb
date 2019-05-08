package cn.jeeweb.web.ebp.enums;

/**
 * 买手任务状态
 *
 * @authr:YAOTENGJIAO
 * @date:2019/5/8
 */

public enum BuyerTaskStatusRnum {
    RECEIVE_TASK(1,"已接单、待下单"),
    WAITING_SEND(2,"已下单、待发货"),
    WAITING_ACCEPT(3,"已发货、待收货"),
    FINISH(4,"已收货、完成"),
    ;


    public int code;
    public String codeName;

    BuyerTaskStatusRnum(int code, String codeName){
        this.code=code;
        this.codeName=codeName;
    }
}
