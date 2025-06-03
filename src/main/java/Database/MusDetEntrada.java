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
@Table(name = "MUS_DET_ENTRADA")
@NamedQueries({
    @NamedQuery(name = "MusDetEntrada.findAll", query = "SELECT m FROM MusDetEntrada m"),
    @NamedQuery(name = "MusDetEntrada.findByDeId", query = "SELECT m FROM MusDetEntrada m WHERE m.deId = :deId"),
    @NamedQuery(name = "MusDetEntrada.findByDeEnid", query = "SELECT m FROM MusDetEntrada m WHERE m.deEnid = :deEnid"),
    @NamedQuery(name = "MusDetEntrada.findByDeSaid", query = "SELECT m FROM MusDetEntrada m WHERE m.deSaid = :deSaid"),
    @NamedQuery(name = "MusDetEntrada.findByDePrecio", query = "SELECT m FROM MusDetEntrada m WHERE m.dePrecio = :dePrecio")})
public class MusDetEntrada implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DE_ID")
    private Integer deId;
    @Basic(optional = false)
    @Column(name = "DE_ENID")
    private Integer deEnid;
    @Basic(optional = false)
    @Column(name = "DE_SAID")
    private Integer deSaid;
    @Basic(optional = false)
    @Column(name = "DE_PRECIO")
    private Double dePrecio;

    public MusDetEntrada() {
    }

    public MusDetEntrada(Integer deId) {
        this.deId = deId;
    }

    public MusDetEntrada(Integer deId, Integer deEnid, Integer deSaid, Double dePrecio) {
        this.deId = deId;
        this.deEnid = deEnid;
        this.deSaid = deSaid;
        this.dePrecio = dePrecio;
    }

    public Integer getDeId() {
        return deId;
    }

    public void setDeId(Integer deId) {
        this.deId = deId;
    }

    public Integer getDeEnid() {
        return deEnid;
    }

    public void setDeEnid(Integer deEnid) {
        this.deEnid = deEnid;
    }

    public Integer getDeSaid() {
        return deSaid;
    }

    public void setDeSaid(Integer deSaid) {
        this.deSaid = deSaid;
    }

    public Double getDePrecio() {
        return dePrecio;
    }

    public void setDePrecio(Double dePrecio) {
        this.dePrecio = dePrecio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deId != null ? deId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusDetEntrada)) {
            return false;
        }
        MusDetEntrada other = (MusDetEntrada) object;
        if ((this.deId == null && other.deId != null) || (this.deId != null && !this.deId.equals(other.deId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MusDetEntrada[ deId=" + deId + " ]";
    }
    
}
