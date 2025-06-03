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
@Table(name = "MUS_SALA")
@NamedQueries({
    @NamedQuery(name = "MusSala.findAll", query = "SELECT m FROM MusSala m"),
    @NamedQuery(name = "MusSala.findBySaId", query = "SELECT m FROM MusSala m WHERE m.saId = :saId"),
    @NamedQuery(name = "MusSala.findBySaMuid", query = "SELECT m FROM MusSala m WHERE m.saMuid = :saMuid"),
    @NamedQuery(name = "MusSala.findBySaNombre", query = "SELECT m FROM MusSala m WHERE m.saNombre = :saNombre"),
    @NamedQuery(name = "MusSala.findBySaDescripcion", query = "SELECT m FROM MusSala m WHERE m.saDescripcion = :saDescripcion")})
public class MusSala implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SA_ID")
    private Integer saId;
    @Basic(optional = false)
    @Column(name = "SA_MUID")
    private Integer saMuid;
    @Basic(optional = false)
    @Column(name = "SA_NOMBRE")
    private String saNombre;
    @Basic(optional = false)
    @Column(name = "SA_DESCRIPCION")
    private String saDescripcion;

    public MusSala() {
    }

    public MusSala(Integer saId) {
        this.saId = saId;
    }

    public MusSala(Integer saId, Integer saMuid, String saNombre, String saDescripcion) {
        this.saId = saId;
        this.saMuid = saMuid;
        this.saNombre = saNombre;
        this.saDescripcion = saDescripcion;
    }

    public Integer getSaId() {
        return saId;
    }

    public void setSaId(Integer saId) {
        this.saId = saId;
    }

    public Integer getSaMuid() {
        return saMuid;
    }

    public void setSaMuid(Integer saMuid) {
        this.saMuid = saMuid;
    }

    public String getSaNombre() {
        return saNombre;
    }

    public void setSaNombre(String saNombre) {
        this.saNombre = saNombre;
    }

    public String getSaDescripcion() {
        return saDescripcion;
    }

    public void setSaDescripcion(String saDescripcion) {
        this.saDescripcion = saDescripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saId != null ? saId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusSala)) {
            return false;
        }
        MusSala other = (MusSala) object;
        if ((this.saId == null && other.saId != null) || (this.saId != null && !this.saId.equals(other.saId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MusSala[ saId=" + saId + " ]";
    }
    
}
