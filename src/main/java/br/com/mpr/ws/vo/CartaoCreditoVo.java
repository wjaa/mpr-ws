package br.com.mpr.ws.vo;

/**
 *
 */
public class CartaoCreditoVo {

    private String secutiryCode;
    private String brand;
    private String expirationDate;
    private String cardNumber;
    private String holder;
    private String token;
    private Integer quantity;


    public String getSecutiryCode() {
        return secutiryCode;
    }

    public CartaoCreditoVo setSecutiryCode(String secutiryCode) {
        this.secutiryCode = secutiryCode;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public CartaoCreditoVo setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public CartaoCreditoVo setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CartaoCreditoVo setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public String getHolder() {
        return holder;
    }

    public CartaoCreditoVo setHolder(String holder) {
        this.holder = holder;
        return this;
    }

    public String getToken() {
        return token;
    }

    public CartaoCreditoVo setToken(String token) {
        this.token = token;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
