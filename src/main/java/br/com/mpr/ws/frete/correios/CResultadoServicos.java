
package br.com.mpr.ws.frete.correios;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cResultadoServicos complex type.
 * 
 * <p>The following schema fragment specifies the expected         content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cResultadoServicos"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServicosCalculo" type="{http://tempuri.org/}ArrayOfCServicosCalculo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cResultadoServicos", propOrder = {
    "servicosCalculo"
})
public class CResultadoServicos {

    @XmlElement(name = "ServicosCalculo")
    protected ArrayOfCServicosCalculo servicosCalculo;

    /**
     * Gets the value of the servicosCalculo property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCServicosCalculo }
     *     
     */
    public ArrayOfCServicosCalculo getServicosCalculo() {
        return servicosCalculo;
    }

    /**
     * Sets the value of the servicosCalculo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCServicosCalculo }
     *     
     */
    public void setServicosCalculo(ArrayOfCServicosCalculo value) {
        this.servicosCalculo = value;
    }

}
