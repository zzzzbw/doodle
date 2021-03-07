package com.github.zzzzbw.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

/**
 * 类操作工具类
 *
 * @author zzzzbw
 * @since 2018/5/29 12:27
 */
@Slf4j
public final class ClassUtil {

    /**
     * file形式url协议
     */
    public static final String FILE_PROTOCOL = "file";

    /**
     * jar形式url协议
     */
    public static final String JAR_PROTOCOL = "jar";

    /**
     * 获取classLoader
     *
     * @return 当前ClassLoader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取Class
     *
     * @param className class全名
     * @return Class
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化class
     *
     * @param className class全名
     * @param <T>       class的类型
     * @return 类的实例化
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className) {
        try {
            Class<?> clazz = loadClass(className);
            return (T) clazz.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化class
     *
     * @param clazz Class
     * @param <T>   class的类型
     * @return 类的实例化
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置类的属性值
     *
     * @param field  属性
     * @param target 类实例
     * @param value  值
     */
    public static void setField(Field field, Object target, Object value) {
        setField(field, target, value, true);
    }

    /**
     * 设置类的属性值
     *
     * @param field      属性
     * @param target     类实例
     * @param value      值
     * @param accessible 是否允许设置私有属性
     */
    public static void setField(Field field, Object target, Object value, boolean accessible) {
        field.setAccessible(accessible);
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            log.error("setField error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取包下类集合
     *
     * @param basePackage 包的路径
     * @return 类集合
     */
    public static Set<Class<?>> getPackageClass(String basePackage) {
        URL url = getClassLoader()
                .getResource(basePackage.replace(".", "/"));
        if (null == url) {
            throw new RuntimeException("无法获取项目路径文件");
        }

        try {
            if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
                File file = new File(url.getFile());
                Path basePath = file.toPath();
                return Files.walk(basePath)
                        .filter(path -> path.toFile().getName().endsWith(".class"))
                        .map(path -> getClassByPath(path, basePath, basePackage))
                        .collect(Collectors.toSet());
            } else if (url.getProtocol().equalsIgnoreCase(JAR_PROTOCOL)) {
                // 若在 jar 包中，则解析 jar 包中的 entry
                JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                return jarURLConnection.getJarFile()
                        .stream()
                        .filter(jarEntry -> jarEntry.getName().endsWith(".class"))
                        .filter(jarEntry -> getJarEntryFileClassPath(jarEntry).startsWith(basePackage))
                        .map(ClassUtil::getClassByJar)
                        .collect(Collectors.toSet());
            }
            return Collections.emptySet();
        } catch (IOException e) {
            log.error("load package error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 从Path获取Class
     *
     * @param classPath   类的路径
     * @param basePath    包目录的路径
     * @param basePackage 包名
     * @return 类
     */
    private static Class<?> getClassByPath(Path classPath, Path basePath, String basePackage) {
        String packageName = classPath.toString().replace(basePath.toString(), "");
        String className = (basePackage + packageName)
                .replace("/", ".")
                .replace("\\", ".")
                .replace(".class", "");
        // 如果class在根目录要去除最前面的.
        className = className.charAt(0) == '.' ? className.substring(1) : className;
        return loadClass(className);
    }

    /**
     * 从jar包获取Class
     *
     * @param jarEntry jar文件
     * @return 类
     */
    private static Class<?> getClassByJar(JarEntry jarEntry) {
        String jarEntryClassPath = getJarEntryFileClassPath(jarEntry);
        // 获取类名
        String className = jarEntryClassPath.substring(0, jarEntryClassPath.lastIndexOf("."));
        return loadClass(className);
    }

    /**
     * github/zzzzbw/App.class -> github.zzzzbw.App.class
     **/
    private static String getJarEntryFileClassPath(JarEntry jarEntry){
        return jarEntry.getName().replaceAll("/", ".");
    }
}
