package br.com.mpr.ws.service;

import java.util.Date;
import br.com.mpr.ws.constants.LoginType;
import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.LoginEntity;
import br.com.mpr.ws.exception.LoginServiceException;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.LoginForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Log LOG = LogFactory.getLog(LoginServiceImpl.class);

    @Autowired
    private CommonDao dao;

    @Autowired
    private NotificationService notification;


    @Override
    public ClienteEntity login(LoginForm loginForm) throws LoginServiceException {

        if (loginForm.getLoginType() == null){
            throw new LoginServiceException("Tipo de login não informado.");
        }


        switch (loginForm.getLoginType()){
            case FACEBOOK:
            case GPLUS: {
               ClienteEntity clienteEntity = this.findBySocialKey(loginForm.getSocialKey(),loginForm.getLoginType());
               if (clienteEntity == null){
                   throw new LoginServiceException("Usuário não encontrado!");
               }
               return clienteEntity;
            }
            case PASSWORD:{
                ClienteEntity clienteEntity = this.findByPassword(loginForm.getEmail(),
                        StringUtils.createMD5(loginForm.getPassword()));
                if (clienteEntity == null){
                    throw new LoginServiceException("Usuário ou senha inválida!");
                }
                return clienteEntity;
            }
        }

        throw new LoginServiceException("Usuário não encontrado!");
    }

    @Override
    public void resetSenha(String email, String cpf) throws LoginServiceException {

        ClienteEntity cliente = this.dao.findByPropertiesSingleResult(
                ClienteEntity.class, new String[]{"email","cpf"}, new Object[]{email,cpf});

        if (cliente == null){
            throw new LoginServiceException("Cadastro não encontrado");
        }
        LoginEntity login = cliente.getLogin();
        Date today = new Date();
        login.setHashTrocaSenha(StringUtils.createMD5(cliente.getId().toString() + today.getTime() + email));
        //5 DIAS PARA EXPIRAR A TROCA DE SENHA.
        login.setExpirationTrocaSenha(DateUtils.addDays(today,5));

        dao.update(login);

        notification.sendEsqueceuSenha(cliente,login.getHashTrocaSenha());


    }

    @Override
    public void trocarSenha(String hash, String novaSenha) throws LoginServiceException {
        LoginEntity login = dao.findByPropertiesSingleResult(LoginEntity.class, new String[]{"hashTrocaSenha"},
                new Object[]{hash});

        if (login == null){
            throw new LoginServiceException("Código para troca de senha não encontrado!");
        }

        if (login.getExpirationTrocaSenha().before(new Date())){
            throw new LoginServiceException("Código para troca de senha expirou, solicite uma nova troca de senha.");
        }

        login.setSenha(novaSenha);
        dao.update(login);
    }

    private ClienteEntity findBySocialKey(String socialKey, LoginType loginType) throws LoginServiceException {
        List<ClienteEntity> clientes =  dao.findByProperties(ClienteEntity.class,
                new String[]{"login.socialKey","login.loginType"},
                new Object[]{socialKey,loginType});

        if (clientes.size() > 2){
            LOG.error("ERRO GRAVE, FOI ENCONTRADO " + clientes.size() + " PARA O MESMO SOCIAL KEY [" +
                    socialKey + "] E MESMO LOGIN TYPE [" + loginType);
            throw new LoginServiceException("Cliente inválido!");
        }

        if (clientes.size() == 1){
            return clientes.get(0);
        }

        return null;
    }

    private ClienteEntity findByPassword(String email, String pass) throws LoginServiceException {

        if (org.springframework.util.StringUtils.isEmpty(email) || org.springframework.util.StringUtils.isEmpty(pass)){
            throw new LoginServiceException("E-mail e senha são obrigatórios para o login.");
        }

        List<ClienteEntity> clientes =  dao.findByProperties(ClienteEntity.class,
                new String[]{"email","login.senha"},
                new Object[]{email,pass});

        if (clientes.size() > 2){
            LOG.error("ERRO GRAVE, FOI ENCONTRADO " + clientes.size() + " PARA O MESMO EMAIL [" +
                    email + "] E MESMA SENHA [" + pass);
            throw new LoginServiceException("Cliente inválido!");
        }

        if (clientes.size() == 1){
            return clientes.get(0);
        }

        return null;
    }
}
