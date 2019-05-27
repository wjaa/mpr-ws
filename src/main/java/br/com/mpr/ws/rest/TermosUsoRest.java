package br.com.mpr.ws.rest;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/core")
public class TermosUsoRest extends BaseRest {


    @RequestMapping(value = "/termos/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE,
            method = RequestMethod.GET)
    public byte[] getPdf() throws IOException {
        InputStream inputStream = TermosUsoRest.class.getClassLoader()
                .getResourceAsStream("termos_uso.pdf");
        return IOUtils.toByteArray(inputStream);
    }


}
