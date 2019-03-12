package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.AdminServiceException;
import br.com.mpr.ws.exception.ClienteServiceException;
import br.com.mpr.ws.properties.MprWsProperties;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.PedidoFindForm;
import br.com.mpr.ws.vo.ProdutoEstoqueVo;
import br.com.mpr.ws.vo.ProdutoFindForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wagner on 6/25/18.
 */
@Service
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class AdminServiceImpl implements AdminService {

    public static final int MAX_DAYS_CUPOM = 60;

    private static final Log LOG = LogFactory.getLog(AdminServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ImagemService imagemService;

    @Resource(name = "findAllEstoqueByIdProduto")
    private String findAllEstoqueByIdProduto;

    @Resource(name = "findTabelaPrecoAtualByProduto")
    private String findTabelaPrecoAtualByProduto;

    @Resource(name = "findAllProduto")
    private String findAllProduto;

    @Resource(name = "findAllProduto.filterDescricao")
    private String filterDescricao;

    @Resource(name = "findAllProduto.filterTipoProduto")
    private String filterTipoProduto;

    @Resource(name = "findAllProduto.orderByTipoProduto")
    private String orderByTipoProduto;

    @Resource(name = "findProdutoEmEstoque")
    private String findProdutoEmEstoque;

    @Resource(name = "findEstoqueById")
    private String findEstoqueById;

    @Autowired
    private MprWsProperties properties;


    @Override
    public FornecedorEntity getFornecedorById(long id) {
        FornecedorEntity entity = commonDao.get(FornecedorEntity.class,id);
        return entity;
    }

    @Override
    public TipoProdutoEntity getTipoProdutoById(Long id) {
        return commonDao.get(TipoProdutoEntity.class,id);
    }

    @Override
    public ProdutoEntity getProdutoById(Long id) {
        ProdutoEntity produtoEntity = commonDao.get(ProdutoEntity.class,id);

        if (produtoEntity != null){
            TabelaPrecoEntity tabelaPrecoAtual = this.getTabelaPrecoAtualByIdProduto(produtoEntity.getId());

            if (tabelaPrecoAtual != null){
                produtoEntity.setPreco(tabelaPrecoAtual.getPreco());
            }
        }
        return produtoEntity;
    }

    private TabelaPrecoEntity getTabelaPrecoAtualByIdProduto(Long id) {
        List<TabelaPrecoEntity> list = commonDao.findByNativeQuery(this.findTabelaPrecoAtualByProduto,
                TabelaPrecoEntity.class,
                new String[]{"id"}, new Object[]{id});
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public EstoqueEntity getEstoqueById(Long id) {
        List<EstoqueEntity> resultList = commonDao.findByNativeQuery(findEstoqueById, EstoqueEntity.class,
                new String[]{"id"},new Object[]{id},true);

        EstoqueEntity estoqueEntity = null;
        if (resultList.size() > 0){
            estoqueEntity = resultList.get(0);
        }

        if (estoqueEntity != null){
            estoqueEntity.setFornecedor(this.getFornecedorById(estoqueEntity.getIdFornecedor()));
            List<EstoqueItemEntity> produtos = commonDao.findByProperties(EstoqueItemEntity.class,
                    new String[]{"idEstoque"},new Object[]{estoqueEntity.getId()});
            estoqueEntity.setProdutos(produtos);
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
        List<ProdutoEntity> listProdutos = commonDao.findByNativeQuery(findAllProduto, ProdutoEntity.class, true);
        return listProdutos;
    }

    @Override
    public Collection<ProdutoEntity> findProduto(ProdutoFindForm findForm) {
        StringBuilder sb = new StringBuilder();
        sb.append(findAllProduto);
        sb.append(!org.springframework.util.StringUtils.isEmpty(findForm.getDescricao()) ? filterDescricao : "");
        sb.append(findForm.getIdTipoProduto() != null ? filterTipoProduto : "");
        sb.append(orderByTipoProduto);

        List<ProdutoEntity> listProdutos =
                commonDao
                .findByNativeQuery(sb.toString(),
                        ProdutoEntity.class,
                        getProdutoFilterName(findForm),
                        getProdutoFilterValue(findForm),
                        true);
        return listProdutos;
    }

    private Object[] getProdutoFilterValue(ProdutoFindForm findForm) {
        List<Object> values = new ArrayList<>();
        if (!org.springframework.util.StringUtils.isEmpty(findForm.getDescricao())){
            values.add("%" + findForm.getDescricao() + "%");
        }
        if (findForm.getIdTipoProduto() != null){
            values.add(findForm.getIdTipoProduto());
        }

        return values.toArray(new Object[]{});
    }

    private String[] getProdutoFilterName(ProdutoFindForm findForm) {
        List<String> values = new ArrayList<>();
        if (!org.springframework.util.StringUtils.isEmpty(findForm.getDescricao())){
            values.add("descricao");
        }
        if (findForm.getIdTipoProduto() != null){
            values.add("idTipoProduto");
        }

        return values.toArray(new String[]{});
    }

    @Override
    public List<EstoqueEntity> listEstoqueByIdProduto(Long idProduto) {
        //TODO IMPLEMENTAR UM PAGINATION
        List<EstoqueEntity> listEstoque = commonDao.findByNativeQuery(findAllEstoqueByIdProduto,
                EstoqueEntity.class,
                new String[]{"idProduto"},
                new Object[]{idProduto},
                true);
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
    public List<ProdutoEstoqueVo> listProdutoEmEstoque(){
        return commonDao.findByNativeQuery(findProdutoEmEstoque,ProdutoEstoqueVo.class);
    }


    @Override
    public FornecedorEntity saveFornecedor(FornecedorEntity fe) throws AdminServiceException {

        if (this.isNew(fe.getId())){
            //tirando o zero se houver.
            fe.setId(null);
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

            if (this.isNew(tipoProduto.getId())){
                throw exception;
            }

            if (tipoProduto.getId() != null && !tipoProduto.getId().equals(tipoProdutoDuplicado.getId())){
                throw exception;
            }

        }

        if (this.isNew(tipoProduto.getId())){
            tipoProduto.setId(null);
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

            if (this.isNew(produto.getId())){
                throw exception;
            }

            if (produto.getId() != null && !produto.getId().equals(produtoDuplicado.getId())){
                throw exception;
            }

        }

        if (this.isNew(produto.getId())){
            if (produto.getByteImgPreview() == null || produto.getByteImgDestaque() == null){
                throw new AdminServiceException("As imagens de destaque e preview são obrigatórias para cadastrar um produto!");
            }

            this.saveImages(produto);

            produto.setId(null);
            produto = commonDao.save(produto);

            //na criacao do produto tiver um preco, criamos um tabela de preco inicial.
            if ( produto.getPreco() != null ){
                TabelaPrecoEntity tabelaPrecoEntity = new TabelaPrecoEntity();
                tabelaPrecoEntity.setPreco(produto.getPreco());
                tabelaPrecoEntity.setDataVigencia(new Date());
                tabelaPrecoEntity.setProduto(produto);
                tabelaPrecoEntity.setDescricao("Valor inicial do produto.");
                tabelaPrecoEntity.setIdProduto(produto.getId());
                this.saveTabelaPreco(tabelaPrecoEntity);
            }

        }else{
            ProdutoEntity produtoMerge = commonDao.get(ProdutoEntity.class, produto.getId());
            BeanUtils.copyProperties(produto,produtoMerge,"imgDestaque","imgPreview","listImgDestaque");
            this.mesclaImagemDestaque(produto,produtoMerge);
            if (produto.getByteImgPreview() != null || produto.getByteImgDestaque() != null ||
                    this.temNovaImagemDestaque(produto.getListImgDestaque()) ){

                this.saveImages(produtoMerge);

            }


            produto = commonDao.update(produtoMerge);
        }
        return produto;
    }

    private void mesclaImagemDestaque(ProdutoEntity produto, ProdutoEntity produtoMerge) {

        //adicionando os novos.
        for (ProdutoImagemDestaqueEntity img : produto.getListImgDestaque()){
            //ignorando objetos que vem vazios.
            if (img.isEmpty()){
                continue;
            }

            if ( !produtoMerge.getListImgDestaque().contains(img) ){
                produtoMerge.getListImgDestaque().add(img);
            }
        }

        List<ProdutoImagemDestaqueEntity> listRemover = new ArrayList<>();
        //excluindo os que foram removidos
        for (ProdutoImagemDestaqueEntity img : produtoMerge.getListImgDestaque()){
            if ( !produto.getListImgDestaque().contains(img) ){
                listRemover.add(img);
            }
        }
        produtoMerge.getListImgDestaque().removeAll(listRemover);

    }

    private boolean temNovaImagemDestaque(List<ProdutoImagemDestaqueEntity> listImgDestaque) {
        boolean tem = false;
        if (!CollectionUtils.isEmpty(listImgDestaque)){
            for (ProdutoImagemDestaqueEntity e : listImgDestaque){
                tem |= e.getByteImgDestaque() != null;
            }
        }
        return tem;
    }

    private void saveImages(ProdutoEntity produto) throws AdminServiceException {
        try{
            //LOOP NAS IMAGENS DE DESTAQUE.
            if (!CollectionUtils.isEmpty(produto.getListImgDestaque())){
                for (ProdutoImagemDestaqueEntity imgDestaque : produto.getListImgDestaque()){

                    //se o byte nao estiver vazio é porque tem nova imagem de destaque.
                    if (imgDestaque.getByteImgDestaque() != null){

                        String finalName = imagemService.uploadFotoProdutoDestaque(imgDestaque.getByteImgDestaque(),
                                imgDestaque.getNameImgDestaque());
                        imgDestaque.setImg(finalName);
                        imgDestaque.setProduto(produto);
                    }
                }
            }

            if (produto.getByteImgDestaque() != null){
                String finalName = imagemService.uploadFotoProdutoDestaque(produto.getByteImgDestaque(),
                        produto.getNameImgDestaque());
                produto.setImgDestaque(finalName);

            }

            if (produto.getByteImgPreview() != null){
                String finalName = imagemService.uploadFotoProdutoPreview(produto.getByteImgPreview(),
                        produto.getNameImgPreview());
                produto.setImgPreview(finalName);

            }

        }catch (Exception ex){
            LOG.error("Erro ao salvar as imagens de um produto", ex);
            throw new AdminServiceException("Erro ao salvar as imagens de um produto, detail:" + ex.getMessage());
        }

    }

    private String getExtension(String fileName) {
        if (fileName.lastIndexOf(".") > -1){
            return fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
        }

        return ".png";

    }

    private String createFileName(String nameImgLow) {
        //criando um nome aleatorio da imagem.
        return StringUtils.createMD5(nameImgLow + new Date().getTime() + StringUtils.createRandomHash());
    }

    @Override
    public TabelaPrecoEntity saveTabelaPreco(TabelaPrecoEntity tabPreco) throws AdminServiceException {

        //TIRANDO 1 min do now para dar uma boa folga do sitema chegar até aqui, quando for uma tabela preco nova.
        Date now = new Date(new Date().getTime() - 1000 * 60);
        if (DateUtils.isLesser(tabPreco.getDataVigencia(), now) ) {
            throw new AdminServiceException("Você não pode " + (tabPreco.getId() == null ? "criar" : "alterar") +
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

        if (this.isNew(tabPreco.getId())){
            tabPreco.setId(null);
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
        if (this.isNew(estoque.getId())){
            estoque.setId(null);

            estoque = commonDao.save(estoque);


            //criando os itens do estoque.
            List<EstoqueItemEntity> produtos = new ArrayList<>(estoque.getQuantidade());
            for (int i = 0; i < estoque.getQuantidade(); i++){
                EstoqueItemEntity estoqueProduto = new EstoqueItemEntity();
                estoqueProduto.setIdProduto(estoque.getIdProduto());
                estoqueProduto.setIdEstoque(estoque.getId());
                estoqueProduto.setInvalido(false);
                produtos.add(estoqueProduto);
                commonDao.save(estoqueProduto);
            }
            estoque.setProdutos(produtos);


        }else{
            List<EstoqueItemEntity> produtos = commonDao.findByProperties(EstoqueItemEntity.class,
                    new String[]{"idEstoque"},
                    new Object[]{estoque.getId()});


            //verificando se o usuário trocou o produto, para modificar os itens
            this.verificaMudancaProduto(estoque, produtos);
            //verificando se usuário trocou a quantidade do estoque.
            this.verificaMudancaQuantidade(estoque, produtos);


            EstoqueEntity estoqueMerged = commonDao.get(EstoqueEntity.class, estoque.getId());

            BeanUtils.copyProperties(estoque,estoqueMerged);

            commonDao.update(estoqueMerged);
        }


        return this.getEstoqueById(estoque.getId());
    }

    private void verificaMudancaQuantidade(EstoqueEntity estoque, List<EstoqueItemEntity> produtos) throws AdminServiceException {
        if (!estoque.getQuantidade().equals(produtos.size())){


            List<ItemPedidoEntity> listBaixa = commonDao.findByProperties(ItemPedidoEntity.class,
                    new String[]{"idEstoque"},
                    new Object[]{estoque.getId()});

            if (listBaixa.size() > 0){
                throw new AdminServiceException("Você não pode alterar a quantidade desse lote, porque algum item já teve baixa. Contate o administrador do sistema!");
            }


            //se usuario aumentou a quantidade, vamos criar novos itens
            if (estoque.getQuantidade() > produtos.size()){
                EstoqueItemEntity produtoBase = produtos.get(0);
                for (int i = produtos.size(); i < estoque.getQuantidade(); i++){
                    EstoqueItemEntity estoqueItemEntity = new EstoqueItemEntity();
                    estoqueItemEntity.setIdProduto(produtoBase.getIdProduto());
                    estoqueItemEntity.setIdEstoque(produtoBase.getIdEstoque());
                    estoqueItemEntity.setInvalido(false);
                    commonDao.save(estoqueItemEntity);
                }


            }else
           //se usuario diminuiu a quantidade, vamos remover itens.
            if (estoque.getQuantidade() < produtos.size()){
                for (int i = estoque.getQuantidade(); i < produtos.size(); i++){
                    commonDao.remove(EstoqueItemEntity.class, produtos.get(i).getId());
                }
            }
        }
    }

    private void verificaMudancaProduto(EstoqueEntity estoque, List<EstoqueItemEntity> produtos) throws AdminServiceException {
        if (!estoque.getIdProduto().equals(produtos.get(0).getIdProduto()) ){

            //se trocou o produto, precisamos verificar se algum  item já nao foi baixado do estoque.
            List<ItemPedidoEntity> listBaixa = commonDao.findByProperties(ItemPedidoEntity.class,
                    new String[]{"idEstoque"},
                    new Object[]{estoque.getId()});

            if (listBaixa.size() > 0){
                throw new AdminServiceException("Você não pode alterar o produto desse lote, porque algum item já teve baixa. Contate o administrador do sistema!");
            }

            //alterando o produto.
            for (EstoqueItemEntity produto : produtos){
                produto.setIdProduto(estoque.getIdProduto());
                commonDao.update(produto);
            }

        }
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

        //se for um novo cupom
        if (this.isNew(cupomEntity.getId())){
            cupomEntity.setId(null);
            //não é promocional, então geramos um código aleatório.
            if (!cupomEntity.getPromocao()){
                boolean cupomExiste = false;

                //evitando duplicidade com o código do cupom.
                do {
                    String hash = StringUtils.createRandomHash();
                    List<?> result = commonDao.findByProperties(CupomEntity.class,new String[]{"hash"}, new Object[]{hash});
                    if (result.size() == 0){
                        cupomEntity.setHash(hash);
                        cupomExiste = false;
                    }else{
                        cupomExiste = true;
                    }

                }while(cupomExiste);
            }else{
                if (org.springframework.util.StringUtils.isEmpty(cupomEntity.getHash())){
                    throw new AdminServiceException("É obrigatório o preenchimento do código do cupom promocional.");
                }

                if (cupomEntity.getHash().matches("[a-zA-Z0-9]{8}")){
                    throw new AdminServiceException("Código do cupom promocional precisa ter 8 caracteres, " +
                            "respeitando letras, maiúsculas ou minúsculas [a-zA-Z] e números [0-9]");
                }
            }

            cupomEntity = commonDao.save(cupomEntity);


        }else{

            PedidoEntity pedido = commonDao.findByPropertiesSingleResult(PedidoEntity.class, new String[]{"idCupom"},
                    new Object[]{cupomEntity.getId()});

            if (pedido != null){
                throw new AdminServiceException("Você não pode alterar esse cupom, porque ele está vinculado com um pedido.");
            }

            cupomEntity = commonDao.update(cupomEntity);
        }

        return cupomEntity;
    }

    private boolean isNew(Long id) {
        return id == null || id < 1;
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
    public Collection<PedidoEntity> findPedido(PedidoFindForm pedidoFindForm) {
        return pedidoService.findPedidoByForm(pedidoFindForm);
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
            case "CupomEntity": return listaAllCupom();
            case "ClienteEntity": return listAllCliente();
            case "EstoqueEntity": return listProdutoEmEstoque();
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
            default: throw new AdminServiceException("Argumento [entity] {" +
                    "inválido!");

        }
    }

    //TODO NAO EXISTE REMOVER REGISTROS E SIM DESATIVAR.
}


