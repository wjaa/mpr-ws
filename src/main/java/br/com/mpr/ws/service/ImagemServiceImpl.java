package br.com.mpr.ws.service;

import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.properties.MprWsProperties;
import br.com.mpr.ws.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class ImagemServiceImpl implements ImagemService {

    private static final Log LOG = LogFactory.getLog(ImagemServiceImpl.class);

    @Autowired
    private MprWsProperties properties;


    @Override
    public String uploadFotoCliente(byte[] img, String originName) throws ImagemServiceException {
        File folderCliente = getFileFolderPreview(properties.getFolderCliente());

        if ( !folderCliente.exists() || !folderCliente.isDirectory()){
            if (!folderCliente.mkdirs()){
                throw new ImagemServiceException("O caminho para salvar a imagem de destaque não existe ou não está dispónivel. [" +
                        folderCliente.getAbsolutePath() + "]");
            }
        }

        return this.saveImages(img,originName, properties.getFolderCliente());
    }

    @Override
    public String uploadFotoDestaqueProduto(byte[] img, String originName) throws ImagemServiceException {
        File folderDestaque = getFileFolderPreview(properties.getFolderDestaque());

        if ( !folderDestaque.exists() || !folderDestaque.isDirectory()){
            if (!folderDestaque.mkdirs()){
                throw new ImagemServiceException("O caminho para salvar a imagem de destaque não existe ou não está dispónivel. [" +
                        folderDestaque.getAbsolutePath() + "]");
            }
        }


        return this.saveImages(img,originName, properties.getFolderDestaque());
    }

    @Override
    public String uploadFotoPreviewProduto(byte[] img, String originName) throws ImagemServiceException {
        File folderPreview = getFileFolderPreview(properties.getFolderPreview());

        if ( !folderPreview.exists() || !folderPreview.isDirectory()){
            if (!folderPreview.mkdirs()){
                throw new ImagemServiceException("O caminho para salvar a imagem de preview não existe ou não está dispónivel. [" +
                        folderPreview.getAbsolutePath() + "]");
            }
        }


        return this.saveImages(img,originName, properties.getFolderPreview());
    }

    private File getFileFolderPreview(String folder) {
        return new File(properties.getPathImg() +
                File.separator +
                folder);
    }

    @Override
    public String uploadFotoCatalogo(byte[] img, String originName) throws ImagemServiceException {
        File folderCatalogo = getFileFolderPreview(properties.getFolderCatalogo());

        if ( !folderCatalogo.exists() || !folderCatalogo.isDirectory()){
            if (!folderCatalogo.mkdirs()){
                throw new ImagemServiceException("O caminho para salvar a imagem de preview não existe ou não está dispónivel. [" +
                        folderCatalogo.getAbsolutePath() + "]");
            }
        }
        return this.saveImages(img,originName, properties.getFolderCatalogo());
    }

    @Override
    public String getUrlFotoCatalogo(String foto) {
        return properties.getBaseUrlCatalogo() + foto;
    }

    @Override
    public String getUrlFotoCliente(String foto) {
        return properties.getBaseUrlCliente() + foto;
    }

    @Override
    public String getUrlPreviewProduto(String foto){
        return properties.getBaseUrlPreview() + foto;
    }

    @Override
    public String getUrlImagemDestaque(String foto){
        return properties.getBaseUrlDestaque() + foto;
    }

    @Override
    public String getUrlPreviewCliente(String foto){
        return properties.getBaseUrlPreviewCliente() + foto;
    }

    @Override
    public void removePreviewCliente(String foto) {
        File imgPreviewCliente = getFileFotoPreviewCliente(foto);

        if ( !imgPreviewCliente.exists()){
            LOG.error("Imagem [" + imgPreviewCliente.getAbsolutePath() + "], não existe. Delete abortado!");
            return;
        }
        imgPreviewCliente.delete();
    }

    @Override
    public void removeFotoCliente(String foto) {
        File fotoCliente = getFileFotoCliente(foto);

        if ( !fotoCliente.exists()){
            LOG.error("Imagem [" + fotoCliente.getAbsolutePath() + "], não existe. Delete abortado!");
            return;
        }
        fotoCliente.delete();
    }

    @Override
    public String createPreviewCliente(String foto, List<String> fotosCliente, List<String> fotosCatalogo)
            throws ImagemServiceException {
        File fileFotoPreview = getFilePreviewProduto(foto);

        if (!fileFotoPreview.exists()){
            throw new ImagemServiceException("Preview do produto não encontrado!");
        }

        final List<File> fotos = new ArrayList<>();

        //fotos do cliente
        if (!CollectionUtils.isEmpty(fotosCliente)){
            for (String fotoCliente : fotosCliente){
                File fileFotoCliente = getFileFotoCliente(fotoCliente);
                if (!fileFotoCliente.exists()){
                    throw new ImagemServiceException("Foto do cliente não encontrado!");
                }
                fotos.add(fileFotoCliente);
            }
        }

        //fotos de catalogo.
        if (!CollectionUtils.isEmpty(fotosCatalogo)){
            for (String fotoCatalogo : fotosCatalogo){
                File fileFotoCatalogo = getFileFotoCatalogo(fotoCatalogo);
                if (!fileFotoCatalogo.exists()){
                    throw new ImagemServiceException("Foto do catalogo não encontrado!");
                }
                fotos.add(fileFotoCatalogo);
            }
        }

        return createPreviewCliente(fileFotoPreview, fotos);
    }

    private String createPreviewCliente(File fileFotoPreview, List<File> fotos) {

        //TODO: ESSE CARA IRÁ CHAMAR MPR-IMAGE RESPONSAVEL POR GERAR PREVIEW.

        return "no-image.jpg";
    }

    public File getFilePreviewProduto(String foto) {
        File folder = getFileFolderPreview(properties.getFolderPreview());
        return new File(folder.getAbsoluteFile() + File.separator + foto);
    }

    @Override
    public File getFileFotoCatalogo(String foto) {
        File folder = getFileFolderPreview(properties.getFolderCatalogo());
        return new File(folder.getAbsoluteFile() + File.separator + foto);
    }

    @Override
    public File getFileFotoCliente(String foto) {
        File folder = getFileFolderPreview(properties.getFolderCliente());
        return new File(folder.getAbsoluteFile() + File.separator + foto);
    }

    @Override
    public File getFileDestaqueProduto(String foto) {
        File folder = getFileFolderPreview(properties.getFolderDestaque());
        return new File(folder.getAbsoluteFile() + File.separator + foto);
    }

    @Override
    public File getFileFotoPreviewCliente(String foto) {
        File folder = getFileFolderPreview(properties.getFolderPreviewCliente());
        return new File(folder.getAbsoluteFile() + File.separator + foto);
    }

    private String saveImages(byte[] data, String originName, String folder ) throws ImagemServiceException {

        if (data == null || data.length == 0){
            LOG.warn("Bytes da imagem [" + originName + "] estao vazios.");
            throw new ImagemServiceException("Imagem do upload está vazia!");
        }

        if (org.springframework.util.StringUtils.isEmpty(originName)){
            LOG.warn("Nome da imagem está vazio.");
            throw new ImagemServiceException("Nome da imagem está vazio.");
        }

        File folderImgs = new File(properties.getPathImg());

        if ( !folderImgs.exists() || !folderImgs.isDirectory()){
            if (!folderImgs.mkdirs()){
                throw new ImagemServiceException("O caminho para salvar as imagens não existe ou não está dispónivel. [" +
                        properties.getPathImg() + "]");
            }
        }

        try{

            File file = new File(properties.getPathImg() +
                    File.separator +
                    folder +
                    File.separator +
                    this.createFileName(originName) +
                    this.getExtension(originName));
            FileCopyUtils.copy(data, file);

            return file.getName();



        }catch (Exception ex){
            LOG.error("Erro ao salvar as imagens de um produto", ex);
            throw new ImagemServiceException("Erro ao salvar uma imagem no diretorio, detail:" + ex.getMessage());
        }

    }

    private String getExtension(String fileName) {
        if (fileName.lastIndexOf(".") > -1){
            return fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
        }

        return ".png";

    }

    private String createFileName(String nameImg) {
        //criando um nome aleatorio da imagem.
        return StringUtils.createMD5(nameImg + new Date().getTime() + StringUtils.createRandomHash());
    }
}
