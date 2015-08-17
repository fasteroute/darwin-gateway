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
import javax.xml.bind.annotation.XmlValue;


/**
 * Type used to represent a cancellation or late running reason
 * 
 * <p>Java class for DisruptionReasonType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DisruptionReasonType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1>ReasonCodeType">
 *       &lt;attribute name="tiploc" type="{http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1}TiplocType" />
 *       &lt;attribute name="near" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DisruptionReasonType", namespace = "http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1", propOrder = {
    "value"
})
public class DisruptionReasonType {

    @XmlValue
    protected short value;
    @XmlAttribute(name = "tiploc")
    protected String tiploc;
    @XmlAttribute(name = "near")
    protected Boolean near;

    /**
     * A Darwin Reason Code
     * 
     */
    public short getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(short value) {
        this.value = value;
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
     * Gets the value of the near property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNear() {
        if (near == null) {
            return false;
        } else {
            return near;
        }
    }

    /**
     * Sets the value of the near property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNear(Boolean value) {
        this.near = value;
    }

}
