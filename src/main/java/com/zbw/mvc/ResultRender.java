package com.zbw.mvc;

import com.alibaba.fastjson.JSON;
import com.zbw.Doodle;
import com.zbw.core.BeanContainer;
import com.zbw.mvc.annotation.ResponseBody;
import com.zbw.mvc.bean.ModelAndView;
import com.zbw.util.CastUtil;
import com.zbw.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 结果执行器
 *
 * @author zbw
 * @since 2018/7/16 15:50
 */
@Slf4j
public class ResultRender {

    private BeanContainer beanContainer;

    public ResultRender() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行Controller的方法
     *
     * @param req            {@link HttpServletRequest}
     * @param resp           {@link HttpServletResponse}
     * @param controllerInfo {@link ControllerInfo}
     */
    public void invokeController(HttpServletRequest req, HttpServletResponse resp, ControllerInfo controllerInfo) {
        Map<String, String> requestParam = getRequestParams(req);
        List<Object> methodParams = instantiateMethodArgs(controllerInfo.getMethodParameter(), requestParam);

        Object controller = beanContainer.getBean(controllerInfo.getControllerClass());
        Method invokeMethod = controllerInfo.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result;
        try {
            if (methodParams.size() == 0) {
                result = invokeMethod.invoke(controller);
            } else {
                result = invokeMethod.invoke(controller, methodParams.toArray());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resultResolver(controllerInfo, result, req, resp);
    }

    /**
     * 获取http中的参数
     *
     * @param request {@link HttpServletRequest}
     * @return http中请求参数map
     */
    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        //GET和POST方法是这样获取请求参数的
        request.getParameterMap().forEach((paramName, paramsValues) -> {
            if (ValidateUtil.isNotEmpty(paramsValues)) {
                paramMap.put(paramName, paramsValues[0]);
            }
        });
        // TODO: Body、Path、Header等方式的请求参数获取
        return paramMap;
    }

    /**
     * 实例化方法参数
     *
     * @param methodParams  方法的参数
     * @param requestParams request中的参数
     * @return 方法参数实例集合
     */
    private List<Object> instantiateMethodArgs(Map<String, Class<?>> methodParams, Map<String, String> requestParams) {
        return methodParams.keySet().stream().map(paramName -> {
            Class<?> type = methodParams.get(paramName);
            String requestValue = requestParams.get(paramName);
            Object value;
            if (null == requestValue) {
                value = CastUtil.primitiveNull(type);
            } else {
                value = CastUtil.convert(type, requestValue);
                // TODO: 实现非原生类的参数实例化
            }
            return value;
        }).collect(Collectors.toList());
    }


    /**
     * Controller方法执行后返回值解析
     *
     * @param controllerInfo {@link ControllerInfo}
     * @param result         返回值
     * @param req            {@link HttpServletRequest}
     * @param resp           {@link HttpServletResponse}
     */
    private void resultResolver(ControllerInfo controllerInfo, Object result, HttpServletRequest req, HttpServletResponse resp) {
        if (null == result) {
            return;
        }
        boolean isJson = controllerInfo.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        if (isJson) {
            // 设置响应头
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            // 向响应中写入数据
            try (PrintWriter writer = resp.getWriter()) {
                writer.write(JSON.toJSONString(result));
                writer.flush();
            } catch (IOException e) {
                log.error("转发请求失败", e);
                // TODO: 异常统一处理，400等...
            }
        } else {
            String path;
            if (result instanceof ModelAndView) {
                ModelAndView mv = (ModelAndView) result;
                path = mv.getView();
                Map<String, Object> model = mv.getModel();
                if (ValidateUtil.isNotEmpty(model)) {
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
            } else if (result instanceof String) {
                path = (String) result;
            } else {
                throw new RuntimeException("返回类型不合法");
            }
            try {
                req.getRequestDispatcher(Doodle.getConfiguration().getResourcePath() + path).forward(req, resp);
            } catch (Exception e) {
                log.error("转发请求失败", e);
                // TODO: 异常统一处理，400等...
            }
        }
    }
}
