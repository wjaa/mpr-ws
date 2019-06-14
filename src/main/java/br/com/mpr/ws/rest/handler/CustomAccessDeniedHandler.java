package br.com.mpr.ws.rest.handler;

import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.ErrorMessageVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {

    private static final Log LOG = LogFactory.getLog(CustomAccessDeniedHandler.class);


    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        if (!response.isCommitted()) {

            LOG.error("m=handle, error=" + e.getMessage(), e);
            ErrorMessageVo errorDetails = new ErrorMessageVo(HttpStatus.FORBIDDEN.value(),
                    new Date(),"AccessDenied",
                    e.getMessage());

            response.setContentType("application/json; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(ObjectUtils.toJson(errorDetails));
            response.getWriter().close();
        }


    }
}
