package br.com.mpr.ws.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CHECKOUT_FRETE")
public class CheckoutFreteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "ID_CHECKOUT", nullable = false)
    private CheckoutEntity checkout;

    @Column(name = "FRETE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private FreteType freteType;

    @Column(name = "VALOR", nullable = false)
    private Double valor;

    @Column(name = "DIAS_UTEIS", nullable = false)
    private Integer diasUteis;

    @Column(name = "PREVISAO_ENTREGA", nullable = false)
    private Date previsaoEntrega;

    @Column(name = "MESSAGE_ERROR", length = 300)
    private String messageError;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CheckoutEntity getCheckout() {
        return checkout;
    }

    public void setCheckout(CheckoutEntity checkout) {
        this.checkout = checkout;
    }

    public FreteType getFreteType() {
        return freteType;
    }

    public void setFreteType(FreteType freteType) {
        this.freteType = freteType;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getDiasUteis() {
        return diasUteis;
    }

    public void setDiasUteis(Integer diasUteis) {
        this.diasUteis = diasUteis;
    }

    public Date getPrevisaoEntrega() {
        return previsaoEntrega;
    }

    public void setPrevisaoEntrega(Date previsaoEntrega) {
        this.previsaoEntrega = previsaoEntrega;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }


}
