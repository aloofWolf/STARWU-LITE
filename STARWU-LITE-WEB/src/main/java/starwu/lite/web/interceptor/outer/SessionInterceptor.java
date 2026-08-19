package starwu.lite.web.interceptor.outer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import starwu.lite.distributed.session.core.SessionCore;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.config.distributed.session.SessionExcludeUrlConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    private final SessionCore core;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        UserSession<?> session = core.validateSession(request);
        log.info("session信息:{}", session.toString());
        return true;
    }

    public List<String> getExcexcludeUrls(){
        return core.getExcexcludeUrls();
    }
}