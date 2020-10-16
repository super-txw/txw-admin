package com.itmk.result;

import com.itmk.status.StatusCode;

/**
 * 返回工具类
 */
public class ResultUtils {
    public static  ResultVo vo(String msg,int code,Object data){
        return new ResultVo(msg,code,data);
    }
    //无参数返回值
    public static ResultVo success(){
       return vo(null, StatusCode.SUCCESS_CODE,null);
    }
    //一个参数返回值
    public static ResultVo success(String msg){
        return vo(msg, StatusCode.SUCCESS_CODE,null);
    }
    //全部参数
    public static ResultVo success(String msg,int code,Object data){
        return vo(msg, code,data);
    }
    //提示信息和返回数据
    public static ResultVo success(String msg,Object data){
        return vo(msg, StatusCode.SUCCESS_CODE,data);
    }
    //返回失败
    //无参数返回值
    public static ResultVo error(){
        return vo(null, StatusCode.ERROR_CODE,null);
    }
    public static ResultVo error(String msg){
        return vo(msg, StatusCode.ERROR_CODE,null);
    }
    public static ResultVo error(String msg,int code,Object data){
        return vo(msg, code,data);
    }
    //带分页返回
    public static ResultPageVo success(String msg,int pageNum,int pageSize,int total,Object data){
        return new ResultPageVo(msg,StatusCode.SUCCESS_CODE,pageNum,pageSize,total,data);

    }
}
