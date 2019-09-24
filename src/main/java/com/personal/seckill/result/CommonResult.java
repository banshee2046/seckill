package com.personal.seckill.result;

public class CommonResult<T> {

    private int code;
    private String msg;
    private T data;

    private CommonResult(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private CommonResult(CodeMsg codeMsg) {
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(data);
    }

    public static <T> CommonResult<T> error(CodeMsg codeMsg) {
        return new CommonResult<T>(codeMsg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public T getData() {
        return data;
    }

}
