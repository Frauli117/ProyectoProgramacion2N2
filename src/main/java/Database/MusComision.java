/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Kenneth
 */
@Entity
@Table(name = "MUS_COMISION")
@NamedQueries({
    @NamedQuery(name = "MusComision.findAll", query = "SELECT m FROM MusComision m"),
    @NamedQuery(name = "MusComision.findByCmId", query = "SELECT m FROM MusComision m WHERE m.cmId = :cmId"),
    @NamedQuery(name = "MusComision.findByCmTipo", query = "SELECT m FROM MusComision m WHERE m.cmTipo = :cmTipo"),
    @NamedQuery(name = "MusComision.findByCmPorcentaje", query = "SELECT m FROM MusComision m WHERE m.cmPorcentaje = :cmPorcentaje")})
public class MusComision implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CM_ID")
    private Integer cmId;
    @Basic(optional = false)
    @Column(name = "CM_TIPO")
    private String cmTipo;
    @Basic(optional = false)
    @Column(name = "CM_PORCENTAJE")
    private Double cmPorcentaje;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enComid", fetch = FetchType.EAGER)
    private Collection<MusEntrada> musEntradaCollection;

    public MusComision() {
    }

    public MusComision(Integer cmId) {
        this.cmId = cmId;
    }

    public MusComision(Integer cmId, String cmTipo, Double cmPorcentaje) {
        this.cmId = cmId;
        this.cmTipo = cmTipo;
        this.cmPorcentaje = cmPorcentaje;
    }

    public Integer getCmId() {
        return cmId;
    }

    public void setCmId(Integer cmId) {
        this.cmId = cmId;
    }

    public String getCmTipo() {
        return cmTipo;
    }

    public void setCmTipo(String cmTipo) {
        this.cmTipo = cmTipo;
    }

    public Double getCmPorcentaje() {
        return cmPorcentaje;
    }

    public void setCmPorcentaje(Double cmPorcentaje) {
        this.cmPorcentaje = cmPorcentaje;
    }

    public Collection<MusEntrada> getMusEntradaCollection() {
        return musEntradaCollection;
    }

    public void setMusEntradaCollection(Collection<MusEntrada> musEntradaCollection) {
        this.musEntradaCollection = musEntradaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cmId != null ? cmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusComision)) {
            return false;
        }
        MusComision other = (MusComision) object;
        if ((this.cmId == null && other.cmId != null) || (this.cmId != null && !this.cmId.equals(other.cmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MusComision[ cmId=" + cmId + " ]";
    }
    
}
