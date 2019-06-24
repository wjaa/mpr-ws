package br.com.mpr.ws.service;

import br.com.uol.pagseguro.api.PagSeguro;
import org.springframework.stereotype.Service;

@Service("PagamentoServicePagseguroMock")
public class PagamentoServicePagseguroMock extends PagamentoServicePagseguroImpl {


    public void setPagSeguroMock(PagSeguro pagSeguro){
        this.pagSeguro = pagSeguro;
    }

}
