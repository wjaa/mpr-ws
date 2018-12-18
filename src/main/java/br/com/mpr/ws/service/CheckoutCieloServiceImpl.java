package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.CarrinhoEntity;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.CheckoutCieloServiceException;
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

import java.io.IOException;

/**
 *
 */
@Service("CheckoutCieloServiceImpl")
@ConfigurationProperties("cielo")
public class CheckoutCieloServiceImpl implements CheckoutService {

    private String merchantId;
    private String merchantKey;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private PedidoService pedidoService;


    public PedidoEntity checkout(CheckoutForm form) throws CheckoutCieloServiceException {



        if (form.getFormaPagamento().isBoleto()){
            return this.pagamentoBoleto(form);
        }else{
            return this.pagamentoCartaoCredito(form);
        }



    }

    private PedidoEntity pagamentoBoleto(CheckoutForm form) {
        return null;
    }

    private PedidoEntity pagamentoCartaoCredito(CheckoutForm form) throws CheckoutCieloServiceException {
        Merchant merchant = new Merchant(merchantId, merchantKey);

        // Crie uma instância de Sale informando o ID do pagamento
        Sale sale = new Sale("ID do pagamento");

        // Crie uma instância de Customer informando o nome do cliente
        Customer customer = sale.customer("Comprador Teste");

        // Crie uma instância de Payment informando o valor do pagamento
        Payment payment = sale.payment(15700);

        CartaoCreditoVo cartaoCredito = form.getFormaPagamento().getCartaoCredito();
        // Crie  uma instância de Credit Card utilizando os dados de teste
        // esses dados estão disponíveis no manual de integração
        payment.creditCard(cartaoCredito.getSecutiryCode(), cartaoCredito.getBrand())
                .setCardToken(cartaoCredito.getCardToken());

        //fazer uma requisicao antes para esconder os dados do cartao de credito do cliente.
        //assim d[a para utilizar o token.
        //.setCardToken("TOKEN");

        // Crie o pagamento na Cielo
        try {
            // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
            sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);

            // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
            // dados retornados pela Cielo
            String paymentId = sale.getPayment().getPaymentId();
            System.out.println(paymentId);

            CarrinhoEntity carrinho = commonDao.get(CarrinhoEntity.class,form.getIdCarrinho());
            PedidoEntity pedidoEntity = pedidoService.createPedido(carrinho,form.getFormaPagamento());

            return pedidoEntity;
        } catch (CieloRequestException e) {
            // Em caso de erros de integração, podemos tratar o erro aqui.
            // os códigos de erro estão todos disponíveis no manual de integração.
            CieloError error = e.getError();
            e.printStackTrace();

            throw new CheckoutCieloServiceException(error.getMessage());
        } catch (IOException e) {
            throw new CheckoutCieloServiceException(e.getMessage());
        }

    }

    @Override
    public String getCardToken(CartaoCreditoVo cartaoCreditoVo) throws CheckoutCieloServiceException {
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
            throw new CheckoutCieloServiceException(error.getMessage());
        } catch (IOException e) {
            throw new CheckoutCieloServiceException(e.getMessage());
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
