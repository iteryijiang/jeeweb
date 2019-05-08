package cn.jeeweb.web.ebp.enums;

/**
 * 异常任务处理方式
 *
 * @authr:YAOTENGJIAO
 * @date:2019/5/8
 */
public enum UnusualTaskHandleMethodEnum {
    BUYERTASK_CANCEL(1,"撤销买手任务"),
    SHOPTASK_CANCEL(2,"撤销商家任务"),
    ;
    /**
     * 编码
     */
    public int code;
    /**
     * 编码名称
     */
    public String codeName;

    UnusualTaskHandleMethodEnum(int code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public final static UnusualTaskHandleMethodEnum valueOfCode(int code) {
        for (UnusualTaskHandleMethodEnum obj:UnusualTaskHandleMethodEnum.values()){
            if (obj.code == code){
                return obj;
            }
        }
        return null;
    }
}
