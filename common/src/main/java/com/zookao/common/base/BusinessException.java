package com.zookao.common.base;

public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 3455708556465120030L;

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(String msg,String code){
        super(msg+":--:"+code);
    }
}