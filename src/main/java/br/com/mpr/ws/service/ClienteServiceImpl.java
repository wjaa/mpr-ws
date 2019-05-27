package br.com.mpr.ws.service;

import br.com.mpr.ws.constants.LoginType;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.EnderecoEntity;
import br.com.mpr.ws.entity.LoginEntity;
import br.com.mpr.ws.exception.ClienteServiceException;
import br.com.mpr.ws.utils.PasswordEncoderUtils;
import br.com.mpr.ws.utils.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Service
public class ClienteServiceImpl implements ClienteService{

    private static final Log LOG = LogFactory.getLog(ClienteServiceImpl.class);

    @Autowired
    private CommonDao commonDao;


    @Autowired
    private Validator validator;

    @Override
    public ClienteEntity saveCliente(ClienteEntity cliente) throws ClienteServiceException {

        try{

            if ( !Utils.validateCpf(cliente.getCpf()) ){
                throw new ClienteServiceException("Cpf inválido!");
            }

            if (cliente.getId() == null || cliente.getId() < 1){
                ClienteEntity cliCpf = commonDao.findByPropertiesSingleResult(ClienteEntity.class,new String[]{"cpf"},
                        new Object[]{cliente.getCpf()});

                if (cliCpf != null && !cliCpf.getId().equals(cliente.getId())){
                    throw new ClienteServiceException("Já existe um cliente cadastrado com esse CPF.");
                }

                ClienteEntity cliEmail = commonDao.findByPropertiesSingleResult(ClienteEntity.class,new String[]{"email"},
                        new Object[]{cliente.getEmail()});

                if (cliEmail != null && !cliEmail.getId().equals(cliente.getId())){
                    throw new ClienteServiceException("Já existe um cliente cadastrado com esse E-MAIL.");
                }

                if (cliente.getLogin() == null){
                    throw new ClienteServiceException("Login é obrigatório para criar um novo cliente");
                }

                this.saveLogin(cliente.getLogin());

                cliente.setAtivo(true);
                cliente = commonDao.save(cliente);
            }else{
                ClienteEntity clienteMerged = commonDao.get(ClienteEntity.class, cliente.getId());
                BeanUtils.copyProperties(cliente,clienteMerged,"login");
                commonDao.update(clienteMerged);
            }

            saveEnderecos(cliente);

            return cliente;
        }catch(ConstraintViolationException ex){
            LOG.error("Erro de validação ", ex);
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation c : ex.getConstraintViolations() ){
                errors.append(c.getMessage() + " ");
            }
            throw new ClienteServiceException(errors.toString());
        }catch (ClienteServiceException ex){
            throw ex;
        }catch(Exception ex){
            LOG.error("Erro ao salvar o cliente: ", ex);
            throw new ClienteServiceException("Erro ao salvar o cliente");
        }
    }

    private void saveLogin(@Valid LoginEntity login) throws ClienteServiceException {

        Set<ConstraintViolation<LoginEntity>> violations = validator.validate(login);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<LoginEntity> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage() + " ");
            }
            throw new ClienteServiceException("Erro de validação: " + sb.toString());
        }

        if (LoginType.PASSWORD.equals(login.getLoginType()) && StringUtils.isEmpty(login.getPass())){
            throw new ClienteServiceException("Para login do tipo PASSWORD cliente precisa criar uma senha.");
        }

        if (!LoginType.PASSWORD.equals(login.getLoginType()) && StringUtils.isEmpty(login.getSocialKey())){
            throw new ClienteServiceException("Para login do tipo FACEBOOK & GPLUS o SocialKey é obrigatório.");
        }

        login.setDataCriacao(new Date());
        login.setDataUltimoAcesso(new Date());
        if (LoginType.PASSWORD.equals(login.getLoginType())){
            login.setSenha(br.com.mpr.ws.utils.StringUtils.createMD5(login.getPass()));
        }
        commonDao.save(login);

    }

    @Override
    public ClienteEntity getClienteById(long id) {
        ClienteEntity clienteEntity = commonDao.get(ClienteEntity.class,id);

        if (clienteEntity != null){
            clienteEntity.setEnderecos(this.getEnderecosByIdCliente(clienteEntity.getId()));
        }
        return clienteEntity;
    }

    @Override
    public List<ClienteEntity> listAllCliente() {
        return commonDao.listAll(ClienteEntity.class);
    }

    @Override
    public ClienteEntity getClienteByKeyDevice(String keyDevice) {
        ClienteEntity clienteEntity = commonDao.findByPropertiesSingleResult(ClienteEntity.class,
                new String[]{"login.keyDeviceGcm"},
                new Object[]{keyDevice});

        if (clienteEntity != null){
            clienteEntity.setEnderecos(this.getEnderecosByIdCliente(clienteEntity.getId()));
        }
        return clienteEntity;
    }

    @Override
    public List<EnderecoEntity> getEnderecosByIdCliente(Long idCliente) {
        return commonDao.findByProperties(EnderecoEntity.class,
                new String[]{"idCliente","ativo"},
                new Object[]{idCliente,"true"});
    }

    @Override
    public EnderecoEntity getEnderecoPrincipalByIdCliente(Long idCliente) {
        return commonDao.findByPropertiesSingleResult(EnderecoEntity.class,
                new String[]{"idCliente","principal"},
                new Object[]{idCliente,true});
    }

    private void saveEnderecos(ClienteEntity cliente) throws ClienteServiceException {
        if (!CollectionUtils.isEmpty(cliente.getEnderecos())){
            for (EnderecoEntity enderecoEntity: cliente.getEnderecos()) {
                if (enderecoEntity.getId() == null){
                    enderecoEntity.setIdCliente(cliente.getId());
                    enderecoEntity.setAtivo(true);
                    commonDao.save(enderecoEntity);
                }else{
                    commonDao.update(enderecoEntity);
                }
            }
        }else{
            throw new ClienteServiceException("Endereço é obrigatório!");
        }
    }

}
