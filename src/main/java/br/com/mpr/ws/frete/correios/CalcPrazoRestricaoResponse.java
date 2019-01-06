
package br.com.mpr.ws.frete.correios;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="CalcPrazoRestricaoResult" type="{http://tempuri.org/}cResultado"/&gt;
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
    "calcPrazoRestricaoResult"
})
@XmlRootElement(name = "CalcPrazoRestricaoResponse")
public class CalcPrazoRestricaoResponse {

    @XmlElement(name = "CalcPrazoRestricaoResult", required = true)
    protected CResultado calcPrazoRestricaoResult;

    /**
     * Gets the value of the calcPrazoRestricaoResult property.
     * 
     * @return
     *     possible object is
     *     {@link CResultado }
     *     
     */
    public CResultado getCalcPrazoRestricaoResult() {
        return calcPrazoRestricaoResult;
    }

    /**
     * Sets the value of the calcPrazoRestricaoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CResultado }
     *     
     */
    public void setCalcPrazoRestricaoResult(CResultado value) {
        this.calcPrazoRestricaoResult = value;
    }

}
