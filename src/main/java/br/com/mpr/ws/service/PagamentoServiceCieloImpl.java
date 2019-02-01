package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.PagamentoServiceException;
import br.com.mpr.ws.vo.CartaoCreditoVo;
import br.com.mpr.ws.vo.CheckoutForm;
import cieloecommerce.sdk.Merchant;
import cieloecommerce.sdk.ecommerce.*;
import cieloecommerce.sdk.ecommerce.request.CieloError;
import cieloecommerce.sdk.ecommerce.request.CieloRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 *
 */
@Service("PagamentoServiceCieloImpl")
@ConfigurationProperties("cielo")
public class PagamentoServiceCieloImpl implements PagamentoService {

    private String merchantId;
    private String merchantKey;
    private Merchant merchant;


    @PostConstruct
    private void init(){
        merchant = new Merchant(merchantId, merchantKey);
    }

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private FreteService freteService;

    @Autowired
    private ClienteService clienteService;


    public PedidoEntity pagamento(CheckoutForm form) throws PagamentoServiceException {

        if (form.getFormaPagamento().isBoleto()){
            return this.pagamentoBoleto(form);
        }else{
            return this.pagamentoCartaoCredito(form);
        }

    }

    private PedidoEntity pagamentoBoleto(CheckoutForm form) throws PagamentoServiceException {
        // Crie uma instância de Sale informando o ID do pagamento
        Sale sale = new Sale("ID do pagamento");

        // Crie uma instância de Customer informando o nome do cliente
        Customer customer = sale.customer("Comprador Teste");

        // Crie uma instância de Payment informando o valor do pagamento
        Payment payment = sale.payment(15700);

        CartaoCreditoVo cartaoCredito = form.getFormaPagamento().getCartaoCredito();
        // Crie  uma instância de Credit Card utilizando os dados de teste
        // esses dados estão disponíveis no manual de integração
        payment.setType(Payment.Type.Boleto);

        // Crie o pagamento na Cielo
        try {
            // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
            sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);


            // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
            // dados retornados pela Cielo
            String paymentId = sale.getPayment().getPaymentId();
            System.out.println(paymentId);

            PedidoEntity pedidoEntity = pedidoService.createPedido(form);

            return pedidoEntity;
        } catch (CieloRequestException e) {
            // Em caso de erros de integração, podemos tratar o erro aqui.
            // os códigos de erro estão todos disponíveis no manual de integração.
            CieloError error = e.getError();
            e.printStackTrace();

            throw new PagamentoServiceException(error.getMessage());
        } catch (IOException e) {
            throw new PagamentoServiceException(e.getMessage());
        }

    }

    private PedidoEntity pagamentoCartaoCredito(CheckoutForm form) throws PagamentoServiceException {


        //no momento da tentativa de pagamento, precisamos criar o pedido para enviar o ID para a cielo.
        PedidoEntity pedidoEntity = pedidoService.createPedido(form);

        // Crie uma instância de Sale informando o ID do pagamento
        Sale sale = new Sale(pedidoEntity.getId().toString());

        // Crie uma instância de Customer informando o nome do cliente
        Customer customer = sale.customer("Comprador Teste");

        // Crie uma instância de Payment informando o valor do pagamento
        Payment payment = sale.payment(15700);

        CartaoCreditoVo cartaoCredito = form.getFormaPagamento().getCartaoCredito();
        // Crie  uma instância de Credit Card utilizando os dados de teste
        // esses dados estão disponíveis no manual de integração
        payment.creditCard(cartaoCredito.getSecutiryCode(), cartaoCredito.getBrand())
                .setCardToken(cartaoCredito.getToken());

        //fazer uma requisicao antes para esconder os dados do cartao de credito do cliente.
        //assim d[a para utilizar o token.
        //.setToken("TOKEN");

        // Crie o pagamento na Cielo
        try {
            // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
            sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);

            // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
            // dados retornados pela Cielo
            String paymentId = sale.getPayment().getPaymentId();
            System.out.println(paymentId);




            return pedidoEntity;
        } catch (CieloRequestException e) {
            // Em caso de erros de integração, podemos tratar o erro aqui.
            // os códigos de erro estão todos disponíveis no manual de integração.
            CieloError error = e.getError();
            e.printStackTrace();

            throw new PagamentoServiceException(error.getMessage());
        } catch (IOException e) {
            throw new PagamentoServiceException(e.getMessage());
        }

    }

    @Override
    public String getCardToken(CartaoCreditoVo cartaoCreditoVo) throws PagamentoServiceException {
        // Configure seu merchant
        Merchant merchant = new Merchant(this.merchantId, this.merchantKey);

        // Informe os dados do cartão que irá tokenizar
        CardToken cardToken = new CardToken();
        BeanUtils.copyProperties(cartaoCreditoVo, cardToken);

        // Crie o Token para o cartão
        try {
            // // Configure o SDK com seu merchant e o ambiente apropriado para
            // gerar o token
            cardToken = new CieloEcommerce(merchant, Environment.SANDBOX).createCardToken(cardToken);

            return cardToken.getCardToken();

        } catch (CieloRequestException e) {
            CieloError error = e.getError();
            throw new PagamentoServiceException(error.getMessage());
        } catch (IOException e) {
            throw new PagamentoServiceException(e.getMessage());
        }
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

}
