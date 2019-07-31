package cn.jeeweb.web.ebp.enums;

/**
 * 系统参数
 *
 */
public enum SysConfigParamEnum {
    PLATFORM_LOGISTICS_COMMISSION("ORDER_OUT_STORE_COMMISSION"),
    ;
    public String configParam;

    SysConfigParamEnum(String configParam) {
        this.configParam = configParam;
    }
}
