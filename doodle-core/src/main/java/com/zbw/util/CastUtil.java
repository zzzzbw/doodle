package com.zbw.util;


/**
 * 转换工具类
 *
 * @author zbw
 * @since 2018/5/29 16:21
 */
public final class CastUtil {

    /**
     * String类型转换成对应类型
     *
     * @param type  转换的类
     * @param value 值
     * @return 转换后的Object
     */
    public static Object convert(Class<?> type, String value) {
        if (isPrimitive(type)) {
            if (ValidateUtil.isEmpty(value)) {
                return primitiveNull(type);
            }

            if (type.equals(int.class) || type.equals(Integer.class)) {
                return Integer.parseInt(value);
            } else if (type.equals(String.class)) {
                return value;
            } else if (type.equals(Double.class) || type.equals(double.class)) {
                return Double.parseDouble(value);
            } else if (type.equals(Float.class) || type.equals(float.class)) {
                return Float.parseFloat(value);
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                return Long.parseLong(value);
            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                return Boolean.parseBoolean(value);
            } else if (type.equals(Short.class) || type.equals(short.class)) {
                return Short.parseShort(value);
            } else if (type.equals(Byte.class) || type.equals(byte.class)) {
                return Byte.parseByte(value);
            }
            return value;
        } else {
            throw new RuntimeException("暂时不支持非原生");
        }
    }

    /**
     * 返回基本数据类型的空值
     *
     * @param type 类
     * @return 对应的空值
     */
    public static Object primitiveNull(Class<?> type) {
        if (type.equals(int.class) || type.equals(double.class) ||
                type.equals(short.class) || type.equals(long.class) ||
                type.equals(byte.class) || type.equals(float.class)) {
            return 0;
        }
        if (type.equals(boolean.class)) {
            return false;
        }
        return null;
    }


    /**
     * 判定是否基本数据类型(包括包装类)
     *
     * @param type 类
     * @return 是否为基本数据类型
     */
    public static boolean isPrimitive(Class<?> type) {
        return type == boolean.class
                || type == Boolean.class
                || type == double.class
                || type == Double.class
                || type == float.class
                || type == Float.class
                || type == short.class
                || type == Short.class
                || type == int.class
                || type == Integer.class
                || type == long.class
                || type == Long.class
                || type == String.class
                || type == byte.class
                || type == Byte.class
                || type == char.class
                || type == Character.class;
    }

}
