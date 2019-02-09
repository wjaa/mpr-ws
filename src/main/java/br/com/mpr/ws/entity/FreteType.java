package br.com.mpr.ws.entity;

/**
 * Created by wagner on 15/01/19.
 */
public enum FreteType {

    ECONOMICO("Padrão"),
    RAPIDO("Express"),

    ;
    private String descricao;

    FreteType(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
