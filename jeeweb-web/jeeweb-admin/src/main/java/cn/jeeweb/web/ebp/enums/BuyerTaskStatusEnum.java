package cn.jeeweb.web.ebp.enums;

/**
 * 买手任务状态
 *
 * @authr:YAOTENGJIAO
 * @date:2019/5/8
 */

public enum BuyerTaskStatusEnum {
    RECEIVE_TASK(1,"待下单"),//已接单、待下单
    WAITING_SEND(2,"待发货"),//已下单、待发货
    WAITING_ACCEPT(3,"待收货"),//已发货、待收货
    FINISH(4,"已收货"),//已收货、完成
    CHARGEBACK(5,"退单"),
    ;


    public int code;
    public String codeName;

    BuyerTaskStatusEnum(int code, String codeName){
        this.code=code;
        this.codeName=codeName;
    }

    public final static BuyerTaskStatusEnum valueOfCode(int code){
        for(BuyerTaskStatusEnum obj:BuyerTaskStatusEnum.values()){
            if(obj.code == code){
                return obj;
            }
        }
        return null;
    }
}
