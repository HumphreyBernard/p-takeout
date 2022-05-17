package ivan.takeout.filter;

import com.alibaba.fastjson.JSON;
import ivan.takeout.common.BaseContext;
import ivan.takeout.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * 检查用户是否完成登录
 *
 * @author Maximilian_Li
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    /**
     * 定义进行路径比较的匹配器，支持通配符匹配
     */
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 定义不需要处理的请求路径
     */
    String[] validUrls = new String[]{
            "/employee/login",
            "/employee/logout",
            "/backend/**",
            "/front/**",
            "/user/login",
            "/user/sendMsg"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*
        1. 获取本次请求的uri
        2. 判断本次请求是否需要处理，如果不需要请求则直接放行
        3. 判断登陆状态，如果已经登陆则直接放行；如果未登录则返回未登录结果
         */
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String requestURI = req.getRequestURI();
        log.info(requestURI);
        boolean canGo = check(requestURI);

        if (canGo) {
            log.info("本次请求 {} 不需要处理", requestURI);
            filterChain.doFilter(req, resp);
        } else {
            Long empId = (Long) req.getSession().getAttribute("employee");
            Long userId = (Long) req.getSession().getAttribute("user");
            if (!Objects.equals(null, empId)) {
                // 后台登录
                BaseContext.setCurrentId(empId);
                log.info("后台工作人员已登录，id为 {}", empId);
                filterChain.doFilter(req, resp);
            } else if (!Objects.equals(null, userId)) {
                // 前台登录
                BaseContext.setCurrentId(userId);
                log.info("前台顾客已登录，id为 {}", userId);
                filterChain.doFilter(req, resp);
            } else {
                // 登录失败
                log.info("请求 {} 被拦截", requestURI);
                // 通过输出流的方式向客户端页面响应数据
                PrintWriter writer = resp.getWriter();
                writer.write(JSON.toJSONString(R.error("NOTLOGIN")));
            }
        }
    }


    /**
     * 检查本次请求是否需要放行
     */
    public boolean check(String requestURI) {
        for (String uri : validUrls) {
            boolean ismatch = PATH_MATCHER.match(uri, requestURI);
            if (ismatch) {
                return true;
            }
        }
        return false;
    }
}
