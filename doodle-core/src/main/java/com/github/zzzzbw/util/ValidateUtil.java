package com.github.zzzzbw.util;

import java.util.Collection;
import java.util.Map;

/**
 * 验证相关工具类
 *
 * @author zzzzbw
 * @since 2018/5/24 13:00
 */
public final class ValidateUtil {

    /**
     * Object是否为null
     *
     * @param obj Object
     * @return 是否为空
     */
    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    /**
     * String是否为null或""
     *
     * @param obj String
     * @return 是否为空
     */
    public static boolean isEmpty(String obj) {
        return (obj == null || "".equals(obj));
    }

    /**
     * Array是否为null或者size为0
     *
     * @param obj Array
     * @return 是否为空
     */
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    /**
     * Collection是否为null或size为0
     *
     * @param obj Collection
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * Map是否为null或size为0
     *
     * @param obj Map
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * Object是否不为null
     *
     * @param obj Object
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * String是否不为null或""
     *
     * @param obj String
     * @return 是否不为空
     */
    public static boolean isNotEmpty(String obj) {
        return !isEmpty(obj);
    }

    /**
     * Array是否不为null或者size为0
     *
     * @param obj Array
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object[] obj) {
        return !isEmpty(obj);
    }

    /**
     * Collection是否不为null或size为0
     *
     * @param obj Collection
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Collection<?> obj) {
        return !isEmpty(obj);
    }

    /**
     * Map是否不为null或size为0
     *
     * @param obj Map
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Map<?, ?> obj) {
        return !isEmpty(obj);
    }

}
