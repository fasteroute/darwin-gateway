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
 * Describes the identifier of a train in the train order
 * 
 * <p>Java class for TrainOrderItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrainOrderItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="rid">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1>RIDType">
 *                 &lt;attGroup ref="{http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1}CircularTimes"/>
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="trainID" type="{http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1}TrainIdType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrainOrderItem", namespace = "http://www.thalesgroup.com/rtti/PushPort/TrainOrder/v1", propOrder = {
    "rid",
    "trainID"
})
public class TrainOrderItem {

    protected TrainOrderItem.Rid rid;
    protected String trainID;

    /**
     * Gets the value of the rid property.
     * 
     * @return
     *     possible object is
     *     {@link TrainOrderItem.Rid }
     *     
     */
    public TrainOrderItem.Rid getRid() {
        return rid;
    }

    /**
     * Sets the value of the rid property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrainOrderItem.Rid }
     *     
     */
    public void setRid(TrainOrderItem.Rid value) {
        this.rid = value;
    }

    /**
     * Gets the value of the trainID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrainID() {
        return trainID;
    }

    /**
     * Sets the value of the trainID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrainID(String value) {
        this.trainID = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1>RIDType">
     *       &lt;attGroup ref="{http://www.thalesgroup.com/rtti/PushPort/CommonTypes/v1}CircularTimes"/>
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Rid {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "wta")
        protected String wta;
        @XmlAttribute(name = "wtd")
        protected String wtd;
        @XmlAttribute(name = "wtp")
        protected String wtp;
        @XmlAttribute(name = "pta")
        protected String pta;
        @XmlAttribute(name = "ptd")
        protected String ptd;

        /**
         * RTTI Train ID Type
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the wta property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWta() {
            return wta;
        }

        /**
         * Sets the value of the wta property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWta(String value) {
            this.wta = value;
        }

        /**
         * Gets the value of the wtd property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWtd() {
            return wtd;
        }

        /**
         * Sets the value of the wtd property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWtd(String value) {
            this.wtd = value;
        }

        /**
         * Gets the value of the wtp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWtp() {
            return wtp;
        }

        /**
         * Sets the value of the wtp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWtp(String value) {
            this.wtp = value;
        }

        /**
         * Gets the value of the pta property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPta() {
            return pta;
        }

        /**
         * Sets the value of the pta property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPta(String value) {
            this.pta = value;
        }

        /**
         * Gets the value of the ptd property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPtd() {
            return ptd;
        }

        /**
         * Sets the value of the ptd property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPtd(String value) {
            this.ptd = value;
        }

    }

}
