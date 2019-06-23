package cn.jeeweb.web.ebp.enums;

public enum EcommerceEnum {
    JD(1,"京东"),
    ;


    public int code;
    public String codeName;

    EcommerceEnum(int code, String codeName){
        this.code=code;
        this.codeName=codeName;
    }
}
