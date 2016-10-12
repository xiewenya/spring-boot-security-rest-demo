package com.baojinsuo.base;

/**
 * Created by bresai on 2016/10/3.
 */
public enum ResponseCode {
    success("success", "成功"),
    login_required("login_first","请先登录"),
    id_null("id_null","登陆已经失效，请关闭页面重新进入"),
    sina_account_not_open("sina_account_not_open","请先完成实名认证"),
    real_name_not_set("real_name_not_set","请先完成实名认证"),
    user_status_not_found("user_status_not_found", "实名状态未找到"),
    card_not_bind("card_not_bind", "请先绑定银行卡"),
    card_already_bind("card_already_bind", "银行卡已绑定，请不要重复绑卡"),
    gesture_password_not_set("gesture_password_not_set", "请先设置手势密码"),
    pay_password_not_set("pay_password_not_set", "请先设置支付密码"),
    password_empty("password_empty", "密码不能为空"),
    password_format_error("password_format_error", "密码格式错误"),
    password_not_correct("password_not_correct", "原密码错误"),
    old_password_not_correct("old_password_not_correct", "原密码错误"),
    validation_request_error("validation_request_error","请求参数错误"),
    validation_code_not_found("validation_code_not_found","验证码未找到或已失效,请重新获取"),
    validation_code_not_correct("validation_code_not_correct", "验证码不正确"),
    login_failed("login_failed", "用户名或密码错误"),
    user_not_found("user_not_found", "用户不存在"),
    mobile_already_used("mobile_already_used", "该手机号已经注册"),
    logout_success("logout_success", "登出成功"),
    request_param_error("request_param_error","请求参数错误"),

    unknown_error("unknown_error","未知错误"),
    result_ok("success","成功"),
    invalid_request("invalid_request","无效的调用"),
    database_error("database_error","数据库错误"),
    bonus_code_error("bonus_code_error","加新码不正确"),
    bonus_product_not_exist("bonus_product_not_exist","加薪码不正确或加新标尚未开售，请耐心等待!"),
    product_not_exist("product_not_exist","标的不存在"),
    order_amount_up_limit("order_amount_up_limit","金额超过上限"),
    product_low_limit("product_low_limit","订购金额必须大于等于最低购买额度"),
    product_high_limit("product_high_limit","订购金额必须小于等于最高可购买额度"),
    product_not_open("product_not_open","产品不在售卖期"),
    internal_server_error("internal_server_error", "网络延迟,请重新提交"),
    state_error("state_error", "订单状态与业务逻辑不一致"),
    order_not_deal_error("order_not_deal_error","尚有订单未处理"),
    captcha_error("withdraw_amount_error", "验证码错误"),
    withdraw_amount_error("withdraw_amount_error", "提现金额过低"),
    user_grade_low_limit_error("user_grade_low_limit_error","你当前的账户等级无法购买该产品"),
    not_found("not_found", "未找到相关信息"),
    current_ransom_success("current_ransom_success", "活期赎回提交成功,处理中"),
    current_reconciliation("current_reconciliation","系统对账中暂不开放购买赎回"),
    sina_password_already_set("sina_password_already_set","新浪支付密码已经设置" ),
    logout_failed("logout_failed", "登出失败"),
    validator_type_error("validator_type_error", "该验证器不能用于这个类型的参数"),
    duplicated("duplicated", "已经被注册"),
    sms_send_failed("sms_send_failed", "验证码发送失败");

    private String code;
    private String 	desc;

    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }

    ResponseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

