package br.com.mpr.ws.rest.handler;

import br.com.mpr.ws.exception.ServiceException;
import br.com.mpr.ws.vo.ErrorMessageVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Created by wagner on 7/13/18.
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Log LOG = LogFactory.getLog(CustomizedResponseEntityExceptionHandler.class);


    /****
     *
     * ERRO DE VALIDACAO DE OBJETOS
     *
     **/

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error("m=handleMethodArgumentNotValid, error=" + ex.getMessage(), ex);
        ErrorMessageVo errorDetails = new ErrorMessageVo(status.value(),new Date(), "Erro de validação",
                getStrMessageErrors(ex.getBindingResult()));
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    private String [] getStrMessageErrors(BindingResult bindingResult) {
        String [] errors = new String[bindingResult.getAllErrors().size()];

        int count = 0;
        for (ObjectError error : bindingResult.getAllErrors() ){
            errors[count++] = error.getDefaultMessage();
        }
        return errors;
    }


    /****
     *
     * ERRO DE NEGOCIO
     *
     **/
    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<ErrorMessageVo> handleServiceException(ServiceException ex, WebRequest request) {
        LOG.error("m=handleAdminException, error=" + ex.getMessage(), ex);
        ErrorMessageVo errorDetails = new ErrorMessageVo(HttpStatus.BAD_REQUEST.value(),
                new Date(),"Erro de negócio",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    /****
     *
     * ACESSO NEGADO
     *
     **/
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ErrorMessageVo> handleException(AccessDeniedException ex, WebRequest request) {
        LOG.error("m=handleException, error=" + ex.getMessage());
        ErrorMessageVo errorDetails = new ErrorMessageVo(HttpStatus.FORBIDDEN.value(),
                new Date(),"AccessDenied",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }


    /****
     *
     * ERRO INTERNO
     *
     **/
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ErrorMessageVo> handleException(RuntimeException ex, WebRequest request) {
        LOG.error("m=handleException, error=" + ex.getMessage(), ex);
        ErrorMessageVo errorDetails = new ErrorMessageVo(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),"Erro interno",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity<ErrorMessageVo> handleException(Throwable ex, WebRequest request) {
        LOG.error("m=handleException, error=" + ex.getMessage(), ex);
        ErrorMessageVo errorDetails = new ErrorMessageVo(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),"Erro interno",
                ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
