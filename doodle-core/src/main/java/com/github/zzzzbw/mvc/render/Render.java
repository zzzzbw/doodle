package com.github.zzzzbw.mvc.render;

import com.github.zzzzbw.mvc.RequestHandlerChain;

/**
 * 渲染请求结果 interface
 *
 * @author zzzzbw
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
