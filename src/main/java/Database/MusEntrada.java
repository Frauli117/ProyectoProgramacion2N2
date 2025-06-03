/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "MUS_ENTRADA")
@NamedQueries({
    @NamedQuery(name = "MusEntrada.findAll", query = "SELECT m FROM MusEntrada m"),
    @NamedQuery(name = "MusEntrada.findByEnId", query = "SELECT m FROM MusEntrada m WHERE m.enId = :enId"),
    @NamedQuery(name = "MusEntrada.findByEnFecha", query = "SELECT m FROM MusEntrada m WHERE m.enFecha = :enFecha"),
    @NamedQuery(name = "MusEntrada.findByEnTotal", query = "SELECT m FROM MusEntrada m WHERE m.enTotal = :enTotal"),
    @NamedQuery(name = "MusEntrada.findByEnQr", query = "SELECT m FROM MusEntrada m WHERE m.enQr = :enQr"),
    @NamedQuery(name = "MusEntrada.findByEnNombre", query = "SELECT m FROM MusEntrada m WHERE m.enNombre = :enNombre")})
public class MusEntrada implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EN_ID")
    private Integer enId;
    @Basic(optional = false)
    @Column(name = "EN_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enFecha;
    @Basic(optional = false)
    @Column(name = "EN_TOTAL")
    private Double enTotal;
    @Basic(optional = false)
    @Column(name = "EN_QR")
    private String enQr;
    @Basic(optional = false)
    @Column(name = "EN_NOMBRE")
    private String enNombre;
    @JoinColumn(name = "EN_COMID", referencedColumnName = "CM_ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MusComision enComid;

    public MusEntrada() {
    }

    public MusEntrada(Integer enId) {
        this.enId = enId;
    }

    public MusEntrada(Integer enId, Date enFecha, Double enTotal, String enQr, String enNombre) {
        this.enId = enId;
        this.enFecha = enFecha;
        this.enTotal = enTotal;
        this.enQr = enQr;
        this.enNombre = enNombre;
    }

    public Integer getEnId() {
        return enId;
    }

    public void setEnId(Integer enId) {
        this.enId = enId;
    }

    public Date getEnFecha() {
        return enFecha;
    }

    public void setEnFecha(Date enFecha) {
        this.enFecha = enFecha;
    }

    public Double getEnTotal() {
        return enTotal;
    }

    public void setEnTotal(Double enTotal) {
        this.enTotal = enTotal;
    }

    public String getEnQr() {
        return enQr;
    }

    public void setEnQr(String enQr) {
        this.enQr = enQr;
    }

    public String getEnNombre() {
        return enNombre;
    }

    public void setEnNombre(String enNombre) {
        this.enNombre = enNombre;
    }

    public MusComision getEnComid() {
        return enComid;
    }

    public void setEnComid(MusComision enComid) {
        this.enComid = enComid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enId != null ? enId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusEntrada)) {
            return false;
        }
        MusEntrada other = (MusEntrada) object;
        if ((this.enId == null && other.enId != null) || (this.enId != null && !this.enId.equals(other.enId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MusEntrada[ enId=" + enId + " ]";
    }
    
}
