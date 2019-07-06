package cn.jeeweb.web.ebp.exception;

/**
 * 自定义异常信息
 *
 */
public class MyProcessException extends  RuntimeException {

    private static final long serialVersionUID = 7886879810834395424L;

    public static final int CODE_UNKNOWN_ERROR = -1;

    protected int code;

    public MyProcessException(int code) {
        this.code = code;
    }

    public MyProcessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public MyProcessException(String message, Throwable e) {
        super(message, e);
    }

    public MyProcessException(int code, String message, Throwable e) {
        super(message, e);
        this.code = code;
    }

    public MyProcessException(int code, Throwable e) {
        super(e);
        this.code = code;
    }

    public MyProcessException(Throwable e) {
        super(e);
        this.code = CODE_UNKNOWN_ERROR;
    }

    public MyProcessException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }
}
