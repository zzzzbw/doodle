package com.zbw.mvc;

import com.zbw.mvc.handler.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DispatcherServlet 所有http请求都由此Servlet转发
 *
 * @author zbw
 * @since 2018/5/24 17:55
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {

    /**
     * 请求执行链
     */
    private final List<Handler> HANDLER = new ArrayList<>();

    /**
     * 初始化Servlet
     *
     * @throws ServletException ServletException
     */
    @Override
    public void init() throws ServletException {
        HANDLER.add(new PreRequestHandler());
        HANDLER.add(new SimpleUrlHandler(getServletContext()));
        HANDLER.add(new JspHandler(getServletContext()));
        HANDLER.add(new ControllerHandler());
    }

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
        RequestHandlerChain handlerChain = new RequestHandlerChain(HANDLER.iterator(), req, resp);
        handlerChain.doHandlerChain();
        handlerChain.doRender();
    }
}
