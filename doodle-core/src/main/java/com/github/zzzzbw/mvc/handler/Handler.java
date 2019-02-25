package com.github.zzzzbw.mvc.handler;

import com.github.zzzzbw.mvc.RequestHandlerChain;

/**
 * 请求执行器 Handler
 *
 * @author zzzzbw
 * @since 2018/6/12 17:52
 */
public interface Handler {
    /**
     * 请求的执行器
     *
     * @param handlerChain {@link RequestHandlerChain}
     * @return 是否执行下一个
     * @throws Exception Exception
     */
    boolean handle(final RequestHandlerChain handlerChain) throws Exception;
}
