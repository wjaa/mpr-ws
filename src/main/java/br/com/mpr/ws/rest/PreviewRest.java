package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.UploadEntity;
import br.com.mpr.ws.exception.ProdutoPreviewServiceException;
import br.com.mpr.ws.exception.UploadServiceException;
import br.com.mpr.ws.service.ProdutoPreviewService;
import br.com.mpr.ws.service.UploadService;
import br.com.mpr.ws.vo.ImagemPreviewVo;
import br.com.mpr.ws.vo.UploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/core")
public class PreviewRest extends BaseRest {

    @Autowired
    private ProdutoPreviewService produtoPreviewService;


    //@PreAuthorize(value = "hasAuthority('USER')")
    @RequestMapping(value = "/preview/{uploadToken}/{produtoRef}",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.GET)
    public ImagemPreviewVo preview(@PathVariable String uploadToken,
                                   @PathVariable String produtoRef) throws ProdutoPreviewServiceException {

        return produtoPreviewService.generatePreview(uploadToken, produtoRef);
    }

}
