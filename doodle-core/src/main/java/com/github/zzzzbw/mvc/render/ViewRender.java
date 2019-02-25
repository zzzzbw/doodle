package com.github.zzzzbw.mvc.render;

import com.github.zzzzbw.mvc.RequestHandlerChain;
import com.github.zzzzbw.Doodle;
import com.github.zzzzbw.mvc.bean.ModelAndView;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 渲染页面
 *
 * @author zzzzbw
 * @since 2018/6/13 16:53
 */
@Slf4j
public class ViewRender implements Render {

    private ModelAndView mv;

    public ViewRender(Object mv) {
        if (mv instanceof ModelAndView) {
            this.mv = (ModelAndView) mv;
        } else if (mv instanceof String) {
            this.mv = new ModelAndView().setView((String) mv);
        } else {
            throw new RuntimeException("返回类型不合法");
        }
    }

    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        HttpServletRequest req = handlerChain.getRequest();
        HttpServletResponse resp = handlerChain.getResponse();
        String path = mv.getView();
        Map<String, Object> model = mv.getModel();
        model.forEach(req::setAttribute);
        req.getRequestDispatcher(Doodle.getConfiguration().getViewPath() + path).forward(req, resp);

    }
}
