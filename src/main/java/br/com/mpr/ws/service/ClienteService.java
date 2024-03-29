package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.EnderecoEntity;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.exception.ClienteServiceException;

import java.util.List;

/**
 *
 */
public interface ClienteService {

    ClienteEntity saveCliente(ClienteEntity cliente) throws ClienteServiceException;

    ClienteEntity getClienteById(long l);

    List<ClienteEntity> listAllCliente();

    ClienteEntity getClienteByKeyDevice(String keyDevice);

    List<EnderecoEntity> getEnderecosByIdCliente(Long idCliente);

    EnderecoEntity getEnderecoPrincipalByIdCliente(Long idCliente);
}
