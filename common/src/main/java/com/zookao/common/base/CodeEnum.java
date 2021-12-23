package com.zookao.common.base;

public enum CodeEnum {
    SUCCESS("10000", "操作成功"),
    ERROR("20000", "操作失败"),
    IDENTIFICATION_ERROR("20001", "身份异常"),
    DATA_ERROR("20002", "业务错误"),
    PARAM_ERROR("20003", "参数错误"),
    EMAIL_ERROR("20004", "邮箱格式错误"),
    INVALID_USERNAME_PASSWORD("20005", "用户名或密码错误"),
    INVALID_RE_PASSWORD("20006", "两次输入密码不一致"),
    INVALID_USER("20007", "用户不存在"),
    INVALID_USER_EXIST("20008", "用户已存在"),
    INVALID_ROLE("20009", "角色不存在"),
    MOBILE_ERROR("20010", "手机号格式错误"),
    INVALID_TOKEN("20011","令牌无效"),
    INVALID_CAPTCHA("20012","验证码不正确");

    private String respCode;
    private String respMsg;

    CodeEnum(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public String getCode() {
        return respCode;
    }

    public String getMsg() {
        return respMsg;
    }
}
