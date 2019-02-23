
package es.makigas.hibernate.tests;

import es.makigas.hibernate.modelo.Autor;
import es.makigas.hibernate.modelo.Libro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestAutores {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistencia");
    
    public static void main(String[] args) {
        crearDatos();
        imprimirDatos();
        remover(4L);
        imprimirDatos();
    }

    static void crearDatos() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Autor a1 = new Autor(1L, "Pablo Pérez", "Española");
        Autor a2 = new Autor(2L, "Elena Gómez", "Mexicana");
        Autor a3 = new Autor(3L, "Miguel López", "Chilena");
        
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        
        em.persist(new Libro(1L, "Programar en Java es fácil", a2));
        em.persist(new Libro(2L, "Cómo vestirse con estilo", a3));
        em.persist(new Libro(3L, "Cómo cocinar sin quemar la cocina", a1));
        em.persist(new Libro(4L, "Programar en Cobol es divertido", a2));
        em.persist(new Libro(5L, "Programar en Cobol no es divertido", a2));
        
        /*Libro l = new Libro();
        l.setId(1L);
        l.setTitulo("Programar en Java es fácil");
        em.persist(l);
        
        Autor a1 = new Autor(1L, "Pablo Pérez", "Española");
        a1.addLibro(l);
        em.persist(a1);*/
        
        em.getTransaction().commit();
        em.close();
    }
    
    private static void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Libro libro = em.find(Libro.class, id);
        Autor autor = libro.getAutor();
        autor.removeLibro(libro);
        em.getTransaction().commit();
        em.close();
    }

    private static void imprimirDatos() {
        EntityManager em = emf.createEntityManager();
        Autor autor = em.find(Autor.class, 2L);
        System.out.println(autor);
        List<Libro> libros = autor.getLibros();
        for (Libro libro : libros) {
            System.out.println(libro.getTitulo());
        }
        em.close();
    }
    
}
