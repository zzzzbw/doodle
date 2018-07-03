package com.zbw.mvc.render;

import com.zbw.mvc.handler.RequestHandlerChain;

import javax.servlet.http.HttpServletResponse;

/**
 * 渲染500
 *
 * @author zbw
 * @since 2018/6/13 21:24
 */
public class InternalErrorRender implements Render {

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        handlerChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
