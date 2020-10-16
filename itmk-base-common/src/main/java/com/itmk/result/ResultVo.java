package com.itmk.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 普通返回值封装
 */
@Data
// 所有的无参构造函数
@AllArgsConstructor
public class ResultVo<T> {
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回状态码
     */
    private int code;
    /**
     * 返回的数据
     */
    private T data;
}
