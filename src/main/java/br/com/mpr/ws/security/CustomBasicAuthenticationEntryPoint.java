package br.com.mpr.ws.security;

import br.com.mpr.ws.constants.MprConstants;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.ErrorMessageVo;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {
        //Authentication failed, send error response.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
        PrintWriter writer = response.getWriter();
        ErrorMessageVo error = new ErrorMessageVo(403, "Authentication failed");
        writer.println(ObjectUtils.toJson(error));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(MprConstants.REALM_MPR);
        super.afterPropertiesSet();
    }

}
