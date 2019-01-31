package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.EnderecoEntity;
import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.PagamentoServiceCieloException;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.vo.*;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.PaymentItem;
import br.com.uol.pagseguro.api.common.domain.ShippingType;
import br.com.uol.pagseguro.api.common.domain.builder.*;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.DocumentType;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.transaction.register.DirectPaymentRegistrationBuilder;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wagner on 21/01/19.
 */
@Service("PagamentoServicePagseguroImpl")
public class PagamentoServicePagseguroImpl implements PagamentoService {

    private PagSeguro pagSeguro;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CommonDao commonDao;

    @PostConstruct
    private void init(){
        Credential credential = Credential.applicationCredential("app3620108836", "93E0960A9797EC6004FD6FA2200EA86B");
        PagSeguroEnv environment = PagSeguroEnv.SANDBOX;
        pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(),
                new JSEHttpClient(),
                credential, environment);
    }


    @Override
    public PedidoEntity pagamento(CheckoutForm form) throws PagamentoServiceCieloException {

        if ( form.getFormaPagamento().isBoleto() ){
            return checkoutBoleto(form);
        }else{
            return checkoutCartaoCredito(form);
        }
    }

    private PedidoEntity checkoutCartaoCredito(CheckoutForm form) {

        CheckoutVo checkout = checkoutService.getCheckout(form.getIdCheckout());
        ClienteEntity cliente = clienteService.getClienteById(checkout.getIdCliente());
        EnderecoEntity enderecoEntrega = commonDao.get(EnderecoEntity.class, checkout.getEndereco().getId());
        EnderecoEntity enderecoCliente = cliente.getEnderecoPrincipal();
        CartaoCreditoVo cartaoCredito = form.getFormaPagamento().getCartaoCredito();

        // Checkout transparente (pagamento direto) com cartao de credito
        TransactionDetail creditCardTransaction =
                pagSeguro.transactions().register(new DirectPaymentRegistrationBuilder()
                        .withPaymentMode("default")
                        .withCurrency(Currency.BRL)
                        .addItems(this.getPaymentItems(checkout.getProdutos()))
                        .withNotificationURL("api.meuportaretrato.com/psNotification")
                        .withReference("API_MPR_PAYMENT_PAGSEGURO")
                        .withSender(this.getSender(cliente))
                        .withShipping(this.getShipping(enderecoEntrega, checkout.getFreteSelecionado())
                        )
                ).withCreditCard(new CreditCardBuilder()
                        .withBillingAddress(this.getAddress(enderecoCliente)
                        )
                        .withInstallment(new InstallmentBuilder()
                                .withQuantity(cartaoCredito.getQuantity())
                                .withValue(new BigDecimal(checkout.getValorTotal()))
                        )
                        .withHolder(this.getHolder(cliente)
                        )
                        .withToken(cartaoCredito.getToken())
                );
        System.out.println(creditCardTransaction);

        return null;
    }

    private HolderBuilder getHolder(ClienteEntity cliente) {
        return new HolderBuilder()
                .addDocument(new DocumentBuilder()
                        .withType(DocumentType.CPF)
                        .withValue(cliente.getCpf())
                )
                .withName(cliente.getNome())
                .withBithDate(cliente.getAniversario())
                .withPhone(new PhoneBuilder()
                        //.withAreaCode("99")
                        .withNumber(cliente.getCelular())
                );
    }

    private AddressBuilder getAddress(EnderecoEntity endereco) {
        return new AddressBuilder()
                .withPostalCode(endereco.getCep())
                .withCountry("BRA")
                .withState(State.valueOf(endereco.getUf().toUpperCase()))
                .withCity(endereco.getCidade())
                .withComplement(endereco.getComplemento())
                .withDistrict(endereco.getBairro())
                .withNumber(endereco.getNumero())
                .withStreet(endereco.getLogradouro());
    }

    private PedidoEntity checkoutBoleto(CheckoutForm form) {
        CheckoutVo checkout = checkoutService.getCheckout(form.getIdCheckout());
        ClienteEntity cliente = clienteService.getClienteById(checkout.getIdCliente());
        EnderecoEntity enderecoEntrega = commonDao.get(EnderecoEntity.class, checkout.getEndereco().getId());
        // Checkout transparente (pagamento direto) com boleto
        TransactionDetail bankSlipTransaction =
                pagSeguro.transactions().register(new DirectPaymentRegistrationBuilder()
                        .withPaymentMode("default")
                        .withCurrency(Currency.BRL)
                        .addItems(this.getPaymentItems(checkout.getProdutos()))
                        .withNotificationURL("www.sualoja.com.br/notification")
                        .withReference("LIBJAVA_DIRECT_PAYMENT")
                        .withSender(this.getSender(cliente))
                        .withShipping(this.getShipping(enderecoEntrega, checkout.getFreteSelecionado()))
                ).withBankSlip();
        System.out.println(bankSlipTransaction);

        return new PedidoEntity();
    }

    private ShippingBuilder getShipping(EnderecoEntity enderecoEntity, ResultFreteVo freteSelecionado) {
        return new ShippingBuilder()
                .withType(this.getShippingType(freteSelecionado.getFreteType()))
                .withCost(new BigDecimal(freteSelecionado.getValor()))
                .withAddress(this.getAddress(enderecoEntity));
    }

    private ShippingType.Type getShippingType(FreteType freteType) {
        switch (freteType){
            case ECONOMICO: return ShippingType.Type.PAC;
            case RAPIDO: return ShippingType.Type.SEDEX;
        }
        return ShippingType.Type.UNRECOGNIZED;
    }

    private SenderBuilder getSender(ClienteEntity clienteEntity) {
        return new SenderBuilder()
                .withEmail(clienteEntity.getEmail())
                .withName(clienteEntity.getNome())
                .withCPF(clienteEntity.getCpf())
                //.withHash("abc123")
                .withPhone(new PhoneBuilder()
                        //.withAreaCode(clienteEntity.getCelular().substring(0,2))
                        .withNumber(clienteEntity.getCelular()));
    }

    private Iterable<? extends PaymentItem> getPaymentItems(List<ProdutoVo> produtos) {
        List list = new ArrayList<>();
        int countItem = 1;
        for (ProdutoVo pvo : produtos){
            list.add(new PaymentItemBuilder()
                    .withId("000" + countItem++)
                    .withDescription(pvo.getDescricao())
                    .withAmount(new BigDecimal(pvo.getPreco()))
                    .withQuantity(1)
                    .withWeight(pvo.getPeso().intValue()).build()
            );

        }
        return list;
    }

    @Override
    public String getCardToken(CartaoCreditoVo cartaoCreditoVo) throws PagamentoServiceCieloException {
        return null;
    }
}
