package br.com.mpr.ws.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by wagner on 04/02/19.
 */
public class AnexoVo {

    private Long id;
    private byte[] foto;
    private String nomeArquivo;
    private String urlFoto;
    private Long idCatalogo;


    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Long getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Long idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean temCatalogo(){
        return this.idCatalogo != null;
    }

    @JsonIgnore
    public boolean temFoto(){
        return this.foto != null && this.foto.length > 0;
    }

}
