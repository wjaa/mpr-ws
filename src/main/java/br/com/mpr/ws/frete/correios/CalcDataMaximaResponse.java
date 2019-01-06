
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
 *         &lt;element name="CalcDataMaximaResult" type="{http://tempuri.org/}cResultadoObjeto"/&gt;
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
    "calcDataMaximaResult"
})
@XmlRootElement(name = "CalcDataMaximaResponse")
public class CalcDataMaximaResponse {

    @XmlElement(name = "CalcDataMaximaResult", required = true)
    protected CResultadoObjeto calcDataMaximaResult;

    /**
     * Gets the value of the calcDataMaximaResult property.
     * 
     * @return
     *     possible object is
     *     {@link CResultadoObjeto }
     *     
     */
    public CResultadoObjeto getCalcDataMaximaResult() {
        return calcDataMaximaResult;
    }

    /**
     * Sets the value of the calcDataMaximaResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CResultadoObjeto }
     *     
     */
    public void setCalcDataMaximaResult(CResultadoObjeto value) {
        this.calcDataMaximaResult = value;
    }

}
