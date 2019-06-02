package cn.jeeweb.web.ebp.enums;

/**
 * 是与否的枚举值
 *
 * @authr:YAOTENGJIAO
 * @date:2019/5/8
 */

public enum YesNoEnum {
    YES(1,"是"),NO(0,"否"),;
    public int code;
    public String codeName;

    YesNoEnum(int code, String codeName){
        this.code=code;
        this.codeName=codeName;
    }

    public final static YesNoEnum valueOfCode(int code) {
        for (YesNoEnum obj:YesNoEnum.values()){
            if (obj.code == code){
                return obj;
            }
        }
        return null;
    }
}
