
package br.com.mpr.ws.frete.correios;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCObjeto complex type.
 * 
 * <p>The following schema fragment specifies the expected         content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCObjeto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cObjeto" type="{http://tempuri.org/}cObjeto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCObjeto", propOrder = {
    "cObjeto"
})
public class ArrayOfCObjeto {

    protected List<CObjeto> cObjeto;

    /**
     * Gets the value of the cObjeto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cObjeto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCObjeto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CObjeto }
     * 
     * 
     */
    public List<CObjeto> getCObjeto() {
        if (cObjeto == null) {
            cObjeto = new ArrayList<CObjeto>();
        }
        return this.cObjeto;
    }

}
