package br.com.mpr.ws.suite;

import br.com.mpr.ws.service.AdminServiceImplTest;
import br.com.mpr.ws.service.ProdutoServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminServiceImplTest.class,
        ProdutoServiceImplTest.class
})
public class SuiteTest {
}
