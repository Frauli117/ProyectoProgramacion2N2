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
@Table(name = "MUS_VALORACION")
@NamedQueries({
    @NamedQuery(name = "MusValoracion.findAll", query = "SELECT m FROM MusValoracion m"),
    @NamedQuery(name = "MusValoracion.findByVaId", query = "SELECT m FROM MusValoracion m WHERE m.vaId = :vaId"),
    @NamedQuery(name = "MusValoracion.findByVaSaid", query = "SELECT m FROM MusValoracion m WHERE m.vaSaid = :vaSaid"),
    @NamedQuery(name = "MusValoracion.findByVaEstrellas", query = "SELECT m FROM MusValoracion m WHERE m.vaEstrellas = :vaEstrellas"),
    @NamedQuery(name = "MusValoracion.findByVaComentario", query = "SELECT m FROM MusValoracion m WHERE m.vaComentario = :vaComentario")})
public class MusValoracion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "VA_ID")
    private Integer vaId;
    @Basic(optional = false)
    @Column(name = "VA_SAID")
    private Integer vaSaid;
    @Basic(optional = false)
    @Column(name = "VA_ESTRELLAS")
    private Integer vaEstrellas;
    @Basic(optional = false)
    @Column(name = "VA_COMENTARIO")
    private String vaComentario;

    public MusValoracion() {
    }

    public MusValoracion(Integer vaId) {
        this.vaId = vaId;
    }

    public MusValoracion(Integer vaId, Integer vaSaid, Integer vaEstrellas, String vaComentario) {
        this.vaId = vaId;
        this.vaSaid = vaSaid;
        this.vaEstrellas = vaEstrellas;
        this.vaComentario = vaComentario;
    }

    public Integer getVaId() {
        return vaId;
    }

    public void setVaId(Integer vaId) {
        this.vaId = vaId;
    }

    public Integer getVaSaid() {
        return vaSaid;
    }

    public void setVaSaid(Integer vaSaid) {
        this.vaSaid = vaSaid;
    }

    public Integer getVaEstrellas() {
        return vaEstrellas;
    }

    public void setVaEstrellas(Integer vaEstrellas) {
        this.vaEstrellas = vaEstrellas;
    }

    public String getVaComentario() {
        return vaComentario;
    }

    public void setVaComentario(String vaComentario) {
        this.vaComentario = vaComentario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vaId != null ? vaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusValoracion)) {
            return false;
        }
        MusValoracion other = (MusValoracion) object;
        if ((this.vaId == null && other.vaId != null) || (this.vaId != null && !this.vaId.equals(other.vaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MusValoracion[ vaId=" + vaId + " ]";
    }
    
}
