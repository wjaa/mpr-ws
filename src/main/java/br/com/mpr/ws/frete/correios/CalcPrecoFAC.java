
package br.com.mpr.ws.frete.correios;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected         content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nCdServico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nVlPeso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strDataCalculo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nCdServico",
    "nVlPeso",
    "strDataCalculo"
})
@XmlRootElement(name = "CalcPrecoFAC")
public class CalcPrecoFAC {

    protected String nCdServico;
    protected String nVlPeso;
    protected String strDataCalculo;

    /**
     * Gets the value of the nCdServico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNCdServico() {
        return nCdServico;
    }

    /**
     * Sets the value of the nCdServico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNCdServico(String value) {
        this.nCdServico = value;
    }

    /**
     * Gets the value of the nVlPeso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNVlPeso() {
        return nVlPeso;
    }

    /**
     * Sets the value of the nVlPeso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNVlPeso(String value) {
        this.nVlPeso = value;
    }

    /**
     * Gets the value of the strDataCalculo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrDataCalculo() {
        return strDataCalculo;
    }

    /**
     * Sets the value of the strDataCalculo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrDataCalculo(String value) {
        this.strDataCalculo = value;
    }

}
