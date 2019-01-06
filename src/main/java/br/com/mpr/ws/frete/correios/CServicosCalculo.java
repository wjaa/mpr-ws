
package br.com.mpr.ws.frete.correios;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cServicosCalculo complex type.
 * 
 * <p>The following schema fragment specifies the expected         content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cServicosCalculo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descricao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="calcula_preco" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="calcula_prazo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="erro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="msgErro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cServicosCalculo", propOrder = {
    "codigo",
    "descricao",
    "calculaPreco",
    "calculaPrazo",
    "erro",
    "msgErro"
})
public class CServicosCalculo {

    protected String codigo;
    protected String descricao;
    @XmlElement(name = "calcula_preco")
    protected String calculaPreco;
    @XmlElement(name = "calcula_prazo")
    protected String calculaPrazo;
    protected String erro;
    protected String msgErro;

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
     * Gets the value of the descricao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Sets the value of the descricao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricao(String value) {
        this.descricao = value;
    }

    /**
     * Gets the value of the calculaPreco property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalculaPreco() {
        return calculaPreco;
    }

    /**
     * Sets the value of the calculaPreco property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalculaPreco(String value) {
        this.calculaPreco = value;
    }

    /**
     * Gets the value of the calculaPrazo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalculaPrazo() {
        return calculaPrazo;
    }

    /**
     * Sets the value of the calculaPrazo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalculaPrazo(String value) {
        this.calculaPrazo = value;
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

}
