/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.althaia.buscadorbd.clases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Ainara
 */
@Entity
@Table(name = "CLIENTES")
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
    @NamedQuery(name = "Clientes.findByIdCliente", query = "SELECT c FROM Clientes c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Clientes.findByNombre", query = "SELECT c FROM Clientes c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Clientes.findByPoblacion", query = "SELECT c FROM Clientes c WHERE c.poblacion = :poblacion"),
    @NamedQuery(name = "Clientes.findByProvincia", query = "SELECT c FROM Clientes c WHERE c.provincia = :provincia"),
    @NamedQuery(name = "Clientes.findByNif", query = "SELECT c FROM Clientes c WHERE c.nif = :nif"),
    @NamedQuery(name = "Clientes.findByTfFax", query = "SELECT c FROM Clientes c WHERE c.tfFax = :tfFax"),
    @NamedQuery(name = "Clientes.findByEmail", query = "SELECT c FROM Clientes c WHERE c.email = :email")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private Long idCliente;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "POBLACION")
    private String poblacion;
    @Column(name = "PROVINCIA")
    private String provincia;
    @Column(name = "NIF")
    private String nif;
    @Column(name = "TF_FAX")
    private String tfFax;
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(mappedBy = "idCliente", fetch = FetchType.EAGER)
    private Collection<Expedientes> expedientesCollection;

    public Clientes() {
    }

    public Clientes(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Clientes(Long idCliente, String nombre) {
        this.idCliente = idCliente;
        this.nombre = nombre;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTfFax() {
        return tfFax;
    }

    public void setTfFax(String tfFax) {
        this.tfFax = tfFax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Expedientes> getExpedientesCollection() {
        return expedientesCollection;
    }

    public void setExpedientesCollection(Collection<Expedientes> expedientesCollection) {
        this.expedientesCollection = expedientesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        return !((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente)));
    }

    @Override
    public String toString() {
        return "Clientes{" + "idCliente=" + idCliente + ", nombre=" + nombre + ", direccion=" + direccion + ", poblacion=" + poblacion + ", provincia=" + provincia + ", nif=" + nif + ", tfFax=" + tfFax + ", email=" + email + ", expedientesCollection=" + expedientesCollection + '}';
    }

}
