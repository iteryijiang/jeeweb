package cn.jeeweb.web.ebp.enums;

/**
 * 通告类型
 * 
 * @author ytj
 *
 */
public enum NotificationTypeRangeEnum {
	SHOP_INNER(1,"内部商家通告"),
	SHOP_OUTER(2,"外部商家通告"),
	BUYER(3,"买手通告"),;
    public int code;
    public String codeName;

    NotificationTypeRangeEnum(int code, String codeName){
        this.code=code;
        this.codeName=codeName;
    }

    public final static NotificationTypeRangeEnum valueOfCode(int code) {
        for (NotificationTypeRangeEnum obj:NotificationTypeRangeEnum.values()){
            if (obj.code == code){
                return obj;
            }
        }
        return null;
    }
}
