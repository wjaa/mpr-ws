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
    private String folderPreviewCliente;
    private String imgSemFoto;
    private String baseUrlDestaque;
    private String baseUrlPreview;
    private String baseUrlCliente;
    private String baseUrlCatalogo;
    private String baseUrlPreviewCliente;
    private String notificationUrl;
    private String notificationEmail;
    private String linkPedido;
    private String linkTrocaSenha;
    private String imgIconeBoletoEmail;
    private String imgIconeCartaoEmail;
    private String imgImpressaoFoto;
    private String callbackNotification;

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

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public String getNotificationEmail() {
        return notificationEmail;
    }

    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    public String getLinkPedido() {
        return linkPedido;
    }

    public void setLinkPedido(String linkPedido) {
        this.linkPedido = linkPedido;
    }

    public String getImgIconeBoletoEmail() {
        return imgIconeBoletoEmail;
    }

    public void setImgIconeBoletoEmail(String imgIconeBoletoEmail) {
        this.imgIconeBoletoEmail = imgIconeBoletoEmail;
    }

    public String getImgIconeCartaoEmail() {
        return imgIconeCartaoEmail;
    }

    public void setImgIconeCartaoEmail(String imgIconeCartaoEmail) {
        this.imgIconeCartaoEmail = imgIconeCartaoEmail;
    }

    public String getImgImpressaoFoto() {
        return imgImpressaoFoto;
    }

    public void setImgImpressaoFoto(String imgImpressaoFoto) {
        this.imgImpressaoFoto = imgImpressaoFoto;
    }

    public String getLinkTrocaSenha() {
        return linkTrocaSenha;
    }

    public void setLinkTrocaSenha(String linkTrocaSenha) {
        this.linkTrocaSenha = linkTrocaSenha;
    }

    public String getCallbackNotification() {
        return callbackNotification;
    }

    public void setCallbackNotification(String callbackNotification) {
        this.callbackNotification = callbackNotification;
    }

    public String getFolderPreviewCliente() {
        return folderPreviewCliente;
    }

    public void setFolderPreviewCliente(String folderPreviewCliente) {
        this.folderPreviewCliente = folderPreviewCliente;
    }

    public String getBaseUrlPreviewCliente() {
        return baseUrlPreviewCliente;
    }

    public void setBaseUrlPreviewCliente(String baseUrlPreviewCliente) {
        this.baseUrlPreviewCliente = baseUrlPreviewCliente;
    }
}
