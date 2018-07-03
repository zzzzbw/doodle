package com.zbw.mvc.render;

import com.zbw.mvc.handler.RequestHandlerChain;

/**
 * 渲染请求结果 interface
 *
 * @author zbw
 * @since 2018/6/12 18:10
 */
public interface Render {
    /**
     * 执行渲染
     *
     * @param handlerChain {@link RequestHandlerChain}
     * @throws Exception Exception
     */
    void render(RequestHandlerChain handlerChain) throws Exception;
}
