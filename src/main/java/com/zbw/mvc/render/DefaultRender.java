package com.zbw.mvc.render;

import com.zbw.mvc.handler.RequestHandlerChain;

/**
 * 默认渲染 200
 *
 * @author zbw
 * @since 2018/6/13 16:53
 */
public class DefaultRender implements Render {

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        int status = handlerChain.getResponseStatus();
        handlerChain.getResponse().setStatus(status);
    }
}
