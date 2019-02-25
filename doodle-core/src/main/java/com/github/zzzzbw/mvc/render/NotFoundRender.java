package com.github.zzzzbw.mvc.render;

import com.github.zzzzbw.mvc.RequestHandlerChain;

import javax.servlet.http.HttpServletResponse;

/**
 * 渲染404
 *
 * @author zzzzbw
 * @since 2018/6/13 21:19
 */
public class NotFoundRender implements Render {


    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        handlerChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
