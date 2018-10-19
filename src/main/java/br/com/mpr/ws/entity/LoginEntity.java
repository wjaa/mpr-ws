package br.com.mpr.ws.entity;

import br.com.mpr.ws.constants.LoginType;
import br.com.mpr.ws.helper.JacksonDateSerializer;
import br.com.mpr.ws.helper.JacksonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wagner on 10/1/18.
 */
@Entity
@Table(name = "LOGIN")
public class LoginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DATA_CRIACAO", nullable = false)
    @JsonSerialize(using = JacksonDateTimeSerializer.class)
    private Date dataCriacao;

    @Column(name = "DATA_ULTIMO_ACESSO", nullable = false)
    @JsonSerialize(using = JacksonDateTimeSerializer.class)
    private Date dataUltimoAcesso;

    @Column(name = "SENHA", length = 64)
    private String senha;

    @Column(name = "SOCIAL_KEY", length = 64)
    private String socialKey;

    @Column(name = "LOGIN_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private LoginType loginType;

    @Column(name = "URL_FOTO", length = 1000)
    private String urlFoto;

    @Column(name = "KEY_DEVICE_GCM", length = 255)
    private String keyDeviceGcm;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataUltimoAcesso() {
        return dataUltimoAcesso;
    }

    public void setDataUltimoAcesso(Date dataUltimoAcesso) {
        this.dataUltimoAcesso = dataUltimoAcesso;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSocialKey() {
        return socialKey;
    }

    public void setSocialKey(String socialKey) {
        this.socialKey = socialKey;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getKeyDeviceGcm() {
        return keyDeviceGcm;
    }

    public void setKeyDeviceGcm(String keyDeviceGcm) {
        this.keyDeviceGcm = keyDeviceGcm;
    }
}