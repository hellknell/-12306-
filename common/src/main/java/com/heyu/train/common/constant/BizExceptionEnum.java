package com.heyu.train.common.constant;

public enum BizExceptionEnum {
    // 成功
    SUCCESS("200", "成功"),
    PARAM_ERROR("400", "参数异常"),
    TOKEN_INVALID_ERROR("401", "无效的token"),
    TOKEN_CHECK_ERROR("401", "token验证失败，请重新登录"),
    PARAM_LOST_ERROR("4001", "参数缺失"),

    SYSTEM_ERROR("500", "系统异常"),
    MEMBER_MOBILE_ALREADY_EXISTS("501", "手机号已存在"),
    MEMBER_NO_EXISTS("502", "用户不存在"),
    CODE_ERROR("503", "短信验证码错误"),
    USER_EXIST_ERROR("5001", "用户已存在"),
    USER_NOT_LOGIN("5002", "用户未登录"),
    USER_ACCOUNT_ERROR("5003", "账号或密码错误"),
    USER_NOT_EXIST_ERROR("5004", "用户不存在"),
    PARAM_PASSWORD_ERROR("5005", "原密码输入错误"),
    MONEY_LIMIT_ERROR("5006", "账户余额不足"),
    TRAIN_EXIST_ERROR("5007", "火车已存在"),
    TRAIN_STATION_EXIST_ERROR("5008", "车站已存在"),

    TRAIN_CARRIAGE_EXIST_ERROR("5009", "车厢已存在"),
    NO_DAILY_TRAIN("50010", "没有车次可生成"),
    PASSENGER_COUNT_LIMIT("50011", "最多只能添加50个乘客"),
    NO_TRAINS("50012", "对应车次不存在"),
    NO_TICKETS("500113", "对应车票不存在"),
    TICKETS_LIMITS("500114", "余票不足");
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;

    }

    BizExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
