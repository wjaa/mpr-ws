package br.com.mpr.ws.constants;

/**
 * Created by wagner on 10/1/18.
 */
public enum GeneroType {

    M("Masculino"), //MASCULINO
    F("Feminino"), //FEMININO
    H("Homoafetivo"),  //HOMOAFETIVO
    O("Outros"), //OUTROS
    ;

    GeneroType(String descricao){
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
