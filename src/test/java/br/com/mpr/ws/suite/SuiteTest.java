package br.com.mpr.ws.suite;

import br.com.mpr.ws.rest.*;
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
        CheckoutRestTest.class,

        MprParameterServiceImplTest.class,

        PedidoServiceImplTest.class,
        PedidoRestTest.class,

        CatalogoServiceImplTest.class,
        CatalogoRestTest.class,

        SessionServiceImplTest.class,
        SessionRestTest.class,

        ProdutoPreviewServiceImplTest.class,
        ProdutoPreviewRestTest.class,


})
public class SuiteTest {
}
