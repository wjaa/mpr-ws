package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.exception.ClienteServiceException;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wagner on 6/25/18.
 */
@Service
public class AdminServiceImpl implements AdminService {

    public static final int MAX_DAYS_CUPOM = 60;
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ClienteService clienteService;

    @Resource(name = "findAllEstoque")
    private String findAllEstoque;



    @Override
    public FornecedorEntity getFornecedorById(long id) {
        return commonDao.get(FornecedorEntity.class,id);
    }

    @Override
    public TipoProdutoEntity getTipoProdutoById(Long id) {
        return commonDao.get(TipoProdutoEntity.class,id);
    }

    @Override
    public ProdutoEntity getProdutoById(Long id) {
        ProdutoEntity produtoEntity = commonDao.get(ProdutoEntity.class,id);
        if (produtoEntity != null){
            produtoEntity.setTipo(this.getTipoProdutoById(produtoEntity.getIdTipoProduto()));
        }
        return produtoEntity;
    }

    @Override
    public EstoqueEntity getEstoqueById(Long id) {
        EstoqueEntity estoqueEntity = commonDao.get(EstoqueEntity.class,id);

        if (estoqueEntity != null){
            estoqueEntity.setFornecedor(this.getFornecedorById(estoqueEntity.getIdFornecedor()));
            estoqueEntity.setProduto(this.getProdutoById(estoqueEntity.getIdProduto()));
        }
        return estoqueEntity;
    }

    @Override
    public ClienteEntity getClienteById(Long id) {
       return clienteService.getClienteById(id);
    }

    @Override
    public CupomEntity getCupom(Long id) {
        return commonDao.get(CupomEntity.class,id);
    }

    @Override
    public TabelaPrecoEntity getTabelaPrecoById(Long id) {
        TabelaPrecoEntity tabelaPrecoEntity = commonDao.get(TabelaPrecoEntity.class,id);

        if ( tabelaPrecoEntity != null ){
            tabelaPrecoEntity.setProduto(this.getProdutoById(tabelaPrecoEntity.getIdProduto()));
        }

        return tabelaPrecoEntity;
    }

    @Override
    public List<FornecedorEntity> listAllFornecedor() {
        //TODO IMPLEMENTAR UM PAGINATION
        return commonDao.listAll(FornecedorEntity.class);
    }

    @Override
    public List<TipoProdutoEntity> listAllTipoProduto() {
        //TODO IMPLEMENTAR UM PAGINATION
        return commonDao.listAll(TipoProdutoEntity.class);
    }
    @Override
    public List<ProdutoEntity> listAllProduto() {
        //TODO IMPLEMENTAR UM PAGINATION
        return commonDao.listAll(ProdutoEntity.class);
    }

    @Override
    public List<EstoqueEntity> listAllEstoque() {
        //TODO IMPLEMENTAR UM PAGINATION
        List<EstoqueEntity> listEstoque = commonDao.findByNativeQuery(findAllEstoque, EstoqueEntity.class);
        return listEstoque;
    }

    @Override
    public List<ClienteEntity> listAllCliente() {
        //TODO IMPLEMENTAR UM PAGINATION
        return clienteService.listAllCliente();
    }

    @Override
    public List<CupomEntity> listaAllCupom() {
        //TODO IMPLEMENTAR UM PAGINATION
        return commonDao.listAll(CupomEntity.class);
    }

    @Override
    public List<TabelaPrecoEntity> listAllTabelaPreco() {
        //TODO IMPLEMENTAR UM PAGINATION
        return commonDao.listAll(TabelaPrecoEntity.class);
    }


    @Override
    public FornecedorEntity saveFornecedor(FornecedorEntity fe) throws AdminServiceException {

        if (fe.getId() == null){
            List<FornecedorEntity> listFornec = commonDao.findByProperties(FornecedorEntity.class,
                    new String[]{"cnpj"}, new Object[]{fe.getCnpj()});

            if (listFornec.size() > 0){
                throw new AdminServiceException("Já existe um fornecedor cadastrado com esse cnpj!");
            }

            fe.setAtivo(true);
            fe = commonDao.save(fe);
        }else{
            FornecedorEntity fornecedorMerge = commonDao.get(FornecedorEntity.class, fe.getId());
            BeanUtils.copyProperties(fe,fornecedorMerge);
            commonDao.update(fornecedorMerge);
        }
        return fe;
    }

    @Override
    public TipoProdutoEntity saveTipoProduto(TipoProdutoEntity tipoProduto) throws AdminServiceException {

        TipoProdutoEntity tipoProdutoDuplicado = commonDao.findByPropertiesSingleResult(TipoProdutoEntity.class,
                new String[]{"descricao"},
                new Object[]{tipoProduto.getDescricao()});

        if (tipoProdutoDuplicado != null){
            AdminServiceException exception =
                    new AdminServiceException("Já existe um tipo de produto com essa descrição ['" +
                    tipoProduto.getDescricao() + "']");

            if (tipoProduto.getId() == null){
                throw exception;
            }

            if (tipoProduto.getId() != null && !tipoProduto.getId().equals(tipoProdutoDuplicado.getId())){
                throw exception;
            }

        }

        if (tipoProduto.getId() == null){
            tipoProduto = commonDao.save(tipoProduto);
        }else{

            TipoProdutoEntity tipoProdutoMerge = commonDao.get(TipoProdutoEntity.class, tipoProduto.getId());
            BeanUtils.copyProperties(tipoProduto,tipoProdutoMerge);

            tipoProduto = commonDao.update(tipoProdutoMerge);
        }

        return tipoProduto;
    }



    @Override
    public ProdutoEntity saveProduto(ProdutoEntity produto) throws AdminServiceException {

        ProdutoEntity produtoDuplicado = commonDao.findByPropertiesSingleResult(ProdutoEntity.class,
                new String[]{"descricao"},
                new Object[]{produto.getDescricao()});

        if (produtoDuplicado != null){
            AdminServiceException exception =
                    new AdminServiceException("Já existe um produto com essa descrição ['" +
                    produtoDuplicado.getDescricao() + "']");

            if (produto.getId() == null ){
                throw exception;
            }

            if (produto.getId() != null && !produto.getId().equals(produtoDuplicado.getId())){
                throw exception;
            }

        }

        if (produto.getId() == null){
            produto = commonDao.save(produto);

        }else{
            ProdutoEntity produtoMerge = commonDao.get(ProdutoEntity.class, produto.getId());
            BeanUtils.copyProperties(produto,produtoMerge);

            produto = commonDao.update(produtoMerge);
        }
        return produto;
    }

    @Override
    public TabelaPrecoEntity saveTabelaPreco(TabelaPrecoEntity tabPreco) throws AdminServiceException {

        if (DateUtils.isLesserEqual(tabPreco.getDataVigencia(), new Date())) {
            throw new AdminServiceException("Você não pode " + tabPreco.getId() == null ? "criar" : "alterar" +
                    " uma tabela de preço retroativa!");
        }

        List<TabelaPrecoEntity> resultList = commonDao.findByProperties(TabelaPrecoEntity.class,
                new String[]{"idProduto","dataVigencia"},
                new Object[]{tabPreco.getIdProduto(),tabPreco.getDataVigencia()});

        if (resultList.size() > 0){
            AdminServiceException exception =
                    new AdminServiceException("Já existe um preço cadastrado para esse produto com essa data de vigência.");

            if (tabPreco.getId() == null){
                throw exception;
            }

            //se nao for o mesmo ID o usuario está tentando alterando uma tabela e colocando uma data de vigencia já existente.
            if (tabPreco.getId() != null && !resultList.get(0).getId().equals(tabPreco.getId())){
                throw exception;
            }

        }

        if (tabPreco.getId() == null){
            tabPreco = commonDao.save(tabPreco);
        }else{

            TabelaPrecoEntity tabMerged = commonDao.get(TabelaPrecoEntity.class, tabPreco.getId());
            BeanUtils.copyProperties(tabPreco,tabMerged);
            tabPreco = commonDao.update(tabMerged);
        }


        return tabPreco;
    }
    @Override
    public EstoqueEntity saveEstoque(EstoqueEntity estoque) throws AdminServiceException {

        estoque.setDataAtualizacao(new Date());
        if (estoque.getId() == null){

            //Facilitador para o usuário cadastrar o mesmo item varias vezes.
            if (estoque.getQuantidade() != null && estoque.getQuantidade() > 1){

                //Limite para o usuário nao fazer besteira e acabar inserindo um monte de item ao mesmo tempo.
                if (estoque.getQuantidade() > 100){
                    throw new AdminServiceException("Você está tentando cadastrar mais de 100 produtos no estoque ao mesmo tempo.<br/> " +
                            "Por regra limitamos a no máximo 100 produtos por cadastro");
                }

                for (int i = 0; i < estoque.getQuantidade(); i++){
                    estoque = commonDao.save(estoque);
                }
            }else{
                estoque = commonDao.save(estoque);
            }

        }else{

            BaixaEstoqueEntity baixaEstoque = commonDao.findByPropertiesSingleResult(BaixaEstoqueEntity.class,
                    new String[]{"idEstoque"}, new Object[]{estoque.getId()});

            if (baixaEstoque != null){
                throw new AdminServiceException("Você não pode atualizar um registro do estoque que já foi baixado!");
            }

            EstoqueEntity estoqueMerged = commonDao.get(EstoqueEntity.class, estoque.getId());
            BeanUtils.copyProperties(estoque,estoqueMerged);

            estoque = commonDao.save(estoqueMerged);
        }


        return estoque;
    }

    @Override
    public ClienteEntity saveCliente(ClienteEntity clienteEntity) throws AdminServiceException{
        try {
            return clienteService.saveCliente(clienteEntity);
        } catch (ClienteServiceException e) {
            throw new AdminServiceException(e.getMessage());
        }

    }

    @Override
    public CupomEntity saveCupom(CupomEntity cupomEntity) throws AdminServiceException {

        if ( cupomEntity.getDataFim().before(cupomEntity.getDataInicio()) ){
            throw new AdminServiceException("Periodo do cupom inválido!<br> " +
                    "Data fim não pode ser menor que data inicio.");
        }

        if ( DateUtils.getDiffInDays(cupomEntity.getDataInicio(),cupomEntity.getDataFim()) > MAX_DAYS_CUPOM){
            throw new AdminServiceException("Periodo do Cupom não pode ser maior que " + MAX_DAYS_CUPOM);
        }


        //TODO CONTINUAR AQUI
        /****************AQUI*****************/
        return null;
    }


    @Override
    public void removeFornecedorById(long id) throws AdminServiceException {

        FornecedorEntity entity = commonDao.get(FornecedorEntity.class, id);
        if (entity == null){
            throw new AdminServiceException("Fornecedor não existe com id = #" +id);
        }

        entity.setAtivo(false);

        commonDao.update(entity);
    }

    @Override
    public void removeTabelaPrecoById(long id) throws AdminServiceException {
        TabelaPrecoEntity tabelaPrecoEntity = commonDao.get(TabelaPrecoEntity.class, id);
        if (tabelaPrecoEntity == null){
            throw new AdminServiceException("Tabela de preço não existe com id = #" + id);
        }

        if (DateUtils.isLesserEqual(tabelaPrecoEntity.getDataVigencia(), new Date())) {
            throw new AdminServiceException("Tabela de preço retroativa, não pode ser removida!");
        }
        commonDao.remove(TabelaPrecoEntity.class,id);

    }

    @Override
    public void removeProdutoById(Long id) throws AdminServiceException {

    }

    @Override
    public Serializable getEntityById(String entity, Long id) throws AdminServiceException {
        switch (entity) {
            case "FornecedorEntity": return getFornecedorById(id);
            case "TipoProdutoEntity": return getTipoProdutoById(id);
            case "ProdutoEntity": return getProdutoById(id);
            case "TabelaPrecoEntity": return getTabelaPrecoById(id);
            case "EstoqueEntity": return getEstoqueById(id);
            case "CupomEntity": return getCupom(id);
            case "ClienteEntity": return getClienteById(id);
            default: throw new AdminServiceException("Argumento [entity] inválido!");

        }
    }



    @Override
    public List<? extends Serializable> listAllEntity(String entity) throws AdminServiceException {
        switch (entity) {
            case "FornecedorEntity": return listAllFornecedor();
            case "TipoProdutoEntity": return listAllTipoProduto();
            case "ProdutoEntity": return listAllProduto();
            case "TabelaPrecoEntity": return listAllTabelaPreco();
            case "EstoqueEntity": return listAllEstoque();
            case "CupomEntity": return listaAllCupom();
            case "ClienteEntity": return listAllCliente();
            default: throw new AdminServiceException("Argumento [entity] inválido!");

        }
    }



    @Override
    public Serializable saveEntity(String entity, String jsonEntity) throws AdminServiceException {

        switch (entity) {
            case "FornecedorEntity": return saveFornecedor(ObjectUtils.fromJSON(jsonEntity, FornecedorEntity.class));
            case "TipoProdutoEntity": return saveTipoProduto(ObjectUtils.fromJSON(jsonEntity, TipoProdutoEntity.class));
            case "ProdutoEntity": return saveProduto(ObjectUtils.fromJSON(jsonEntity, ProdutoEntity.class));
            case "TabelaPrecoEntity": return saveTabelaPreco(ObjectUtils.fromJSON(jsonEntity, TabelaPrecoEntity.class));
            case "EstoqueEntity": return saveEstoque(ObjectUtils.fromJSON(jsonEntity, EstoqueEntity.class));
            case "CupomEntity": return saveCupom(ObjectUtils.fromJSON(jsonEntity, CupomEntity.class));
            case "ClienteEntity": return saveCliente(ObjectUtils.fromJSON(jsonEntity, ClienteEntity.class));
            default: throw new AdminServiceException("Argumento [entity] inválido!");

        }
    }



    //TODO NAO EXISTE REMOVER REGISTROS E SIM DESATIVAR.
}
