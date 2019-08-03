package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.UploadEntity;
import br.com.mpr.ws.exception.UploadServiceException;
import br.com.mpr.ws.service.UploadService;
import br.com.mpr.ws.vo.UploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            throws UploadServiceException {

        return uploadService.upload(form);
    }

}
