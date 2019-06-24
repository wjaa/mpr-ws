package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.ProdutoPreviewServiceException;
import br.com.mpr.ws.rest.aop.SessionController;
import br.com.mpr.ws.service.ProdutoPreviewService;
import br.com.mpr.ws.vo.PreviewForm;
import br.com.mpr.ws.vo.ProdutoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/core")
public class ProdutoPreviewRest extends BaseRest {

    @Autowired
    private ProdutoPreviewService produtoPreviewService;


    @PreAuthorize(value = "hasAuthority('USER')")
    @RequestMapping(value = "/preview/addImagem",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.PUT)
    public ProdutoVo addImagem(@RequestBody @Valid PreviewForm previewForm, OAuth2Authentication user)
            throws ProdutoPreviewServiceException {

        ClienteEntity cliente = validateUser(user);
        previewForm.setIdCliente(cliente.getId());

        return produtoPreviewService.addFoto(previewForm);
    }


    @RequestMapping(value = "/preview/addImagem/{sessionToken}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.PUT)
    @SessionController
    public ProdutoVo addImagem(@RequestBody @Valid PreviewForm previewForm, @PathVariable String sessionToken)
            throws ProdutoPreviewServiceException {
        previewForm.setSessionToken(sessionToken);
        return this.produtoPreviewService.addFoto(previewForm);
    }


}
