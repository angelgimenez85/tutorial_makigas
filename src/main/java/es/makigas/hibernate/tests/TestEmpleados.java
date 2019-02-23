package es.makigas.hibernate.tests;

import es.makigas.hibernate.modelo.Direccion;
import es.makigas.hibernate.modelo.Empleado;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestEmpleados {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistencia");

    public static void main(String[] args) {
        
        EntityManager manager = emf.createEntityManager();

        Empleado e = new Empleado(10L, "Pérez", "Pepito", LocalDate.of(1979, 6, 6));
        e.setDireccion(new Direccion(15L, "Calle falsa, 123", "Springfield", "Springfield", "EEUU"));
        //Empleado e2 = new Empleado(25L, "Martínez", "José", LocalDate.of(1984, 10, 10));

        manager.getTransaction().begin();
        manager.persist(e);
        //manager.persist(e2);
        manager.getTransaction().commit();
        manager.close();

        imprimirTodo();

    }

    private static void imprimirTodo() {
        
        List<Empleado> empleados;
        EntityManager manager = emf.createEntityManager();
        empleados = (List<Empleado>) manager.createQuery("FROM Empleado").getResultList();

        System.out.println("En esta base de datos hay " + empleados.size() + " empleado/s.");
        
        for(Empleado emp : empleados) {
            System.out.println(emp);
        }
        manager.close();
    }
}
