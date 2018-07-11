package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.exception.ClienteServiceException;
import br.com.mpr.ws.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private CommonDao commonDao;

    @Override
    public ClienteEntity saveCliente(ClienteEntity cliente) throws ClienteServiceException {

        if ( !Utils.validateCpf(cliente.getCpf()) ){
            throw new ClienteServiceException("Cpf inválido!");
        }

        if ( !Utils.validateEmail(cliente.getEmail()) ){
            throw new ClienteServiceException("Email inválido!");
        }


        ClienteEntity cliCpf = commonDao.findByPropertiesSingleResult(ClienteEntity.class,new String[]{"cpf"},
                new Object[]{cliente.getCpf()});

        if (cliCpf != null && !cliCpf.getId().equals(cliente.getId())){
            throw new ClienteServiceException("Já existe um cliente cadastrado com esse CPF.");
        }

        ClienteEntity cliEmail = commonDao.findByPropertiesSingleResult(ClienteEntity.class,new String[]{"email"},
                new Object[]{cliente.getEmail()});

        if (cliEmail != null && !cliEmail.getId().equals(cliente.getId())){
            throw new ClienteServiceException("Já existe um cliente cadastrado com esse EMAIL.");
        }

        if (cliente.getId() == null){
            cliente = commonDao.save(cliente);
        }else{
            ClienteEntity clienteMerged = commonDao.get(ClienteEntity.class, cliente.getId());
            BeanUtils.copyProperties(cliente,clienteMerged);
            commonDao.update(clienteMerged);
        }
        return cliente;
    }

    @Override
    public ClienteEntity getClienteById(long id) {
        return commonDao.get(ClienteEntity.class,id);
    }

}
