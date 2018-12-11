package br.com.mpr.ws.helper;

import br.com.mpr.ws.constants.GeneroType;
import br.com.mpr.ws.constants.LoginType;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.EnderecoEntity;
import br.com.mpr.ws.entity.LoginEntity;
import br.com.mpr.ws.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 */
public class ClienteHelper {

    public static ClienteEntity createClienteLoginPassword() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Paulo Paulada");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,12);
        c.set(Calendar.MONTH,Calendar.JULY);
        c.set(Calendar.YEAR,1992);
        cliente.setAniversario(c.getTime());
        cliente.setCelular("111111111111");
        cliente.setCpf(geraCPF());
        cliente.setEmail(StringUtils.createRandomHash() + "@email.com");
        cliente.setGenero(GeneroType.M);
        cliente.setEnderecos(new ArrayList<>());
        cliente.getEnderecos().add(createEndereco());
        LoginEntity login = new LoginEntity();
        login.setLoginType(LoginType.PASSWORD);
        login.setSenha("12345");
        login.setKeyDeviceGcm(StringUtils.createRandomHash());
        cliente.setLogin(login);
        return cliente;
    }

    public static ClienteEntity createClienteLoginSocial() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Fernanda Nigths Nigths");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,12);
        c.set(Calendar.MONTH,Calendar.JULY);
        c.set(Calendar.YEAR,1992);
        cliente.setAniversario(c.getTime());
        cliente.setCelular("111111111111");
        cliente.setCpf(geraCPF());
        cliente.setEmail(StringUtils.createRandomHash() + "@email.com");
        cliente.setGenero(GeneroType.M);
        cliente.setEnderecos(new ArrayList<>());
        cliente.getEnderecos().add(createEndereco());
        LoginEntity login = new LoginEntity();
        login.setLoginType(LoginType.FACEBOOK);
        login.setSocialKey(StringUtils.createRandomHash());
        login.setKeyDeviceGcm(StringUtils.createRandomHash());
        cliente.setLogin(login);
        return cliente;
    }

    private static EnderecoEntity createEndereco(){
        EnderecoEntity enderecoEntity = new EnderecoEntity();
        enderecoEntity.setCep("07000000");
        enderecoEntity.setBairro("JD DA FELICIDADE");
        enderecoEntity.setCidade("GUARULHOS");
        enderecoEntity.setDescricao("CASA");
        enderecoEntity.setLogradouro("Rua dos bebedores de redbull");
        enderecoEntity.setUf("UF");
        enderecoEntity.setNumero("0000");
        enderecoEntity.setPrincipal(true);
        return enderecoEntity;
    }

    private static String geraCPF() {
        String iniciais = "";
        Integer numero;
        for (int i = 0; i < 9; i++) {
            numero = new Integer((int) (Math.random() * 10));
            iniciais += numero.toString();
        }
        return iniciais + calcDigVerif(iniciais);
    }

    private static String calcDigVerif(String num) {
        Integer primDig, segDig;
        int soma = 0, peso = 10;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        if (soma % 11 == 0 | soma % 11 == 1)
            primDig = new Integer(0);
        else
            primDig = new Integer(11 - (soma % 11));
        soma = 0;
        peso = 11;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        soma += primDig.intValue() * 2;
        if (soma % 11 == 0 | soma % 11 == 1)
            segDig = new Integer(0);
        else
            segDig = new Integer(11 - (soma % 11));
        return primDig.toString() + segDig.toString();
    }

}
