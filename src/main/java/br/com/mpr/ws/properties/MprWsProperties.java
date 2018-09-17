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
    private String folderThumb;

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

    public String getFolderThumb() {
        return folderThumb;
    }

    public void setFolderThumb(String folderThumb) {
        this.folderThumb = folderThumb;
    }
}
