package com.github.zzzzbw.mvc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * ControllerInfo 存储Controller相关信息
 *
 * @author zzzzbw
 * @since 2018/5/25 10:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerInfo {
    /**
     * controller类
     */
    private Class<?> controllerClass;

    /**
     * 执行的方法
     */
    private Method invokeMethod;

    /**
     * 方法参数别名对应参数类型
     */
    private Map<String, Class<?>> methodParameter;
}
