package com.luisv.jpa1.jpa1.main;

import com.luisv.jpa1.jpa1.modelo.Autor;
import com.luisv.jpa1.jpa1.modelo.Mensaje;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AutorMensaje {
    
    public static void main(String[] args) {       
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mensajes-mysql");
        EntityManager        em  = emf.createEntityManager();
        
        try {                          
            em.getTransaction().begin();

            String email = leerTexto("Ingresa el correo electrónico del autor: ");
            String name  = leerTexto("Ingresa el nombre del autor: ");

            Autor autor = new Autor(email,name);
            em.persist(autor);

            System.out.println("Identificador del autor: " + autor.getId());


            String mensajeStr = leerTexto("Ingresa el mensaje: ");
            Mensaje mensaje = new Mensaje(mensajeStr, new Date(), autor);
            em.persist(mensaje);

            System.out.println("El identificador del mensaje es: " + mensaje.getId());

            em.getTransaction().commit();
            
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
        
        Scanner scanner = new Scanner(System.in);
        String mensaje;
        
        try {
            System.out.print(texto);       
            mensaje = scanner.nextLine();
        
        
        } catch (Exception e) {
            mensaje = "Error";
        }
        
        return mensaje;
    }
    
}
