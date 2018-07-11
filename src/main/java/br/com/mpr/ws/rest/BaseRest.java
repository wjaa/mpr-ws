package br.com.mpr.ws.rest;

import br.com.mpr.ws.vo.ErrorMessageVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

public abstract class BaseRest {

    private static final Log LOG = LogFactory.getLog(BaseRest.class);


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ErrorMessageVo handleException(Exception e, HttpServletResponse response) {
        LOG.error("handleException, error=" + e.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
        return new ErrorMessageVo(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
    }

}
