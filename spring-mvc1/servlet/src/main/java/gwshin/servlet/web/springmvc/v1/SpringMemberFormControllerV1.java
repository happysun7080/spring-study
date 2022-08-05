package gwshin.servlet.web.springmvc.v1;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// 방법 1. @Controller 사용
@Controller

// 방법 2. @Component @RequestMapping 사용
//@Component @RequestMapping

// 방법 3. Bean으로 직접 등록
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
