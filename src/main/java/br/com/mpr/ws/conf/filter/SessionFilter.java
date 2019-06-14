package br.com.mpr.ws.conf.filter;

import br.com.mpr.ws.exception.SessionServiceException;
import br.com.mpr.ws.service.SessionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 *
 */
@Configuration
@Order
public class SessionFilter implements Filter {

    @Autowired
    private SessionService sessionService;

    private static final Log LOG = LogFactory.getLog(SessionFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String sessionToken = request.getParameter("session_token");
        try {
            if (!StringUtils.isEmpty(sessionToken)){
                LOG.debug("m=doFilter, um session_token foi encontrado. Executando renew na session...");
                sessionService.renewSession(sessionToken);
            }
        } catch (SessionServiceException ex) {
            LOG.error("Erro ao executar o renew na session: ", ex);
        }
        chain.doFilter(req, res);
    }

    public void destroy() {}

    public void init(FilterConfig config) throws ServletException {}

}
