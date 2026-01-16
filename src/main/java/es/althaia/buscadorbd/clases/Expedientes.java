/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.althaia.buscadorbd.clases;


//import jakarta.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Ainara
 */
@Entity
@Table(name = "EXPEDIENTES")
@NamedQueries({
    @NamedQuery(name = "Expedientes.findAll", query = "SELECT e FROM Expedientes e"),
    @NamedQuery(name = "Expedientes.findByNExpediente", query = "SELECT e FROM Expedientes e WHERE e.nExpediente = :nExpediente"),
    @NamedQuery(name = "Expedientes.findByAsunto", query = "SELECT e FROM Expedientes e WHERE e.asunto = :asunto"),
    @NamedQuery(name = "Expedientes.findByFechaInicio", query = "SELECT e FROM Expedientes e WHERE e.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Expedientes.findByFechaFin", query = "SELECT e FROM Expedientes e WHERE e.fechaFin = :fechaFin")})
public class Expedientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "N_EXPEDIENTE")
    private String nExpediente;
    @Column(name = "ASUNTO")
    private String asunto;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    @ManyToOne
//    @JsonbTransient
    private Clientes idCliente;

    public Expedientes() {
    }

    public Expedientes(String nExpediente) {
        this.nExpediente = nExpediente;
    }

    public String getNExpediente() {
        return nExpediente;
    }

    public void setNExpediente(String nExpediente) {
        this.nExpediente = nExpediente;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Clientes getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Clientes idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nExpediente != null ? nExpediente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expedientes)) {
            return false;
        }
        Expedientes other = (Expedientes) object;
        return !((this.nExpediente == null && other.nExpediente != null) || (this.nExpediente != null && !this.nExpediente.equals(other.nExpediente)));
    }

    @Override
    public String toString() {
        return "Expedientes{" + "nExpediente=" + nExpediente + ", asunto=" + asunto + ", fechaInicio=" + fechaInicio + 
                ", fechaFin=" + fechaFin + ", idCliente=" + idCliente.getNif() + " - " + idCliente.getNombre()+ '}';
    }

}
