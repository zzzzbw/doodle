package com.github.zzzzbw.mvc.render;

import com.github.zzzzbw.mvc.RequestHandlerChain;

import javax.servlet.http.HttpServletResponse;

/**
 * 渲染500
 *
 * @author zzzzbw
 * @since 2018/6/13 21:24
 */
public class InternalErrorRender implements Render {

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        handlerChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
