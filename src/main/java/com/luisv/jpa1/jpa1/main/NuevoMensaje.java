package com.luisv.jpa1.jpa1.main;

import com.luisv.jpa1.jpa1.modelo.Autor;
import com.luisv.jpa1.jpa1.modelo.Mensaje;
import java.util.Date;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class NuevoMensaje {
    
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mensajes-mysql");
        EntityManager        em  = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Long idAutor = Long.valueOf(leerTexto("Ingresa el id del autor: "));
            
            Autor autor = em.find(Autor.class, idAutor);
            
            if(autor == null){
                System.out.println("El autor no existe");
            }else{
                String mensajeStr = leerTexto("Ingresa mensaje: ");
                Mensaje mensaje = new Mensaje(mensajeStr,new Date(),autor);
                em.persist(mensaje);
                
                System.out.println("Mensaje Id: " + mensaje.getId());
                
                em.getTransaction().commit();
            }
            
            
            
        } catch (Exception e) {
            
            em.getTransaction().rollback();
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally{
            em.close();
            emf.close();
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
