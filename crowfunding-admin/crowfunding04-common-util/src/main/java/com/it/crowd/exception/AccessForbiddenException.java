package com.it.crowd.exception;

/**
 * @author wyj
 * @description 表示用户没有登陆就访问受保护资源时抛出的异常
 * @create 2020-12-08
 */
public class AccessForbiddenException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AccessForbiddenException() {
        super();
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
