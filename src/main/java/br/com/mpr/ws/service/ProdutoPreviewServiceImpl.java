package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.exception.ProdutoPreviewServiceException;
import br.com.mpr.ws.vo.AnexoVo;
import br.com.mpr.ws.vo.ImagemPreviewVo;
import br.com.mpr.ws.vo.PreviewForm;
import br.com.mpr.ws.vo.ProdutoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoPreviewServiceImpl implements ProdutoPreviewService {

    @Autowired
    private ImagemService imagemService;


    @Autowired
    private SessionService sessionService;


    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private CommonDao commonDao;



    @Override
    public ProdutoVo addFoto(PreviewForm form) throws ProdutoPreviewServiceException {
        if (form.getIdCliente() == null  && form.getSessionToken() == null){
            throw new ProdutoPreviewServiceException("Cliente ou session não encontrado.");
        }

        if (form.getAnexos() == null || form.getAnexos().size() == 0){
            throw new ProdutoPreviewServiceException("Nenhum anexo foi encontrado.");
        }

        ProdutoPreviewEntity preview = this.findProdutoPreview(form.getIdCliente(), form.getSessionToken());

        //se já existe um ProdutoPreview, vamos limpar as imagens anteriores.
        if (preview != null){
            this.removeAnexosAnteriores(preview);
        }else{
            preview = new ProdutoPreviewEntity();
        }
        preview.setIdProduto(form.getIdProduto());

        if (form.getIdCliente() != null){
            preview.setIdCliente(form.getIdCliente());
            preview.setIdSession(null);
        }else{
           SessionEntity session = sessionService.getSessionByToken(form.getSessionToken());
           preview.setIdSession(session.getId());
           preview.setIdCliente(null);
        }

        preview.setFotoPreview("noimage.jpg");

        if(preview.getId() == null){
            preview = commonDao.save(preview);
        }else{
            preview = commonDao.update(preview);
        }


        List<ProdutoPreviewAnexoEntity> anexos = this.createAnexos(form, preview);
        preview.setAnexos(anexos);

        final List<String> fotosCliente = new ArrayList<>();
        final List<String> fotosCatalogo = new ArrayList<>();
        anexos.forEach( a -> {
            if (a.getFoto() != null) {
                fotosCliente.add(a.getFoto());
            }else if ( a.getIdCatalogo() != null ){
                CatalogoEntity catalogo = commonDao.get(CatalogoEntity.class, a.getIdCatalogo());
                fotosCatalogo.add(catalogo.getImg());

            }
        });

        String previewProduto = produtoService.getImagemPreviewProdutoById(preview.getIdProduto());
        try {
            preview.setFotoPreview(imagemService.createPreviewCliente(previewProduto,fotosCliente,fotosCatalogo));
        } catch (ImagemServiceException e) {
            throw new ProdutoPreviewServiceException("Erro ao gerar o preview do cliente: " + e.getMessage(), e);
        }
        preview = commonDao.update(preview);

        ProdutoVo produtoVo = produtoService.getProdutoById(preview.getIdProduto());
        String imgPreviewCliente = imagemService.getUrlPreviewCliente(preview.getFotoPreview());
        produtoVo.setImgPreviewCliente(imgPreviewCliente);
        return produtoVo;
    }

    @Override
    public ProdutoPreviewEntity getProdutoPreviewByIdCliente(Long idCliente) {
        return findProdutoPreviewByIdCliente(idCliente);
    }

    @Override
    public ProdutoPreviewEntity getProdutoPreviewBySessionToken(String sessionToken) {
        SessionEntity sessionEntity = sessionService.getSessionByToken(sessionToken);
        if (sessionEntity != null){
            return findProdutoPreviewByIdSession(sessionEntity.getId());
        }
        return null;
    }

    @Override
    public ImagemPreviewVo generatePreview(String uploadToken, String produtoRef) throws ProdutoPreviewServiceException {
        ImagemPreviewVo imagemPreviewVo = new ImagemPreviewVo();

        UploadEntity uploadEntity = uploadService.getUploadByToken(uploadToken);
        Assert.notNull(uploadEntity,"UploadToken não encontrado!!!");

        ProdutoEntity produto = produtoService.getProdutoByRef(produtoRef);
        Assert.notNull(produto,"Produto não encontrado, com a referência informada!!!");

        UploadPreviewEntity uploadPreview = getPreviousPreview(produto.getId(),uploadEntity.getId());

        //SE EXISTIR UM PREVIEW ANTERIOR DO MESMO UPLOAD E MESMO PRODUTO, ENTAO NAO PRECISA GERAR NOVAMENTE.
        if (uploadPreview != null){
            imagemPreviewVo.setUrl(imagemService.getUrlPreviewCliente(uploadPreview.getImagem()));
            return imagemPreviewVo;
        }

        List<String> images = uploadEntity
                .getImagens()
                .stream()
                .map( i -> i.getImagemThumb()).collect(Collectors.toList());
        try{
            String imagePreviewName = imagemService.createPreviewCliente(produto.getImgPreview(),images, null);
            imagemPreviewVo.setUrl(imagemService.getUrlPreviewCliente(imagePreviewName));

            UploadPreviewEntity previewEntity = new UploadPreviewEntity();
            previewEntity.setIdProduto(produto.getId());
            previewEntity.setIdUpload(uploadEntity.getId());
            previewEntity.setImagem(imagePreviewName);
            commonDao.save(previewEntity);

            return imagemPreviewVo;
        } catch (ImagemServiceException e) {
            throw new ProdutoPreviewServiceException(e.getMessage(),e);
        }
    }

    private UploadPreviewEntity getPreviousPreview(Long idProduto, Long idUpload) {
        return commonDao.findByPropertiesSingleResult(UploadPreviewEntity.class,
                new String[]{"idProduto","idUpload"},
                new Object[]{idProduto,idUpload});
    }

    private List<ProdutoPreviewAnexoEntity> createAnexos(PreviewForm form, ProdutoPreviewEntity preview)
            throws ProdutoPreviewServiceException {
        List<ProdutoPreviewAnexoEntity> anexos = new ArrayList<>();
        for (AnexoVo a : form.getAnexos()){
            ProdutoPreviewAnexoEntity previewAnexo = new ProdutoPreviewAnexoEntity();
            previewAnexo.setIdProdutoPreview(preview.getId());

            if (a.getIdCatalogo() != null){
                previewAnexo.setIdCatalogo(a.getIdCatalogo());
            }else if (a.getFoto() != null && a.getFoto().length > 0){
                try {
                    if (StringUtils.isEmpty(a.getNomeArquivo())){
                        throw new ProdutoPreviewServiceException("Nome da imagem está vazio");
                    }
                    String urlImagem = imagemService.uploadFotoCliente(a.getFoto(),a.getNomeArquivo());
                    previewAnexo.setFoto(urlImagem);
                } catch (ImagemServiceException e) {
                    throw new ProdutoPreviewServiceException("Erro ao gerar o preview do cliente: " + e.getMessage(), e);
                }
            }else{
                throw new ProdutoPreviewServiceException("Algum anexo está sem foto.");
            }
            commonDao.save(previewAnexo);
            anexos.add(previewAnexo);
        }
        return anexos;
    }

    private void removeAnexosAnteriores(ProdutoPreviewEntity preview) {
        //removendo o preview anterior.
        imagemService.removePreviewCliente(preview.getFotoPreview());

        //removendo as imagens do cliente e a Entidade Anexo.
        preview.getAnexos().stream().forEach(a -> {
            if ( a.getFoto() != null){
                imagemService.removeFotoCliente(a.getFoto());
            }

            //removendo o anexo do banco.
            commonDao.remove(ProdutoPreviewAnexoEntity.class,a.getId());
        });
    }

    private ProdutoPreviewEntity findProdutoPreview(Long idCliente, String sessionToken)
            throws ProdutoPreviewServiceException {
        ProdutoPreviewEntity preview;
        if (sessionToken != null){
            SessionEntity session = sessionService.getSessionByToken(sessionToken);
            if (session == null){
                throw new ProdutoPreviewServiceException("Sessão não encontrada");
            }
            preview = findProdutoPreviewByIdSession(session.getId());
        }else{
            preview = findProdutoPreviewByIdCliente(idCliente);
        }

        return preview;
    }

    private ProdutoPreviewEntity findProdutoPreviewByIdSession(Long idSession) {
        ProdutoPreviewEntity preview = commonDao.findByPropertiesSingleResult(ProdutoPreviewEntity.class,
                new String[]{"idSession"},
                new Object[]{idSession});

        if (preview != null){
            preview.setAnexos(this.getAnexos(preview.getId()));
        }

        return preview;
    }

    private ProdutoPreviewEntity findProdutoPreviewByIdCliente(Long idCliente) {
        ProdutoPreviewEntity preview = commonDao.findByPropertiesSingleResult(ProdutoPreviewEntity.class,
                new String[]{"idCliente"},
                new Object[]{idCliente});

        if (preview != null){
            preview.setAnexos(this.getAnexos(preview.getId()));
        }

        return preview;

    }

    private List<ProdutoPreviewAnexoEntity> getAnexos(Long idProdutoPreview) {
        if (idProdutoPreview != null){
              return commonDao.findByProperties(ProdutoPreviewAnexoEntity.class,
                    new String[]{"idProdutoPreview"},
                    new Object[]{idProdutoPreview});
        }
        return null;
    }
}
