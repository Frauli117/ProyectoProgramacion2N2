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
@Table(name = "MUS_COLECCION")
@NamedQueries({
    @NamedQuery(name = "MusColeccion.findAll", query = "SELECT m FROM MusColeccion m"),
    @NamedQuery(name = "MusColeccion.findByCoId", query = "SELECT m FROM MusColeccion m WHERE m.coId = :coId"),
    @NamedQuery(name = "MusColeccion.findByCoSaid", query = "SELECT m FROM MusColeccion m WHERE m.coSaid = :coSaid"),
    @NamedQuery(name = "MusColeccion.findByCoNombre", query = "SELECT m FROM MusColeccion m WHERE m.coNombre = :coNombre"),
    @NamedQuery(name = "MusColeccion.findByCoSiglo", query = "SELECT m FROM MusColeccion m WHERE m.coSiglo = :coSiglo"),
    @NamedQuery(name = "MusColeccion.findByCoDescripcion", query = "SELECT m FROM MusColeccion m WHERE m.coDescripcion = :coDescripcion")})
public class MusColeccion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CO_ID")
    private Integer coId;
    @Basic(optional = false)
    @Column(name = "CO_SAID")
    private Integer coSaid;
    @Basic(optional = false)
    @Column(name = "CO_NOMBRE")
    private String coNombre;
    @Basic(optional = false)
    @Column(name = "CO_SIGLO")
    private String coSiglo;
    @Basic(optional = false)
    @Column(name = "CO_DESCRIPCION")
    private String coDescripcion;

    public MusColeccion() {
    }

    public MusColeccion(Integer coId) {
        this.coId = coId;
    }

    public MusColeccion(Integer coId, Integer coSaid, String coNombre, String coSiglo, String coDescripcion) {
        this.coId = coId;
        this.coSaid = coSaid;
        this.coNombre = coNombre;
        this.coSiglo = coSiglo;
        this.coDescripcion = coDescripcion;
    }

    public Integer getCoId() {
        return coId;
    }

    public void setCoId(Integer coId) {
        this.coId = coId;
    }

    public Integer getCoSaid() {
        return coSaid;
    }

    public void setCoSaid(Integer coSaid) {
        this.coSaid = coSaid;
    }

    public String getCoNombre() {
        return coNombre;
    }

    public void setCoNombre(String coNombre) {
        this.coNombre = coNombre;
    }

    public String getCoSiglo() {
        return coSiglo;
    }

    public void setCoSiglo(String coSiglo) {
        this.coSiglo = coSiglo;
    }

    public String getCoDescripcion() {
        return coDescripcion;
    }

    public void setCoDescripcion(String coDescripcion) {
        this.coDescripcion = coDescripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coId != null ? coId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusColeccion)) {
            return false;
        }
        MusColeccion other = (MusColeccion) object;
        if ((this.coId == null && other.coId != null) || (this.coId != null && !this.coId.equals(other.coId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MusColeccion[ coId=" + coId + " ]";
    }
    
}
