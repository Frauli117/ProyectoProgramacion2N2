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
@Table(name = "MUS_MUSEO")
@NamedQueries({
    @NamedQuery(name = "MusMuseo.findAll", query = "SELECT m FROM MusMuseo m"),
    @NamedQuery(name = "MusMuseo.findByMuId", query = "SELECT m FROM MusMuseo m WHERE m.muId = :muId"),
    @NamedQuery(name = "MusMuseo.findByMuNombre", query = "SELECT m FROM MusMuseo m WHERE m.muNombre = :muNombre"),
    @NamedQuery(name = "MusMuseo.findByMuTipo", query = "SELECT m FROM MusMuseo m WHERE m.muTipo = :muTipo"),
    @NamedQuery(name = "MusMuseo.findByMuUbicacion", query = "SELECT m FROM MusMuseo m WHERE m.muUbicacion = :muUbicacion"),
    @NamedQuery(name = "MusMuseo.findByMuFechaFun", query = "SELECT m FROM MusMuseo m WHERE m.muFechaFun = :muFechaFun"),
    @NamedQuery(name = "MusMuseo.findByMuDirector", query = "SELECT m FROM MusMuseo m WHERE m.muDirector = :muDirector"),
    @NamedQuery(name = "MusMuseo.findByMuSitioWep", query = "SELECT m FROM MusMuseo m WHERE m.muSitioWep = :muSitioWep")})
public class MusMuseo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MU_ID")
    private Integer muId;
    @Basic(optional = false)
    @Column(name = "MU_NOMBRE")
    private String muNombre;
    @Basic(optional = false)
    @Column(name = "MU_TIPO")
    private String muTipo;
    @Basic(optional = false)
    @Column(name = "MU_UBICACION")
    private String muUbicacion;
    @Column(name = "MU_FECHA_FUN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date muFechaFun;
    @Basic(optional = false)
    @Column(name = "MU_DIRECTOR")
    private String muDirector;
    @Basic(optional = false)
    @Column(name = "MU_SITIO_WEP")
    private String muSitioWep;

    public MusMuseo() {
    }

    public MusMuseo(Integer muId) {
        this.muId = muId;
    }

    public MusMuseo(Integer muId, String muNombre, String muTipo, String muUbicacion, String muDirector, String muSitioWep) {
        this.muId = muId;
        this.muNombre = muNombre;
        this.muTipo = muTipo;
        this.muUbicacion = muUbicacion;
        this.muDirector = muDirector;
        this.muSitioWep = muSitioWep;
    }

    public Integer getMuId() {
        return muId;
    }

    public void setMuId(Integer muId) {
        this.muId = muId;
    }

    public String getMuNombre() {
        return muNombre;
    }

    public void setMuNombre(String muNombre) {
        this.muNombre = muNombre;
    }

    public String getMuTipo() {
        return muTipo;
    }

    public void setMuTipo(String muTipo) {
        this.muTipo = muTipo;
    }

    public String getMuUbicacion() {
        return muUbicacion;
    }

    public void setMuUbicacion(String muUbicacion) {
        this.muUbicacion = muUbicacion;
    }

    public Date getMuFechaFun() {
        return muFechaFun;
    }

    public void setMuFechaFun(Date muFechaFun) {
        this.muFechaFun = muFechaFun;
    }

    public String getMuDirector() {
        return muDirector;
    }

    public void setMuDirector(String muDirector) {
        this.muDirector = muDirector;
    }

    public String getMuSitioWep() {
        return muSitioWep;
    }

    public void setMuSitioWep(String muSitioWep) {
        this.muSitioWep = muSitioWep;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (muId != null ? muId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusMuseo)) {
            return false;
        }
        MusMuseo other = (MusMuseo) object;
        if ((this.muId == null && other.muId != null) || (this.muId != null && !this.muId.equals(other.muId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return muNombre;
    }
    
}
