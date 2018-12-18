
package br.com.mpr.ws.frete.correios;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cObjeto complex type.
 * 
 * <p>The following schema fragment specifies the expected         content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cObjeto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="servico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cepOrigem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cepDestino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="prazoEntrega" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="dataPostagem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataPostagemCalculo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataMaxEntrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="postagemDH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataUltimoEvento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codigoUltimoEvento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipoUltimoEvento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descricaoUltimoEvento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="erro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="msgErro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nuTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="formaPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valorPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cObjeto", propOrder = {
    "codigo",
    "servico",
    "cepOrigem",
    "cepDestino",
    "prazoEntrega",
    "dataPostagem",
    "dataPostagemCalculo",
    "dataMaxEntrega",
    "postagemDH",
    "dataUltimoEvento",
    "codigoUltimoEvento",
    "tipoUltimoEvento",
    "descricaoUltimoEvento",
    "erro",
    "msgErro",
    "nuTicket",
    "formaPagamento",
    "valorPagamento"
})
public class CObjeto {

    protected String codigo;
    protected String servico;
    protected String cepOrigem;
    protected String cepDestino;
    protected int prazoEntrega;
    protected String dataPostagem;
    protected String dataPostagemCalculo;
    protected String dataMaxEntrega;
    protected String postagemDH;
    protected String dataUltimoEvento;
    protected String codigoUltimoEvento;
    protected String tipoUltimoEvento;
    protected String descricaoUltimoEvento;
    protected String erro;
    protected String msgErro;
    protected String nuTicket;
    protected String formaPagamento;
    protected String valorPagamento;

    /**
     * Gets the value of the codigo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Gets the value of the servico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServico() {
        return servico;
    }

    /**
     * Sets the value of the servico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServico(String value) {
        this.servico = value;
    }

    /**
     * Gets the value of the cepOrigem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCepOrigem() {
        return cepOrigem;
    }

    /**
     * Sets the value of the cepOrigem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCepOrigem(String value) {
        this.cepOrigem = value;
    }

    /**
     * Gets the value of the cepDestino property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCepDestino() {
        return cepDestino;
    }

    /**
     * Sets the value of the cepDestino property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCepDestino(String value) {
        this.cepDestino = value;
    }

    /**
     * Gets the value of the prazoEntrega property.
     * 
     */
    public int getPrazoEntrega() {
        return prazoEntrega;
    }

    /**
     * Sets the value of the prazoEntrega property.
     * 
     */
    public void setPrazoEntrega(int value) {
        this.prazoEntrega = value;
    }

    /**
     * Gets the value of the dataPostagem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataPostagem() {
        return dataPostagem;
    }

    /**
     * Sets the value of the dataPostagem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataPostagem(String value) {
        this.dataPostagem = value;
    }

    /**
     * Gets the value of the dataPostagemCalculo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataPostagemCalculo() {
        return dataPostagemCalculo;
    }

    /**
     * Sets the value of the dataPostagemCalculo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataPostagemCalculo(String value) {
        this.dataPostagemCalculo = value;
    }

    /**
     * Gets the value of the dataMaxEntrega property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataMaxEntrega() {
        return dataMaxEntrega;
    }

    /**
     * Sets the value of the dataMaxEntrega property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataMaxEntrega(String value) {
        this.dataMaxEntrega = value;
    }

    /**
     * Gets the value of the postagemDH property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostagemDH() {
        return postagemDH;
    }

    /**
     * Sets the value of the postagemDH property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostagemDH(String value) {
        this.postagemDH = value;
    }

    /**
     * Gets the value of the dataUltimoEvento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUltimoEvento() {
        return dataUltimoEvento;
    }

    /**
     * Sets the value of the dataUltimoEvento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUltimoEvento(String value) {
        this.dataUltimoEvento = value;
    }

    /**
     * Gets the value of the codigoUltimoEvento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoUltimoEvento() {
        return codigoUltimoEvento;
    }

    /**
     * Sets the value of the codigoUltimoEvento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoUltimoEvento(String value) {
        this.codigoUltimoEvento = value;
    }

    /**
     * Gets the value of the tipoUltimoEvento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoUltimoEvento() {
        return tipoUltimoEvento;
    }

    /**
     * Sets the value of the tipoUltimoEvento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoUltimoEvento(String value) {
        this.tipoUltimoEvento = value;
    }

    /**
     * Gets the value of the descricaoUltimoEvento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricaoUltimoEvento() {
        return descricaoUltimoEvento;
    }

    /**
     * Sets the value of the descricaoUltimoEvento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricaoUltimoEvento(String value) {
        this.descricaoUltimoEvento = value;
    }

    /**
     * Gets the value of the erro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErro() {
        return erro;
    }

    /**
     * Sets the value of the erro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErro(String value) {
        this.erro = value;
    }

    /**
     * Gets the value of the msgErro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgErro() {
        return msgErro;
    }

    /**
     * Sets the value of the msgErro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgErro(String value) {
        this.msgErro = value;
    }

    /**
     * Gets the value of the nuTicket property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuTicket() {
        return nuTicket;
    }

    /**
     * Sets the value of the nuTicket property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuTicket(String value) {
        this.nuTicket = value;
    }

    /**
     * Gets the value of the formaPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * Sets the value of the formaPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormaPagamento(String value) {
        this.formaPagamento = value;
    }

    /**
     * Gets the value of the valorPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValorPagamento() {
        return valorPagamento;
    }

    /**
     * Sets the value of the valorPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValorPagamento(String value) {
        this.valorPagamento = value;
    }

}
