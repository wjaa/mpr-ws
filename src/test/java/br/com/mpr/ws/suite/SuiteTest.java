package br.com.mpr.ws.suite;

import br.com.mpr.ws.rest.AdminRestTest;
import br.com.mpr.ws.rest.CarrinhoRestTest;
import br.com.mpr.ws.rest.LoginRestTest;
import br.com.mpr.ws.rest.ProdutoRestTest;
import br.com.mpr.ws.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminServiceImplTest.class,
        AdminRestTest.class,

        ClienteServiceImplTest.class,

        ProdutoServiceImplTest.class,
        ProdutoRestTest.class,

        CarrinhoServiceImplTest.class,
        CarrinhoRestTest.class,

        LoginServiceImplTest.class,
        LoginRestTest.class,

        EmbalagemServiceImplTest.class,
        FreteServiceCorreioImplTest.class,

        CheckoutServiceImplTest.class,

        MprParameterServiceImplTest.class,


})
public class SuiteTest {
}
