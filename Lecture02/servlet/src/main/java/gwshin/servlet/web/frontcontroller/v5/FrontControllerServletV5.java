package gwshin.servlet.web.frontcontroller.v5;

import gwshin.servlet.web.frontcontroller.ModelView;
import gwshin.servlet.web.frontcontroller.MyView;
import gwshin.servlet.web.frontcontroller.v3.ControllerV3;
import gwshin.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import gwshin.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import gwshin.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import gwshin.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import gwshin.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import gwshin.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import gwshin.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import gwshin.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());

    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 핸들러 조회
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 2. 핸들러를 처리할 수 있는 핸들러 어댑터 조회
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // 3. handle(handler)
        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();

        // 6. viewResolver 호출
        MyView view = viewResolver(viewName);

        // 8. render(model) 호출
        view.render(mv.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        // 7. MyView 반환
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}
