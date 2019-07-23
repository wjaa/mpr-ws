package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.UploadEntity;
import br.com.mpr.ws.exception.ProdutoPreviewServiceException;
import br.com.mpr.ws.rest.aop.SessionController;
import br.com.mpr.ws.service.ProdutoPreviewService;
import br.com.mpr.ws.service.UploadService;
import br.com.mpr.ws.vo.PreviewForm;
import br.com.mpr.ws.vo.ProdutoVo;
import br.com.mpr.ws.vo.UploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/core")
public class UploadRest extends BaseRest {

    @Autowired
    private UploadService uploadService;


    //@PreAuthorize(value = "hasAuthority('USER')")
    @RequestMapping(value = "/upload",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public UploadEntity upload(@ModelAttribute UploadForm form)
            throws ProdutoPreviewServiceException {

        return uploadService.upload(form);
    }

}
