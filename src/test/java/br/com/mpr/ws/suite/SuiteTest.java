package br.com.mpr.ws.suite;

import br.com.mpr.ws.rest.AdminRestTest;
import br.com.mpr.ws.rest.CarrinhoRestTest;
import br.com.mpr.ws.rest.ProdutoRestTest;
import br.com.mpr.ws.service.AdminServiceImplTest;
import br.com.mpr.ws.service.ClienteServiceImplTest;
import br.com.mpr.ws.service.ProdutoServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminServiceImplTest.class,
        AdminRestTest.class,

        ClienteServiceImplTest.class,

        ProdutoServiceImplTest.class,
        ProdutoRestTest.class,

        CarrinhoRestTest.class,

})
public class SuiteTest {
}
