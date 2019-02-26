package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.PagamentoServiceException;
import br.com.mpr.ws.exception.PedidoServiceException;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wagner on 21/01/19.
 */
@Service("PagamentoServicePagseguroImpl")
public class PagamentoServicePagseguroImpl implements PagamentoService {

    private static final Log LOG = LogFactory.getLog(PagamentoServicePagseguroImpl.class);


    private PagSeguro pagSeguro;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private NotificationService notificationService;

    @PostConstruct
    private void init(){
        //Credential credential = Credential.applicationCredential("app3620108836", "2E3924589797D5A8848FCF8A50692011");
        Credential credential = Credential.sellerCredential("admin@meuportaretrato.com", "9F613A6E90C447599BA6BA793221620B");
        PagSeguroEnv environment = PagSeguroEnv.SANDBOX;
        pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(),
                new JSEHttpClient(),
                credential, environment);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public ResultadoPagamentoVo pagamento(CheckoutForm form) throws PagamentoServiceException {
        LOG.info("m=pagamento, form=" + form);
        try {
            if ( form.getFormaPagamento().isBoleto() ){
                LOG.info("m=pagamento, boleto selecionado.");
                return this.checkoutBoleto(form);
            }else{
                LOG.info("m=pagamento, cartao de credido selecionado.");
                return this.checkoutCartaoCredito(form);
            }
        } catch (PedidoServiceException e) {
            LOG.error("Erro no pagamento do pedido.", e);
            throw new PagamentoServiceException(e.getMessage(),e);
        }
    }

    private ResultadoPagamentoVo checkoutCartaoCredito(CheckoutForm form) throws PedidoServiceException {
        LOG.info("m=checkoutCartaoCredito, form=" + form);

        LOG.debug("m=checkoutCartaoCredito, pegando informacoes para criar a transacao.");
        CheckoutVo checkout = checkoutService.getCheckout(form.getIdCheckout());
        ClienteEntity cliente = clienteService.getClienteById(checkout.getCliente().getId());
        EnderecoEntity enderecoEntrega = commonDao.get(EnderecoEntity.class, checkout.getEndereco().getId());
        EnderecoEntity enderecoCliente = cliente.getEnderecoPrincipal();
        CartaoCreditoVo cartaoCredito = form.getFormaPagamento().getCartaoCredito();

        //criando pedido
        LOG.debug("m=checkoutCartaoCredito, criando pedido.");
        PedidoEntity pedido = pedidoService.createPedido(form);


        // Checkout transparente (pagamento direto) com cartao de credito
        LOG.debug("m=checkoutCartaoCredito, iniciando transacao com pagseguro");

        TransactionDetail creditCardTransaction = null;
        try{
            creditCardTransaction = this.createCreditCardTransaction(form, pedido, checkout, cliente,
                    enderecoEntrega, enderecoCliente, cartaoCredito);
        }catch(Exception ex){
            LOG.error("m=checkoutCartaoCredito, erro ao criar a transacao no pagseguro", ex);
            LOG.error("m=checkoutCartaoCredito, criando historico de erro no pedido");
            pedidoService.createNovoHistorico(pedido.getId(), SysCodeType.ERRO);
            return this.getResultadoPagamentoVo(pedido,creditCardTransaction);
        }

        if ( creditCardTransaction == null ){
            LOG.error("m=checkoutCartaoCredito, transacao do pagseguro está nula");
            LOG.error("m=checkoutCartaoCredito, criando historico de erro no pedido");
            pedidoService.createNovoHistorico(pedido.getId(), SysCodeType.ERRO);
            return this.getResultadoPagamentoVo(pedido,creditCardTransaction);
        }

        pedido = this.atualizaStatusPedido(pedido, creditCardTransaction);

        LOG.debug("m=checkoutCartaoCredito, pedido finalizado.");

        return this.getResultadoPagamentoVo(pedido,creditCardTransaction);
    }

    private PedidoEntity atualizaStatusPedido(PedidoEntity pedido,
                                              TransactionDetail transactionDetail) throws PedidoServiceException {
        switch (transactionDetail.getStatus().getStatus()){

            //estao confirmando o pagamento
            case WAITING_PAYMENT:
            case IN_REVIEW: return pedidoService.confirmarPedido(transactionDetail.getCode(), pedido.getId());

            //já foi aprovado o pagamento
            case AVAILABLE:
            case APPROVED: {
                pedido = pedidoService.confirmarPedido(transactionDetail.getCode(), pedido.getId());
                pedidoService.createNovoHistorico(pedido.getId(), SysCodeType.PGCF);
                return pedido;
            }

            //já foi cancelada direto.
            case CANCELLED: {
                pedidoService.confirmarPedido(transactionDetail.getCode(), pedido.getId());
                pedido = pedidoService.cancelarPedido(pedido.getId());
                return pedido;
            }

            //default caso venha outro status.
            default: return pedidoService.confirmarPedido(transactionDetail.getCode(), pedido.getId());
        }
    }

    private TransactionDetail createCreditCardTransaction(CheckoutForm form, PedidoEntity pedido,
                                                          CheckoutVo checkout, ClienteEntity cliente,
                                                          EnderecoEntity enderecoEntrega, EnderecoEntity enderecoCliente,
                                                          CartaoCreditoVo cartaoCredito) {
        TransactionDetail creditCardTransaction;
        creditCardTransaction =
                pagSeguro.transactions().register(new DirectPaymentRegistrationBuilder()
                        .withPaymentMode("default")
                        .withCurrency(Currency.BRL)
                        .addItems(this.getPaymentItems(checkout.getCarrinho().getItems()))
                        //TODO VERIFICAR SE PRECISA PASSAR A URL DE NOTIFICACATION OU APENAS CADASTRAR NO PAGSEGURO
                        //.withNotificationURL("api.meuportaretrato.com/psNotification")
                        .withReference(pedido.getCodigoPedido())
                        .withSender(this.getSender(cliente, form.getSenderHash()))
                        .withShipping(this.getShipping(enderecoEntrega, checkout.getFreteSelecionado()))
                        .withExtraAmount(checkout.getValorDesconto() != null ?
                                new BigDecimal(checkout.getValorDesconto() *-1 ):
                                new BigDecimal(0.0))
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
        return creditCardTransaction;
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
                        .withAreaCode("11")
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

    private ResultadoPagamentoVo checkoutBoleto(CheckoutForm form) throws PedidoServiceException {
        LOG.info("m=checkoutBoleto, form=" + form);
        LOG.debug("m=checkoutBoleto, pegando informacoes para criar a transacao.");
        CheckoutVo checkout = checkoutService.getCheckout(form.getIdCheckout());
        ClienteEntity cliente = clienteService.getClienteById(checkout.getCliente().getId());
        EnderecoEntity enderecoEntrega = commonDao.get(EnderecoEntity.class, checkout.getEndereco().getId());


        PedidoEntity pedido = pedidoService.createPedido(form);
        // Checkout transparente (pagamento direto) com boleto
        LOG.debug("m=checkoutBoleto, iniciando transacao no pagseguro.");

        TransactionDetail bankSlipTransaction = null;
        try{
            bankSlipTransaction = createBankSlipTransaction(form, pedido, checkout, cliente, enderecoEntrega);
        }catch(Exception ex){
            LOG.error("m=checkoutBoleto, erro ao criar a transacao no pagseguro", ex);
            LOG.error("m=checkoutBoleto, criando historico de erro no pedido");
            pedidoService.createNovoHistorico(pedido.getId(), SysCodeType.ERRO);
            return this.getResultadoPagamentoVo(pedido,bankSlipTransaction);
        }

        if ( bankSlipTransaction == null ){
            LOG.error("m=checkoutBoleto, transacao do pagseguro está nula");
            LOG.error("m=checkoutBoleto, criando historico de erro no pedido");
            pedidoService.createNovoHistorico(pedido.getId(), SysCodeType.ERRO);
            return this.getResultadoPagamentoVo(pedido,bankSlipTransaction);
        }

        pedido = this.atualizaStatusPedido(pedido, bankSlipTransaction);


        //notificationService.sendTransacaoCriada(pedido,bankSlipTransaction)

        LOG.debug("m=checkoutBoleto, pedido finalizado.");
        return this.getResultadoPagamentoVo(pedido,bankSlipTransaction);
    }

    private ResultadoPagamentoVo getResultadoPagamentoVo(PedidoEntity pedido,
                                                         TransactionDetail transactionDetail) {
        ResultadoPagamentoVo resultadoPagamentoVo = new ResultadoPagamentoVo();
        resultadoPagamentoVo.setIdPedido(pedido.getId());
        resultadoPagamentoVo.setCodigoPedido(pedido.getCodigoPedido());
        resultadoPagamentoVo.setDataPedido(pedido.getData());
        resultadoPagamentoVo.setEmail(transactionDetail.getSender().getEmail());
        resultadoPagamentoVo.setPagamentoType(pedido.getPagamentoType());
        resultadoPagamentoVo.setStatusPedido(pedido.getStatusAtual().getNomeCliente());
        resultadoPagamentoVo.setPaymentLink(
                PagamentoType.BOLETO.equals(pedido.getPagamentoType()) && transactionDetail != null ?
        transactionDetail.getPaymentLink() : "");

        return resultadoPagamentoVo;
    }

    private TransactionDetail createBankSlipTransaction(CheckoutForm form, PedidoEntity pedido, CheckoutVo checkout, ClienteEntity cliente, EnderecoEntity enderecoEntrega) {
        return pagSeguro.transactions().register(new DirectPaymentRegistrationBuilder()
                .withPaymentMode("default")
                .withCurrency(Currency.BRL)
                .addItems(this.getPaymentItems(checkout.getCarrinho().getItems()))
                //TODO VERIFICAR SE PRECISA PASSAR A URL DE NOTIFICACATION OU APENAS CADASTRAR NO PAGSEGURO
                //.withNotificationURL("www.sualoja.com.br/notification")
                .withReference(pedido.getCodigoPedido())
                .withSender(this.getSender(cliente, form.getSenderHash()))
                .withShipping(this.getShipping(enderecoEntrega, checkout.getFreteSelecionado()))

        ).withBankSlip();
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

    private SenderBuilder getSender(ClienteEntity clienteEntity, String senderHash) {
        return new SenderBuilder()
                .withEmail("v00849507912235340423@sandbox.pagseguro.com.br")
                .withName(clienteEntity.getNome())
                .withCPF(clienteEntity.getCpf())
                .withHash(senderHash)
                .withPhone(new PhoneBuilder()
                        .withAreaCode("11")
                        .withNumber(clienteEntity.getCelular()));
    }

    private Iterable<? extends PaymentItem> getPaymentItems(List<ItemCarrinhoVo> itens) {
        List list = new ArrayList<>();
        int countItem = 1;
        for (ItemCarrinhoVo it : itens){
            list.add(new PaymentItemBuilder()
                    .withId("000" + countItem++)
                    .withDescription(it.getProduto().getDescricao())
                    .withAmount(new BigDecimal(it.getProduto().getPreco()))
                    .withQuantity(1)
                    .withWeight(it.getProduto().getPeso().intValue())
                    .build()
            );

        }
        return list;
    }

    @Override
    public String getCardToken(CartaoCreditoVo cartaoCreditoVo) throws PagamentoServiceException {
        return null;
    }

}
