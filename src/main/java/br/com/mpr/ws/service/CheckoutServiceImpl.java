package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.exception.CheckoutServiceException;
import br.com.mpr.ws.vo.*;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wagner on 11/01/19.
 */
@Service
public class CheckoutServiceImpl implements CheckoutService {

    private static final Log LOG = LogFactory.getLog(CheckoutServiceImpl.class);

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FreteService freteService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private CupomService cupomService;

    @Autowired
    private EmbalagemService embalagemService;


    @Override
    public CheckoutVo checkout(Long idCarrinho) throws CheckoutServiceException {
        CheckoutVo vo = new CheckoutVo();
        vo.setIdCarrinho(idCarrinho);

        //BUSCANDO O CARRINHO PARA PEGAR OS PRODUTOS DO CLIENTE.
        CarrinhoVo carrinhoVo = this.getCarrinhoVo(idCarrinho);

        //BUSCANDO UM CHECKOUT JÁ CRIADO ANTERIORMENTE PARA ESSE CARRINHO
        CheckoutEntity checkout = commonDao.findByPropertiesSingleResult(CheckoutEntity.class,
                new String[]{"idCarrinho"},
                new Object[]{idCarrinho});

        //PEGANDO O ENDERECO PRINCIPAL DO CLIENTE, OU O ENDERECO JÁ GRAVADO NO CHECKOUT (CASO ELE TENHA TROCADO).
        vo.setEndereco(this.getEnderecoVo(checkout, carrinhoVo.getIdCliente()));

        //CONVERTENDO ITENS DO CARRINHO EM PRODUTO E SOMANDO OS VALORES.
        vo.setProdutos(new ArrayList<>());
        Double valorProdutos = 0.0;
        for (ItemCarrinhoVo item : carrinhoVo.getItems()){
            vo.getProdutos().add(item.getProduto());
            //SOMA DO VALOR DE CADA PRODUTO
            valorProdutos += item.getProduto().getPreco();
        }
        //SOMA DOS PRODUTOS
        vo.setValorProdutos(valorProdutos);

        //LISTA DE FRETES (ECONOMICO E RAPIDO).
        vo.setListResultFrete(this.getListResultFrete(vo.getEndereco().getCep(),
                checkout != null ? checkout.getFreteType() : FreteType.ECONOMICO,
                carrinhoVo.getItems()));

        //Calculando o cupom de desconto se o cliente adicionou antes de modificar o carrinho
        if (checkout != null && checkout.getIdCupom() != null){
            CupomEntity cupom = cupomService.getCupomById(checkout.getIdCupom());
            if (cupom != null){
                vo.setIdCupom(cupom.getId());
                vo.setValorDesconto((vo.getValorProdutos() * cupom.getPorcentagem())/100.0);
            }
        }
        //criando ou atualizando os dados do checkout.
        this.saveCheckout(checkout, vo);

        return vo;
    }

    private List<ResultFreteVo> getListResultFrete(String cep, FreteType freteType,
                                                   List<ItemCarrinhoVo> itensCarrinho) {
        Double peso = 0.0;
        List<ProdutoEntity> produtosFrete = new ArrayList<>();
        for (ItemCarrinhoVo item : itensCarrinho){
            //PESO DOS PRODUTOS PARA CALCULO DO FRETE
            peso += item.getProduto().getPeso();
            //LISTA DE PRODUTOS COM AS MEDIDAS PARA CALCULO DO FRETE.
            ProdutoEntity produtoEntity = new ProdutoEntity();
            BeanUtils.copyProperties(item.getProduto(),produtoEntity);
            produtosFrete.add(produtoEntity);
        }

        //BUSCANDO A EMBALAGEM IDEAL PARA TODOS OS PRODUTOS DO CLIENTE.
        EmbalagemEntity embalagemEntity = embalagemService.getEmbalagem(produtosFrete);

        //CALCULANDO FRETE ECONOMICO
        ResultFreteVo freteEconomico = freteService.calcFrete(
                new FreteService.FreteParam(FreteType.ECONOMICO,
                        cep,
                        peso,
                        embalagemEntity.getComp(),
                        embalagemEntity.getLarg(),
                        embalagemEntity.getAlt()
                        ));

        //CALCULANDO FRETE RAPIDO
        ResultFreteVo freteRapido = freteService.calcFrete(
                new FreteService.FreteParam(FreteType.RAPIDO,
                        cep,
                        peso,
                        embalagemEntity.getComp(),
                        embalagemEntity.getLarg(),
                        embalagemEntity.getAlt()
                ));

        //VERIFICANDO O FRETE SELECIONADO PELO CLIENTE. (DEFAULT ECONOMICO)
        freteEconomico.setSelecionado(FreteType.ECONOMICO.equals(freteType));
        freteRapido.setSelecionado(FreteType.RAPIDO.equals(freteType));

        List<ResultFreteVo> listResultFrete = new ArrayList<>(2);
        listResultFrete.add(freteEconomico);
        listResultFrete.add(freteRapido);

        return listResultFrete;
    }

    private EnderecoVo getEnderecoVo(CheckoutEntity checkout, Long idCliente) throws CheckoutServiceException {
        EnderecoVo enderecoVo = null;
        if (checkout != null){
            EnderecoEntity endereco = commonDao.get(EnderecoEntity.class, checkout.getIdEndereco());
            enderecoVo = EnderecoVo.toVo(endereco);
        }else{
            ClienteEntity cliente = clienteService.getClienteById(idCliente);
            EnderecoEntity endereco = cliente.getEnderecoPrincipal();
            if (endereco == null){
                throw new CheckoutServiceException("Cliente não tem endereço cadastrado!");
            }
            enderecoVo = EnderecoVo.toVo(endereco);
        }
        return enderecoVo;
    }

    private CarrinhoVo getCarrinhoVo(Long idCarrinho) throws CheckoutServiceException {
        CarrinhoVo carrinhoVo = null;
        try {
            carrinhoVo = carrinhoService.getCarrinhoById(idCarrinho);
        } catch (CarrinhoServiceException e) {
            LOG.error("Erro ao buscar o carrinho do pagamento", e);
        }

        if (carrinhoVo == null || carrinhoVo.getItems() == null || carrinhoVo.getItems().size() == 0){
            throw new CheckoutServiceException("Carrinho está vazio ou não existe!");
        }
        return carrinhoVo;
    }

    @Override
    public CheckoutVo alterarEndereco(Long idCheckout, Long idEndereco) throws CheckoutServiceException {
        CheckoutEntity checkoutEntity = commonDao.get(CheckoutEntity.class, idCheckout);
        Assert.notNull(checkoutEntity,"Checkout não encontrado!");


        try {
            CarrinhoVo carrinhoVo = carrinhoService.getCarrinhoById(checkoutEntity.getIdCarrinho());
            ClienteEntity cliente = clienteService.getClienteById(carrinhoVo.getIdCliente());
            if (this.enderecoNaoEhDoCliente(cliente.getEnderecos(),idEndereco) ){
                throw new CheckoutServiceException("O endereco informado não é do cliente.");
            }

            checkoutEntity.setIdEndereco(idEndereco);
            commonDao.update(checkoutEntity);

            return this.checkout(checkoutEntity.getIdCarrinho());

        } catch (CarrinhoServiceException e) {
            throw new CheckoutServiceException("Carrinho não encontrado para o checkout informado.");
        }

    }

    private boolean enderecoNaoEhDoCliente(List<EnderecoEntity> enderecos, Long idEndereco) {
        for (EnderecoEntity endereco : enderecos){
            if ( endereco.getId().equals(idEndereco) ){
                return false;
            }
        }
        return true;
    }

    @Override
    public CheckoutVo adicionarCupom(Long idCheckout, String codigoCupom) throws CheckoutServiceException {
        CheckoutEntity checkoutEntity = commonDao.get(CheckoutEntity.class, idCheckout);
        Assert.notNull(checkoutEntity,"Checkout não encontrado!");

        CupomEntity cupomEntity = cupomService.findCupomByCode(codigoCupom);

        if (cupomEntity == null){
            throw new CheckoutServiceException("Cupom não existe!");
        }
        checkoutEntity.setIdCupom(cupomEntity.getId());
        commonDao.update(checkoutEntity);

        return this.checkout(checkoutEntity.getIdCarrinho());

    }

    private void saveCheckout(CheckoutEntity checkout, CheckoutVo vo) {
        if (checkout == null){
            checkout = new CheckoutEntity();
            //DEFAULT PARA CHECKOUT INICIAL.
            checkout.setFreteType(FreteType.ECONOMICO);
        }
        BeanUtils.copyProperties(vo,checkout,"id");
        checkout.setValorFrete(vo.getValorFrete());
        checkout.setIdEndereco(vo.getEndereco().getId());
        checkout.setValorTotal(vo.getValorTotal());
        checkout.setDiasEntrega(vo.getDiasEntrega());

        if (checkout.getId() == null){
            checkout = commonDao.save(checkout);
        }else{
            checkout = commonDao.update(checkout);
        }

        vo.setId(checkout.getId());
    }


}
