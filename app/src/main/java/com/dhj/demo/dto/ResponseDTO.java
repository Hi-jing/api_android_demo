package com.dhj.demo.dto;

/**
 * 接口数据传输对象
 * <p>
 * 作为请求统一响应返回，方便前端逻辑处理
 *
 * @author denghaijing
 */
public class ResponseDTO<T> {

    /**
     * 业务状态码
     * <p>
     * 标识是否业务处理成功，用于前端判断逻辑处理
     * 0：成功 -1：失败
     */
    private Integer code;

    /**
     * 提示消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public ResponseDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDTO() {
    }

    public ResponseDTO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
