/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Kenneth
 */
@Entity
@Table(name = "MUS_TEMATICA")
@NamedQueries({
    @NamedQuery(name = "MusTematica.findAll", query = "SELECT m FROM MusTematica m"),
    @NamedQuery(name = "MusTematica.findByTeId", query = "SELECT m FROM MusTematica m WHERE m.teId = :teId"),
    @NamedQuery(name = "MusTematica.findByTeSaid", query = "SELECT m FROM MusTematica m WHERE m.teSaid = :teSaid"),
    @NamedQuery(name = "MusTematica.findByTeNombre", query = "SELECT m FROM MusTematica m WHERE m.teNombre = :teNombre"),
    @NamedQuery(name = "MusTematica.findByTeCaracteristicas", query = "SELECT m FROM MusTematica m WHERE m.teCaracteristicas = :teCaracteristicas"),
    @NamedQuery(name = "MusTematica.findByTeEpoca", query = "SELECT m FROM MusTematica m WHERE m.teEpoca = :teEpoca")})
public class MusTematica implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TE_ID")
    private Integer teId;
    @Basic(optional = false)
    @Column(name = "TE_SAID")
    private Integer teSaid;
    @Basic(optional = false)
    @Column(name = "TE_NOMBRE")
    private String teNombre;
    @Basic(optional = false)
    @Column(name = "TE_CARACTERISTICAS")
    private String teCaracteristicas;
    @Basic(optional = false)
    @Column(name = "TE_EPOCA")
    private String teEpoca;

    public MusTematica() {
    }

    public MusTematica(Integer teId) {
        this.teId = teId;
    }

    public MusTematica(Integer teId, Integer teSaid, String teNombre, String teCaracteristicas, String teEpoca) {
        this.teId = teId;
        this.teSaid = teSaid;
        this.teNombre = teNombre;
        this.teCaracteristicas = teCaracteristicas;
        this.teEpoca = teEpoca;
    }

    public Integer getTeId() {
        return teId;
    }

    public void setTeId(Integer teId) {
        this.teId = teId;
    }

    public Integer getTeSaid() {
        return teSaid;
    }

    public void setTeSaid(Integer teSaid) {
        this.teSaid = teSaid;
    }

    public String getTeNombre() {
        return teNombre;
    }

    public void setTeNombre(String teNombre) {
        this.teNombre = teNombre;
    }

    public String getTeCaracteristicas() {
        return teCaracteristicas;
    }

    public void setTeCaracteristicas(String teCaracteristicas) {
        this.teCaracteristicas = teCaracteristicas;
    }

    public String getTeEpoca() {
        return teEpoca;
    }

    public void setTeEpoca(String teEpoca) {
        this.teEpoca = teEpoca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teId != null ? teId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusTematica)) {
            return false;
        }
        MusTematica other = (MusTematica) object;
        if ((this.teId == null && other.teId != null) || (this.teId != null && !this.teId.equals(other.teId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MusTematica[ teId=" + teId + " ]";
    }
    
}
