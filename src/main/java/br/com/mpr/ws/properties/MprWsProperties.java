package br.com.mpr.ws.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by wagner on 9/14/18.
 */
@Component
@ConfigurationProperties("mpr")
public class MprWsProperties {

    private String pathImg;
    private String staticHost;
    private String folderPreview;
    private String folderDestaque;
    private String folderCatalogo;
    private String folderCliente;
    private String imgSemFoto;
    private String baseUrlDestaque;
    private String baseUrlPreview;
    private String baseUrlCliente;
    private String baseUrlCatalogo;

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public String getStaticHost() {
        return staticHost;
    }

    public void setStaticHost(String staticHost) {
        this.staticHost = staticHost;
    }

    public String getFolderPreview() {
        return folderPreview;
    }

    public void setFolderPreview(String folderPreview) {
        this.folderPreview = folderPreview;
    }

    public String getFolderDestaque() {
        return folderDestaque;
    }

    public void setFolderDestaque(String folderDestaque) {
        this.folderDestaque = folderDestaque;
    }

    public String getImgSemFoto() {
        return imgSemFoto;
    }

    public void setImgSemFoto(String imgSemFoto) {
        this.imgSemFoto = imgSemFoto;
    }

    public String getBaseUrlDestaque() {
        return baseUrlDestaque;
    }

    public void setBaseUrlDestaque(String baseUrlDestaque) {
        this.baseUrlDestaque = baseUrlDestaque;
    }

    public String getBaseUrlPreview() {
        return baseUrlPreview;
    }

    public void setBaseUrlPreview(String baseUrlPreview) {
        this.baseUrlPreview = baseUrlPreview;
    }

    public String getFolderCatalogo() {
        return folderCatalogo;
    }

    public void setFolderCatalogo(String folderCatalogo) {
        this.folderCatalogo = folderCatalogo;
    }

    public String getFolderCliente() {
        return folderCliente;
    }

    public void setFolderCliente(String folderCliente) {
        this.folderCliente = folderCliente;
    }

    public String getBaseUrlCliente() {
        return baseUrlCliente;
    }

    public void setBaseUrlCliente(String baseUrlCliente) {
        this.baseUrlCliente = baseUrlCliente;
    }

    public String getBaseUrlCatalogo() {
        return baseUrlCatalogo;
    }

    public void setBaseUrlCatalogo(String baseUrlCatalogo) {
        this.baseUrlCatalogo = baseUrlCatalogo;
    }
}
