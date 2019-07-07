package cn.jeeweb.web.ebp.enums;

/**
 * 佣金类型
 *
 * @author : ytj
 * @desc:
 * @date : 2019/7/7 13:08
 */
public enum CommissionTypeEnum {
    FIXED(1,"固定佣金"),
    BILL_OF_LADING(2,"按单提成"),;
    public int code;
    public String codeName;

    private CommissionTypeEnum(int code, String codeName){
        this.code=code;
        this.codeName=codeName;
    }

    public final static CommissionTypeEnum valueOfCode(int code) {
        for (CommissionTypeEnum obj: CommissionTypeEnum.values()){
            if (obj.code == code){
                return obj;
            }
        }
        return null;
    }
}
