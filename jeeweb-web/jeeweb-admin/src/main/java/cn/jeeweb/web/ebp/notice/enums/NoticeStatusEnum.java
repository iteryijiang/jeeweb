package cn.jeeweb.web.ebp.notice.enums;

/**
 * 消息通知状态
 * Created by YAOTENGJIAO on 2019/5/16.
 */
public enum NoticeStatusEnum {
    UNREAD(0,"未读"),
    READ(1,"已读"),
    EXPIRE(2,"过期"),
    ;

    public int code;
    public String name;
    private NoticeStatusEnum(int code,String name){
        this.code=code;
        this.name=name;
    }
}
