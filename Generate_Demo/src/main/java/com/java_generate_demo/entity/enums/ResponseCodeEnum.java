package com.java_generate_demo.entity.enums;



public enum ResponseCodeEnum {
    CODE_200(200, "请求成功"),
    CODE_404(404, "资源不存在"),
    CODE_500(500, "服务器异常，请联系管理员"),
    CODE_600(600, "参数错误"),
    CODE_601(601, "信息已存在");

    private Integer code;

    private String msg;

    ResponseCodeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }

}
