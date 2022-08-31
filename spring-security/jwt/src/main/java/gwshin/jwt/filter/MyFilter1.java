package gwshin.jwt.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getMethod().equals("POST")) {
            String headerAuth = req.getHeader("Authorization");
            log.info("headerAuth={}", headerAuth);

//            if (headerAuth.equals("gwshin")) {
//                chain.doFilter(req, res);
//            } else {
//                PrintWriter out = res.getWriter();
//                out.println("인증안됨");
//            }
            chain.doFilter(req, res);
        }

    }
}
