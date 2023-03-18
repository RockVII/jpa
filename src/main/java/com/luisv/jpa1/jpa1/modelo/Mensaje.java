package com.luisv.jpa1.jpa1.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Mensaje implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "message_id")
    private Long id;
    

    @Column(nullable = false)
    private String texto;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    @ManyToOne
    private Autor autor;
    
    @Version
    private int version;

    public Mensaje() {
    }

    public Mensaje(String texto, Date fecha, Autor autor) {
        this.texto = texto;
        this.fecha = fecha;
        this.autor = autor;
    }
    
    public Mensaje(String texto, Autor autor) {
        this.texto = texto;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                ", fecha=" + fecha +
                ", autor=" + autor +
                '}';
    }
    
    
}
