package com.luisv.jpa1.jpa1.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "autor")
public class Autor implements Serializable {

    public Autor() {
    }

    public Autor(String correo, String nombre) {
        this.correo = correo;
        this.nombre = nombre;
    }
       
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "autor_id")
    private Long id;
    
    @Column(name = "email", nullable = false, unique = true)
    private String correo;
    
    @Column(name = "name", nullable = false)
    private String nombre;
    
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Mensaje> mensajes;
    
    @Version
    private int version;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) { 
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Autor autor = (Autor) o;

        return !(id != null ? !id.equals(autor.id) : autor.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", mensajes=" + mensajes +
                '}';
    }
      
}

