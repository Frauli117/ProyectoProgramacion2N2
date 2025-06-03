/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Kenneth
 */
@Entity
@Table(name = "MUS_ESPECIE")
@NamedQueries({
    @NamedQuery(name = "MusEspecie.findAll", query = "SELECT m FROM MusEspecie m"),
    @NamedQuery(name = "MusEspecie.findByEsId", query = "SELECT m FROM MusEspecie m WHERE m.esId = :esId"),
    @NamedQuery(name = "MusEspecie.findByEsCoid", query = "SELECT m FROM MusEspecie m WHERE m.esCoid = :esCoid"),
    @NamedQuery(name = "MusEspecie.findByEsNombreCient", query = "SELECT m FROM MusEspecie m WHERE m.esNombreCient = :esNombreCient"),
    @NamedQuery(name = "MusEspecie.findByEsNombreComun", query = "SELECT m FROM MusEspecie m WHERE m.esNombreComun = :esNombreComun"),
    @NamedQuery(name = "MusEspecie.findByEsExtincion", query = "SELECT m FROM MusEspecie m WHERE m.esExtincion = :esExtincion"),
    @NamedQuery(name = "MusEspecie.findByEsEpoca", query = "SELECT m FROM MusEspecie m WHERE m.esEpoca = :esEpoca"),
    @NamedQuery(name = "MusEspecie.findByEsPeso", query = "SELECT m FROM MusEspecie m WHERE m.esPeso = :esPeso"),
    @NamedQuery(name = "MusEspecie.findByEsTamano", query = "SELECT m FROM MusEspecie m WHERE m.esTamano = :esTamano"),
    @NamedQuery(name = "MusEspecie.findByEsCaracteristicas", query = "SELECT m FROM MusEspecie m WHERE m.esCaracteristicas = :esCaracteristicas")})
public class MusEspecie implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ES_ID")
    private Integer esId;
    @Basic(optional = false)
    @Column(name = "ES_COID")
    private Integer esCoid;
    @Basic(optional = false)
    @Column(name = "ES_NOMBRE_CIENT")
    private String esNombreCient;
    @Basic(optional = false)
    @Column(name = "ES_NOMBRE_COMUN")
    private String esNombreComun;
    @Column(name = "ES_EXTINCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date esExtincion;
    @Basic(optional = false)
    @Column(name = "ES_EPOCA")
    private String esEpoca;
    @Basic(optional = false)
    @Column(name = "ES_PESO")
    private Double esPeso;
    @Basic(optional = false)
    @Column(name = "ES_TAMANO")
    private Double esTamano;
    @Basic(optional = false)
    @Column(name = "ES_CARACTERISTICAS")
    private String esCaracteristicas;

    public MusEspecie() {
    }

    public MusEspecie(Integer esId) {
        this.esId = esId;
    }

    public MusEspecie(Integer esId, Integer esCoid, String esNombreCient, String esNombreComun, String esEpoca, Double esPeso, Double esTamano, String esCaracteristicas) {
        this.esId = esId;
        this.esCoid = esCoid;
        this.esNombreCient = esNombreCient;
        this.esNombreComun = esNombreComun;
        this.esEpoca = esEpoca;
        this.esPeso = esPeso;
        this.esTamano = esTamano;
        this.esCaracteristicas = esCaracteristicas;
    }

    public Integer getEsId() {
        return esId;
    }

    public void setEsId(Integer esId) {
        this.esId = esId;
    }

    public Integer getEsCoid() {
        return esCoid;
    }

    public void setEsCoid(Integer esCoid) {
        this.esCoid = esCoid;
    }

    public String getEsNombreCient() {
        return esNombreCient;
    }

    public void setEsNombreCient(String esNombreCient) {
        this.esNombreCient = esNombreCient;
    }

    public String getEsNombreComun() {
        return esNombreComun;
    }

    public void setEsNombreComun(String esNombreComun) {
        this.esNombreComun = esNombreComun;
    }

    public Date getEsExtincion() {
        return esExtincion;
    }

    public void setEsExtincion(Date esExtincion) {
        this.esExtincion = esExtincion;
    }

    public String getEsEpoca() {
        return esEpoca;
    }

    public void setEsEpoca(String esEpoca) {
        this.esEpoca = esEpoca;
    }

    public Double getEsPeso() {
        return esPeso;
    }

    public void setEsPeso(Double esPeso) {
        this.esPeso = esPeso;
    }

    public Double getEsTamano() {
        return esTamano;
    }

    public void setEsTamano(Double esTamano) {
        this.esTamano = esTamano;
    }

    public String getEsCaracteristicas() {
        return esCaracteristicas;
    }

    public void setEsCaracteristicas(String esCaracteristicas) {
        this.esCaracteristicas = esCaracteristicas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (esId != null ? esId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusEspecie)) {
            return false;
        }
        MusEspecie other = (MusEspecie) object;
        if ((this.esId == null && other.esId != null) || (this.esId != null && !this.esId.equals(other.esId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MusEspecie[ esId=" + esId + " ]";
    }
    
}
