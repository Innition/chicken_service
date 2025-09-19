package cn.lazylhxzzy.save_upload.utils;

public enum ResultCodeEnum
{
    SUCCESS(200, "成功"),
    GLOBAL_ERROR(1000, "全局错误"),
    PARAM_ERROR(1001, "参数错误"),
    DB_ERROR(1002, "数据库错误"),
    FILE_ERROR(1003, "文件错误"),
    UNKNOWN_ERROR(1004, "未知错误"),
    USER_NOT_EXIST(1101, "用户不存在"),
    USER_ALREADY_EXIST(1102, "用户已存在"),
    USER_OR_PWD_ERROR(1103, "用户名或密码错误"),
    USER_NOT_LOGIN(1104, "用户未登录"),
    USER_NOT_AUTH(1105, "用户无权限"),
    USER_LOCKED(1106, "用户已被锁定"),
    USER_PWD_ERROR(1107, "密码错误次数过多，请稍后再试"),
    USER_PWD_UPDATE_SUCCESS(1108, "密码修改成功"),
    USER_PWD_UPDATE_FAIL(1109, "密码修改失败"),
    USER_ROLE_ERROR(1110, "用户角色错误"),
    USER_ROLE_UPDATE_SUCCESS(1111, "用户角色修改成功"),
    USER_ROLE_UPDATE_FAIL(1112, "用户角色修改失败"),
    USER_ROLE_NOT_EXIST(1113, "用户角色不存在"),
    USER_ROLE_ALREADY_EXIST(1114, "用户角色已存在"),
    USER_LOGIN_ERROR(1115, "用户登录失败"),
    PHONE_ALREADY_EXIST(1116, "手机号已存在"),
    NAME_ALREADY_EXIST(1117, "用户名已存在"),
    USER_PWD_EMPTY(1118, "密码不能为空"),
    FILE_EMPTY(1201, "文件为空"),
    FILE_TYPE_ERROR(1202, "文件类型错误"),
    FILE_SIZE_ERROR(1203, "文件大小超出限制"),
    FILE_UPLOAD_SUCCESS(1204, "文件上传成功"),
    FILE_UPLOAD_FAIL(1205, "文件上传失败"),
    FILE_DOWNLOAD_SUCCESS(1206, "文件下载成功"),
    FILE_DOWNLOAD_FAIL(1207, "文件下载失败"),
    FILE_NOT_EXIST(1208, "文件不存在"),
    NO_FILE_EXIST(1209, "无文件存在"), SAVE_NOT_EXIST(1301, "存档不存在, 请先上传文件");


    private Integer statusCode;
    private String msg;

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    ResultCodeEnum(Integer statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
