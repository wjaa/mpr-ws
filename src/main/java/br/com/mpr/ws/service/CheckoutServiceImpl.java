package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.exception.CheckoutServiceException;
import br.com.mpr.ws.exception.RestException;
import br.com.mpr.ws.utils.RestUtils;
import br.com.mpr.ws.vo.*;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private MprParameterService parameterService;


    @Override
    public CheckoutVo checkout(Long idCarrinho) throws CheckoutServiceException {
        CheckoutVo vo = new CheckoutVo();

        //BUSCANDO O CARRINHO PARA PEGAR OS PRODUTOS DO CLIENTE.
        CarrinhoVo carrinhoVo = this.getCarrinhoVo(idCarrinho);
        vo.setCarrinho(carrinhoVo);

        //BUSCANDO UM CHECKOUT JÁ CRIADO ANTERIORMENTE PARA ESSE CARRINHO
        CheckoutEntity checkout = commonDao.findByPropertiesSingleResult(CheckoutEntity.class,
                new String[]{"idCarrinho"},
                new Object[]{idCarrinho});

        //PEGANDO O ENDERECO PRINCIPAL DO CLIENTE, OU O ENDERECO JÁ GRAVADO NO CHECKOUT (CASO ELE TENHA TROCADO).
        vo.setEndereco(this.getEnderecoVo(checkout, carrinhoVo.getIdCliente()));

        //PEGANDO O CLIENTE DO CARRINHO
        ClienteEntity cliente = clienteService.getClienteById(carrinhoVo.getIdCliente());
        vo.setCliente(new ClienteVo(cliente));

        //CONVERTENDO ITENS DO CARRINHO EM PRODUTO E SOMANDO OS VALORES.
        Double valorProdutos = 0.0;
        for (ItemCarrinhoVo item : carrinhoVo.getItems()){
            //SOMA DO VALOR DE CADA PRODUTO
            valorProdutos += item.getProduto().getPreco();
        }
        //SOMA DOS PRODUTOS
        vo.setValorProdutos(valorProdutos);


        //VALORES DE FRETE
        if (checkout == null){
            //CALCULA OS FRETES PARA (ECONOMICO E RAPIDO).
            vo.setListResultFrete(this.calcularFretes(vo.getEndereco().getCep(),
                    checkout != null ? checkout.getFreteType() : FreteType.ECONOMICO,
                    carrinhoVo.getItems()));
        }else{
            vo.setListResultFrete(this.getListResultFrete(checkout));
        }

        //SE JÁ EXISTIR UM CHECKOU, ENTAO NAO RECALCULAMOS O FRETE, UTILIZAMOS O QUE FOI GRAVADO NA BASE.
        //Calculando o cupom de desconto se o cliente adicionou antes de modificar o carrinho
        if (checkout != null && checkout.getIdCupom() != null){
            CupomEntity cupom = cupomService.getCupomById(checkout.getIdCupom());
            if (cupom != null){
                vo.setCupom(new CupomVo(cupom));
                vo.setValorDesconto((vo.getValorProdutos() * cupom.getPorcentagem())/100.0);
            }
        }
        //adicionando o token de checkout para o gateway pagamento
        vo.setCheckoutToken(this.getCheckoutToken());

        //criando ou atualizando os dados do checkout.
        this.saveCheckout(checkout, vo);

        return vo;
    }

    private List<ResultFreteVo> getListResultFrete(CheckoutEntity checkout) {
        List<ResultFreteVo> listResult = new ArrayList<>();
        for (CheckoutFreteEntity frete : checkout.getListFrete()){
            ResultFreteVo freteVo = new ResultFreteVo();
            BeanUtils.copyProperties(frete, freteVo);
            freteVo.setSelecionado(checkout.getFreteType().equals(frete.getFreteType()));
            listResult.add(freteVo);
        }
        return listResult;
    }

    private List<ResultFreteVo> calcularFretes(String cep, FreteType freteType,
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
        freteEconomico.setFreteType(FreteType.ECONOMICO);
        freteRapido.setSelecionado(FreteType.RAPIDO.equals(freteType));
        freteRapido.setFreteType(FreteType.RAPIDO);

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

            //RECALCULANDO OS FRETES ECONOMICO E RAPIDO.
            List<ResultFreteVo> novoResultList = this.calcularFretes(this.getNovoCep(cliente.getEnderecos(),idEndereco),
                    checkoutEntity.getFreteType(),carrinhoVo.getItems());


            //COPIANDO OS VALORES PARA A LISTA DE FRETES DO CHECKOUT.
            for (CheckoutFreteEntity checkoutFrete : checkoutEntity.getListFrete() ){
                for (ResultFreteVo resultFrete : novoResultList){
                    //ALTERANDO OS VALORES DOS RESULTADOS ANTERIORES
                    if (resultFrete.getFreteType().equals(checkoutFrete.getFreteType())){
                        BeanUtils.copyProperties(resultFrete,checkoutFrete, "id", "checkout");
                    }
                    //ALTERANDO OS VALORES DO CHECKOUT.
                    if (resultFrete.getFreteType().equals(checkoutEntity.getFreteType())){
                        checkoutEntity.setDiasEntrega(resultFrete.getDiasUteis());
                        checkoutEntity.setValorFrete(resultFrete.getValor());
                    }
                }
            }

            //ATUALIZANDO O CHECKOUT.
            commonDao.update(checkoutEntity);

            return this.checkout(checkoutEntity.getIdCarrinho());

        } catch (CarrinhoServiceException e) {
            throw new CheckoutServiceException("Carrinho não encontrado para o checkout informado.");
        }

    }

    private String getNovoCep(List<EnderecoEntity> enderecos, Long idEndereco) {
        for (EnderecoEntity endereco : enderecos){
            if ( endereco.getId().equals(idEndereco) ){
                return endereco.getCep();
            }
        }
        return "";

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
    public String getCheckoutToken() throws CheckoutServiceException {

        //TODO AQUI ESTÁ AMARRADO AO PAGSEGURO, PRECISA SER CORRIGIDO.

        String tokenPS = parameterService.getParameter(MprParameterService.MprParameter.PS_API_TOKEN,
                "9F613A6E90C447599BA6BA793221620B");
        String emailPS = parameterService.getParameter(MprParameterService.MprParameter.PS_API_EMAIL,
                "admin@meuportaretrato.com");
        String urlPS = parameterService.getParameter(MprParameterService.MprParameter.PS_API_SESSION_URL,
                "https://ws.sandbox.pagseguro.uol.com.br/v2/sessions/");

        Map<String,String> param = new HashMap();
        param.put("email",emailPS);
        param.put("token",tokenPS);

        try {
            String xml = RestUtils.post(urlPS,param);
            JAXBContext jaxbContext = JAXBContext.newInstance(SessionPagseguroVo.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            SessionPagseguroVo sessionPagseguro = (SessionPagseguroVo) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return sessionPagseguro.getId();
        } catch (RestException e) {
            LOG.error("Erro ao pegar o token de checkout", e);
            throw new CheckoutServiceException("Erro ao pegar o token de checkout: " + e.getMessage());
        } catch (JAXBException e) {
            LOG.error("Erro no parse do xml do token", e);
            throw new CheckoutServiceException("Erro no parse do xml do token: " + e.getMessage());
        }
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

    @Override
    public CheckoutVo alterarFrete(Long idCheckout, FreteType freteType) throws CheckoutServiceException {

        CheckoutEntity checkoutEntity = commonDao.get(CheckoutEntity.class, idCheckout);
        Assert.notNull(checkoutEntity,"Checkout não encontrado!");

        checkoutEntity.setFreteType(freteType);

        for (CheckoutFreteEntity frete: checkoutEntity.getListFrete()){
            if (frete.getFreteType().equals(freteType)){
                checkoutEntity.setValorFrete(frete.getValor());
                checkoutEntity.setDiasEntrega(frete.getDiasUteis());
            }
        }
        commonDao.update(checkoutEntity);

        return this.checkout(checkoutEntity.getIdCarrinho());
    }

    @Override
    public CheckoutVo getCheckout(Long idCheckout) {

        CheckoutVo vo = new CheckoutVo();
        try {
            CheckoutEntity checkout = commonDao.get(CheckoutEntity.class, idCheckout);
            BeanUtils.copyProperties(checkout,vo);
            CarrinhoVo carrinhoVo = this.getCarrinhoVo(checkout.getIdCarrinho());
            vo.setCarrinho(carrinhoVo);
            ClienteEntity cliente = clienteService.getClienteById(carrinhoVo.getIdCliente());
            vo.setCliente(new ClienteVo(cliente));
            vo.setEndereco(this.getEnderecoVo(checkout,carrinhoVo.getIdCliente()));
            vo.setListResultFrete(this.getListResultFrete(checkout));
            if (checkout != null && checkout.getIdCupom() != null){
                CupomEntity cupom = cupomService.getCupomById(checkout.getIdCupom());
                if (cupom != null){
                    vo.setCupom(new CupomVo(cupom));
                }
            }

        } catch (CheckoutServiceException e) {
           LOG.error("Erro ao buscar o checkout", e);
        }

        return vo;
    }

    private void saveCheckout(CheckoutEntity checkout, CheckoutVo vo) {
        if (checkout == null){
            checkout = new CheckoutEntity();
            //DEFAULT PARA CHECKOUT INICIAL.
            checkout.setFreteType(FreteType.ECONOMICO);
            this.createDadosFrete(checkout,vo);

        }
        BeanUtils.copyProperties(vo,checkout,"id");
        checkout.setValorFrete(vo.getValorFrete());
        checkout.setIdEndereco(vo.getEndereco().getId());
        checkout.setValorTotal(vo.getValorTotal());
        checkout.setDiasEntrega(vo.getDiasEntrega());
        checkout.setIdCupom(vo.getCupom() != null ? vo.getCupom().getId() : null);
        checkout.setIdCarrinho(vo.getCarrinho().getIdCarrinho());

        if (checkout.getId() == null){
            checkout = commonDao.save(checkout);
        }else{
            checkout = commonDao.update(checkout);
        }

        vo.setId(checkout.getId());
    }

    private void createDadosFrete(CheckoutEntity checkout, CheckoutVo vo) {
        checkout.setValorFrete(vo.getValorFrete());
        checkout.setDiasEntrega(vo.getDiasEntrega());
        checkout.setListFrete(new ArrayList<>());

        for (ResultFreteVo freteVo : vo.getListResultFrete()){
            CheckoutFreteEntity frete = new CheckoutFreteEntity();
            frete.setCheckout(checkout);
            BeanUtils.copyProperties(freteVo, frete);
            checkout.getListFrete().add(frete);
        }
    }



}
