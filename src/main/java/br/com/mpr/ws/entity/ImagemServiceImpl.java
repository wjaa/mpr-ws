package br.com.mpr.ws.entity;

import br.com.mpr.ws.exception.ImagemServiceException;
import br.com.mpr.ws.properties.MprWsProperties;
import br.com.mpr.ws.service.ImagemService;
import br.com.mpr.ws.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.util.Date;

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
        File folderCliente = new File(properties.getPathImg() +
                File.separator +
                properties.getFolderCliente());

        if ( !folderCliente.exists() || !folderCliente.isDirectory()){
            if (!folderCliente.mkdirs()){
                throw new ImagemServiceException("O caminho para salvar a imagem de destaque não existe ou não está dispónivel. [" +
                        folderCliente.getAbsolutePath() + "]");
            }
        }
        return this.saveImages(img,originName, properties.getFolderCliente());
    }

    @Override
    public String uploadFotoProdutoDestaque(byte[] img, String originName) throws ImagemServiceException {
        File folderDestaque = new File(properties.getPathImg() +
                File.separator +
                properties.getFolderDestaque());

        if ( !folderDestaque.exists() || !folderDestaque.isDirectory()){
            if (!folderDestaque.mkdirs()){
                throw new ImagemServiceException("O caminho para salvar a imagem de destaque não existe ou não está dispónivel. [" +
                        folderDestaque.getAbsolutePath() + "]");
            }
        }


        return this.saveImages(img,originName, properties.getFolderDestaque());
    }

    @Override
    public String uploadFotoProdutoPreview(byte[] img, String originName) throws ImagemServiceException {
        File folderPreview = new File(properties.getPathImg() +
                File.separator +
                properties.getFolderPreview());

        if ( !folderPreview.exists() || !folderPreview.isDirectory()){
            if (!folderPreview.mkdirs()){
                throw new ImagemServiceException("O caminho para salvar a imagem de preview não existe ou não está dispónivel. [" +
                        folderPreview.getAbsolutePath() + "]");
            }
        }


        return this.saveImages(img,originName, properties.getFolderPreview());
    }

    @Override
    public String uploadFotoCatalogo(byte[] img, String originName) throws ImagemServiceException {
        File folderCatalogo = new File(properties.getPathImg() +
                File.separator +
                properties.getFolderCatalogo());

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
