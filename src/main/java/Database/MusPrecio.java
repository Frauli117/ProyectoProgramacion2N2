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
@Table(name = "MUS_PRECIO")
@NamedQueries({
    @NamedQuery(name = "MusPrecio.findAll", query = "SELECT m FROM MusPrecio m"),
    @NamedQuery(name = "MusPrecio.findByPrId", query = "SELECT m FROM MusPrecio m WHERE m.prId = :prId"),
    @NamedQuery(name = "MusPrecio.findByPrSaid", query = "SELECT m FROM MusPrecio m WHERE m.prSaid = :prSaid"),
    @NamedQuery(name = "MusPrecio.findByPrLunsab", query = "SELECT m FROM MusPrecio m WHERE m.prLunsab = :prLunsab"),
    @NamedQuery(name = "MusPrecio.findByPrDomingo", query = "SELECT m FROM MusPrecio m WHERE m.prDomingo = :prDomingo")})
public class MusPrecio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PR_ID")
    private Integer prId;
    @Basic(optional = false)
    @Column(name = "PR_SAID")
    private Integer prSaid;
    @Basic(optional = false)
    @Column(name = "PR_LUNSAB")
    private Double prLunsab;
    @Basic(optional = false)
    @Column(name = "PR_DOMINGO")
    private Double prDomingo;

    public MusPrecio() {
    }

    public MusPrecio(Integer prId) {
        this.prId = prId;
    }

    public MusPrecio(Integer prId, Integer prSaid, Double prLunsab, Double prDomingo) {
        this.prId = prId;
        this.prSaid = prSaid;
        this.prLunsab = prLunsab;
        this.prDomingo = prDomingo;
    }

    public Integer getPrId() {
        return prId;
    }

    public void setPrId(Integer prId) {
        this.prId = prId;
    }

    public Integer getPrSaid() {
        return prSaid;
    }

    public void setPrSaid(Integer prSaid) {
        this.prSaid = prSaid;
    }

    public Double getPrLunsab() {
        return prLunsab;
    }

    public void setPrLunsab(Double prLunsab) {
        this.prLunsab = prLunsab;
    }

    public Double getPrDomingo() {
        return prDomingo;
    }

    public void setPrDomingo(Double prDomingo) {
        this.prDomingo = prDomingo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prId != null ? prId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusPrecio)) {
            return false;
        }
        MusPrecio other = (MusPrecio) object;
        if ((this.prId == null && other.prId != null) || (this.prId != null && !this.prId.equals(other.prId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MusPrecio[ prId=" + prId + " ]";
    }
    
}
