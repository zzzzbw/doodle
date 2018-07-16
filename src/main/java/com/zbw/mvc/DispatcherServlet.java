package com.zbw.mvc;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DispatcherServlet 所有http请求都由此Servlet转发
 *
 * @author zbw
 * @since 2018/5/24 17:55
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {

    private ControllerHandler controllerHandler = new ControllerHandler();

    private ResultRender resultRender = new ResultRender();

    /**
     * 执行请求
     *
     * @param req  {@link HttpServletRequest}
     * @param resp {@link HttpServletResponse}
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求编码方式
        req.setCharacterEncoding("UTF-8");
        //获取请求方法和请求路径
        String requestMethod = req.getMethod();
        String requestPath = req.getPathInfo();
        log.info("[DoodleConfig] {} {}", requestMethod, requestPath);
        if (requestPath.endsWith("/")) {
            requestPath = requestPath.substring(0, requestPath.length() - 1);
        }

        ControllerInfo controllerInfo = controllerHandler.getController(requestMethod, requestPath);
        log.info("{}", controllerInfo);
        if (null == controllerInfo) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resultRender.invokeController(req, resp, controllerInfo);
    }
}
