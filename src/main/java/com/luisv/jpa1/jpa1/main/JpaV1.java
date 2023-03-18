package com.luisv.jpa1.jpa1.main;

import com.luisv.jpa1.jpa1.modelo.Autor;
import com.luisv.jpa1.jpa1.modelo.Mensaje;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JpaV1 {

    public static void main(String[] jpaStrings) {
        menu();
    }

    private static void menu() {
        int option = 0;
        Scanner sc = new Scanner(System.in);

        while(option != 5) {
            System.out.println("Elije la opción a realizar");
            System.out.println("1. Agregar autor y mensaje");
            System.out.println("2. Agregar mensaje");
            System.out.println("3. Buscar los mensajes de un autor");
            System.out.println("4. Buscar mensajes que contengan una palabra");
            System.out.println("5. Salir");

            try {
                option = Integer.parseInt(readText("Ingresa la opción: "));

                if (option < 1 || option > 4) {
                    System.out.println("Debes ingresar un valor entre 1 y 4");
                } else {
                    executeProgram(option);
                }
            } catch (Exception e) {
                System.out.println("Error: Debes ingresar un valor numérico");
                System.out.println(e.getMessage());
            }
        }
    }

    private static String readText(String message) {
        String text;

        System.out.print(message);
        Scanner sc = new Scanner(System.in);

        try {
            text = sc.nextLine();
        } catch (Exception e) {

            text = "Error";
        }

        return text;
    }

    private static void executeProgram(int option) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mensajes-mysql");
        EntityManager em = emf.createEntityManager();

        switch (option) {
            case 1:
                addAutorAndMessage(emf, em);
                break;
            case 2:
                AddMessage(emf, em);
                break;
            case 3:
                FindMessagesByAutor(emf, em);
                break;
            default:
                FindMessagesByPattern(emf, em);
                break;
        }

    }

    private static void addAutorAndMessage(EntityManagerFactory emf, EntityManager em) {
        System.out.println("**********Agregar autor y mensaje**********");

        try {
            em.getTransaction().begin();

            System.out.println("-Autor");
            String name = readText("Ingresa el nombre del autor: ");
            String email = readText("Ingresa el correo electrónico del autor: ");

            Autor autor = new Autor(email, name);

            em.persist(autor);
            System.out.println("El identificador del autor es: " + autor.getId());

            System.out.println("-Mensaje");
            String mensajeStr = readText("Ingresa el mensaje: ");
            Mensaje mensaje = new Mensaje(mensajeStr, new Date(), autor);

            em.persist(mensaje);
            System.out.println("El identificador del mensaje es: " + mensaje.getId());

            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }

    private static void AddMessage(EntityManagerFactory emf, EntityManager em) {
        System.out.println("**********Agregar mensaje**********");
        em.getTransaction().begin();

        Long idAutor = Long.valueOf(readText("Ingresa el id del autor: "));

        Autor autor = em.find(Autor.class, idAutor);

        if (autor == null) {
            System.out.println("El autor no existe");
        } else {
            String mensajeStr = readText("Ingresa un mensaje: ");

            Mensaje mensaje = new Mensaje(mensajeStr, new Date(), autor);
            em.persist(mensaje);

            System.out.println("Identificador del mensaje: " + mensaje.getId());

            em.getTransaction().commit();

            em.close();
            emf.close();
        }

    }

    private static void FindMessagesByAutor(EntityManagerFactory emf, EntityManager em) {
        System.out.println("**********Buscar los mensajes de un autor**********");

        Long idAutor = Long.valueOf(readText("Ingresa el id del autor: "));

        Autor autor = em.find(Autor.class, idAutor);

        if (autor == null) {
            System.out.println("El autor no existe");
        } else {
            for (Mensaje m : autor.getMensajes()) {
                System.out.println(m.getTexto());
            }
        }

        em.close();
        emf.close();
    }

    private static void FindMessagesByPattern(EntityManagerFactory emf, EntityManager em) {
        System.out.println("**********Buscar mensajes que contengan una palabra**********");

        String PATTERN_FIND_QUERY = "SELECT m FROM Mensaje m WHERE m.texto LIKE :pattern";

        String word = readText("Ingresa la palabra a buscar: ");
        String pattern = "%" + word + "%";

        Query query = em.createQuery(PATTERN_FIND_QUERY);
        query.setParameter("pattern", pattern);

        List<Mensaje> messages = query.getResultList();

        for (Mensaje m : messages) {
            System.out.println("Mensaje: " + m.getTexto() + ", autor: " + m.getAutor().getNombre() + "(" + m.getAutor().getCorreo() + ")");
        }

        em.close();
        emf.close();
    }

}
