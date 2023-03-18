package com.luisv.jpa1.jpa1.main;

import com.luisv.jpa1.jpa1.modelo.Mensaje;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class BuscaMensajes {
    
    private static String QUERY_BUSQUEDA_MENSAJES = "SELECT m FROM Mensaje m WHERE m.texto LIKE :patron";
    
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mensajes-mysql");
        EntityManager em = emf.createEntityManager();
        
        String palabra = leerTexto("Introduce una palabra: ");
        String patron = "%" + palabra + "%";
       
        Query query = em.createQuery(QUERY_BUSQUEDA_MENSAJES);
        query.setParameter("patron", patron);
        
        List<Mensaje> mensajes = query.getResultList();
        
        if(mensajes.isEmpty()){
            System.out.println("No hay mensajes que contengan la palabra " + palabra);
        }else{
            for(Mensaje m: mensajes){
                System.out.println("Mensaje: " + m.getTexto() + ", autor: " + m.getAutor().getNombre() + "(" + m.getAutor().getCorreo() + ")");
            }
        }
        

    }
    
    private static String leerTexto(String texto){
        String mensaje;
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print(texto);
        try {
            mensaje = sc.nextLine();
            
        } catch (Exception e) {
            mensaje = "Error";
        }
        
        return mensaje;
    }
    
}
