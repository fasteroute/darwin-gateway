//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.17 at 11:07:26 AM BST 
//


package uk.fstr.darwingateway.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines the expected Train order at a platform
 * 
 * <p>Java class for TrainOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrainOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="set" type="{http://www.thalesgroup.com/rtti/PushPort/TrainOrder/v1}TrainOrderData"/>
 *         &lt;element name="clear" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/choice>
 *       &lt;attribute name="tiploc" use="required" type="{http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1}TiplocType" />
 *       &lt;attribute name="crs" use="required" type="{http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1}CrsType" />
 *       &lt;attribute name="platform" use="required" type="{http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1}PlatformType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrainOrder", namespace = "http://www.thalesgroup.com/rtti/PushPort/TrainOrder/v1", propOrder = {
    "set",
    "clear"
})
public class TrainOrder {

    protected TrainOrderData set;
    protected Object clear;
    @XmlAttribute(name = "tiploc", required = true)
    protected String tiploc;
    @XmlAttribute(name = "crs", required = true)
    protected String crs;
    @XmlAttribute(name = "platform", required = true)
    protected String platform;

    /**
     * Gets the value of the set property.
     * 
     * @return
     *     possible object is
     *     {@link TrainOrderData }
     *     
     */
    public TrainOrderData getSet() {
        return set;
    }

    /**
     * Sets the value of the set property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrainOrderData }
     *     
     */
    public void setSet(TrainOrderData value) {
        this.set = value;
    }

    /**
     * Gets the value of the clear property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getClear() {
        return clear;
    }

    /**
     * Sets the value of the clear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setClear(Object value) {
        this.clear = value;
    }

    /**
     * Gets the value of the tiploc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTiploc() {
        return tiploc;
    }

    /**
     * Sets the value of the tiploc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTiploc(String value) {
        this.tiploc = value;
    }

    /**
     * Gets the value of the crs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrs() {
        return crs;
    }

    /**
     * Sets the value of the crs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrs(String value) {
        this.crs = value;
    }

    /**
     * Gets the value of the platform property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Sets the value of the platform property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlatform(String value) {
        this.platform = value;
    }

}
