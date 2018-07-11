package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.exception.ClienteServiceException;

/**
 *
 */
public interface ClienteService {

    ClienteEntity saveCliente(ClienteEntity cliente) throws ClienteServiceException;

    ClienteEntity getClienteById(long l);
}
