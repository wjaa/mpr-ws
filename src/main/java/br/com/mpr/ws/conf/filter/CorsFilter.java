package br.com.mpr.ws.conf.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 *
 */
@Configuration
@Order(Ordered. HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    private static final Log LOG = LogFactory.getLog(CorsFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", origin != null && origin.contains("ws") ? "" : origin );
        response.setHeader("Vary", "Origin");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Authorization, Content-Type, Accept, X-CSRF-TOKEN");
        response.setHeader("Access-Control-Max-Age", "3600");
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
        Enumeration<String> e = request.getHeaderNames();
        LOG.trace("---------------------------------------------");
        while (e.hasMoreElements()){
            String key = e.nextElement();
            LOG.trace("doFilter, " + key + ":" + request.getHeader(key));
        }
        LOG.trace("---------------------------------------------");




    }

    public void destroy() {
        System.out.println("destroy filter");
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Init filter");
    }

}
