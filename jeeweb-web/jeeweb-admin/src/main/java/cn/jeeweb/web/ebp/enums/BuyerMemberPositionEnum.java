package cn.jeeweb.web.ebp.enums;

/**
 * 买手在分组中的职位
 *
 * @authr:YAOTENGJIAO
 * @date:2019/5/8
 */

public enum BuyerMemberPositionEnum {
    MEMBER(0,"组员"),
    GROUP_LEADER(1,"组长"),;
    public int code;
    public String codeName;

    BuyerMemberPositionEnum(int code, String codeName){
        this.code=code;
        this.codeName=codeName;
    }

    public final static BuyerMemberPositionEnum valueOfCode(int code) {
        for (BuyerMemberPositionEnum obj: BuyerMemberPositionEnum.values()){
            if (obj.code == code){
                return obj;
            }
        }
        return null;
    }
}
