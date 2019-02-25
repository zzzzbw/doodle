package com.github.zzzzbw.mvc.render;

import com.github.zzzzbw.mvc.RequestHandlerChain;

/**
 * 默认渲染 200
 *
 * @author zzzzbw
 * @since 2018/6/13 16:53
 */
public class DefaultRender implements Render {

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        int status = handlerChain.getResponseStatus();
        handlerChain.getResponse().setStatus(status);
    }
}
