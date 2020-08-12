package Enum;

public enum ResultCode {
    SUCCESS(200);
    private Integer code;
    ResultCode(int code) {
        this.code = code;
    }
}
