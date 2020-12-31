package com.it.crowd.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyj
 * @description 统一整个项目Ajax请求放回的结果（未来也可以用于分布式架构各个模块间调用时统一返回的类型）
 * @create 2020-12-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultEntity<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    // 封装当前请求处理的结果是失败还是成功
    private String result;

    // 请求处理失败时返回的错误消息
    private String message;

    // 返回的数据
    private T data;

    /**
     * 请求处理成功且不需要返回数据时使用的工具方法
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithoutData(){
        return new ResultEntity<Type>(SUCCESS,null,null);
    }


    /**
     * 请求处理成功且需要返回数据时使用的工具方法
     * @param data
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data){
        return new ResultEntity<Type>(SUCCESS,null,data);
    }

    /**
     * 请求处理失败后使用的工具方法
     * @param message
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> fail(String message){
        return new ResultEntity<Type>(FAILED,message,null);
    }

}
