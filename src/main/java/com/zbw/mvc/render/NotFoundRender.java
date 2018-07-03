package com.zbw.mvc.render;

import com.zbw.mvc.handler.RequestHandlerChain;

import javax.servlet.http.HttpServletResponse;

/**
 * 渲染404
 *
 * @author zbw
 * @since 2018/6/13 21:19
 */
public class NotFoundRender implements Render {


    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        handlerChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
