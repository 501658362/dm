package org.cuiyang.dm;

/**
 * 大漠插件异常信息
 *
 * @author cuiyang
 */
public class DmException extends RuntimeException {

    private static final long serialVersionUID = -8190016153946289383L;

    public DmException() {
    }

    public DmException(String message) {
        super(message);
    }

    public DmException(String message, Throwable cause) {
        super(message, cause);
    }

    public DmException(Throwable cause) {
        super(cause);
    }

    public DmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
