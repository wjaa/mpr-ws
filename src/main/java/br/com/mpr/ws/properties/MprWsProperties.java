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
    private String imgSemFoto;
    private String baseUrlDestaque;
    private String baseUrlPreview;

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
}
