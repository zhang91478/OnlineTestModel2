package cn.xu419.jdbc.dao;

import java.sql.SQLException;

public class DaoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DaoException() {
    }

    public DaoException(String message){
        super(message);
    }

    public DaoException(String message,Throwable cause){
        super(message,cause);
    }
}
