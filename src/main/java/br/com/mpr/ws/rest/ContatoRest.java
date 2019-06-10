package br.com.mpr.ws.rest;

import br.com.mpr.ws.entity.ContatoEntity;
import br.com.mpr.ws.exception.ContatoServiceException;
import br.com.mpr.ws.service.ContatoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/core")
public class ContatoRest extends BaseRest{

    private ContatoService contatoService;


    @RequestMapping(value = "/contato/save",
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
            method = RequestMethod.POST)
    public ContatoEntity saveContato(@RequestBody @Valid ContatoEntity contato) throws ContatoServiceException {
        return this.contatoService.save(contato);
    }

}
