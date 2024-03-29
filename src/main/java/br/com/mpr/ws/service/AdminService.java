package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.vo.PedidoFindForm;
import br.com.mpr.ws.vo.ProdutoEstoqueVo;
import br.com.mpr.ws.vo.ProdutoFindForm;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by wagner on 6/25/18.
 */
public interface AdminService {

    FornecedorEntity getFornecedorById(long id);
    TipoProdutoEntity getTipoProdutoById(Long id);
    ProdutoEntity getProdutoById(Long id);
    EstoqueEntity getEstoqueById(Long id);
    ClienteEntity getClienteById(Long id);
    CupomEntity getCupom(Long id);
    TabelaPrecoEntity getTabelaPrecoById(Long id);
    CatalogoGrupoEntity getCatalogoGrupoById(Long id);
    CatalogoEntity getCatalogoById(Long id);
    Serializable getEntityById(String entity, Long id) throws AdminServiceException;

    List<FornecedorEntity> listAllFornecedor();
    List<TipoProdutoEntity> listAllTipoProduto();
    List<ProdutoEntity> listAllProduto();
    List<EstoqueEntity> listEstoqueByIdProduto(Long idProduto);
    List<ClienteEntity> listAllCliente();
    List<CupomEntity> listaAllCupom();
    List<TabelaPrecoEntity> listAllTabelaPreco();
    List<ProdutoEstoqueVo> listProdutoEmEstoque();
    List<CatalogoGrupoEntity> listAllCatalogoGrupo();
    List<CatalogoEntity> listAllCatalogo();
    List<? extends Serializable> listAllEntity(String entity) throws AdminServiceException;

    FornecedorEntity saveFornecedor(FornecedorEntity fe) throws AdminServiceException;
    TipoProdutoEntity saveTipoProduto(TipoProdutoEntity tipoProduto) throws AdminServiceException;
    ProdutoEntity saveProduto(ProdutoEntity produto) throws AdminServiceException;
    EstoqueEntity saveEstoque(EstoqueEntity estoque) throws AdminServiceException;
    ClienteEntity saveCliente(ClienteEntity clienteEntity) throws AdminServiceException;
    CupomEntity saveCupom(CupomEntity cupomEntity) throws AdminServiceException;
    TabelaPrecoEntity saveTabelaPreco(TabelaPrecoEntity tabelaPrecoEntity) throws AdminServiceException;
    CatalogoGrupoEntity saveCatalogoGrupo(CatalogoGrupoEntity catalogoGrupoEntity) throws AdminServiceException;
    CatalogoEntity saveCatalogo(CatalogoEntity catalogoEntity) throws AdminServiceException;
    Serializable saveEntity(String entity, String jsonEntity) throws AdminServiceException;

    void removeFornecedorById(long id) throws AdminServiceException;
    void removeTabelaPrecoById(long id) throws AdminServiceException;
    void removeProdutoById(Long id) throws AdminServiceException;
    void removeCatalogoById(Long id) throws AdminServiceException;

    Collection<PedidoEntity> findPedido(PedidoFindForm pedidoFindForm);
    Collection<ProdutoEntity> findProduto(ProdutoFindForm findForm);

    void removeEntityById(String entity, Long id) throws AdminServiceException;
}
