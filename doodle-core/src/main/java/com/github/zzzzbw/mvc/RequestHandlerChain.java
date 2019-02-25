package com.github.zzzzbw.mvc;

import com.github.zzzzbw.mvc.handler.Handler;
import com.github.zzzzbw.mvc.render.DefaultRender;
import com.github.zzzzbw.mvc.render.InternalErrorRender;
import com.github.zzzzbw.mvc.render.Render;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * http请求处理链
 *
 * @author zzzzbw
 * @since 2018/6/13 16:07
 */
@Data
@Slf4j
public class RequestHandlerChain {
    /**
     * Handler迭代器
     * {@link Handler}
     */
    private Iterator<Handler> handlerIt;

    /**
     * 请求request
     * {@link HttpServletRequest}
     */
    private HttpServletRequest request;

    /**
     * 请求response
     * {@link HttpServletResponse}
     */
    private HttpServletResponse response;

    /**
     * 请求http方法
     */
    private String requestMethod;

    /**
     * 请求http路径
     */
    private String requestPath;

    /**
     * 请求状态码
     */
    private int responseStatus;

    /**
     * 请求结果处理器
     */
    private Render render;

    public RequestHandlerChain(Iterator<Handler> handlerIt, HttpServletRequest request, HttpServletResponse response) {
        this.handlerIt = handlerIt;
        this.request = request;
        this.response = response;
        this.requestMethod = request.getMethod();
        this.requestPath = request.getPathInfo();
        this.responseStatus = HttpServletResponse.SC_OK;
    }

    /**
     * 执行请求链
     */
    public void doHandlerChain() {
        try {
            while (handlerIt.hasNext()) {
                if (!handlerIt.next().handle(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("doHandlerChain error", e);
            render = new InternalErrorRender();
        }
    }

    /**
     * 执行处理器
     */
    public void doRender() {
        if (null == render) {
            render = new DefaultRender();
        }
        try {
            render.render(this);
        } catch (Exception e) {
            log.error("doRender", e);
            throw new RuntimeException(e);
        }
    }
}
