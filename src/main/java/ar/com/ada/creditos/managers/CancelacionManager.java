package ar.com.ada.creditos.managers;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.creditos.entities.*;

public class CancelacionManager {
    protected SessionFactory sessionFactory;

    public void setup() {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
                                                                                                  // from
                                                                                                  // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw ex;
        }

    }

    public void exit() {
        sessionFactory.close();
    }

    public void create(Cancelacion cancelacion) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(cancelacion);

        session.getTransaction().commit();
        session.close();
    }

    public Cancelacion read(int clienteId) {
        Session session = sessionFactory.openSession();

        Cancelacion cancelacion = session.get(Cancelacion.class, clienteId);

        session.close();

        return cancelacion;
    }


    public void update(Cancelacion cancelacion ) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(cancelacion);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Cancelacion cancelacion) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(cancelacion);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Este metodo en la vida real no debe existir ya qeu puede haber miles de
     * usuarios
     * 
     * @return
     */
    public List<Cliente> buscarTodos() {

        Session session = sessionFactory.openSession();

        /// NUNCA HARCODEAR SQLs nativos en la aplicacion.
        // ESTO es solo para nivel educativo
        Query query = session.createNativeQuery("SELECT * FROM cliente", Cliente.class);

        List<Cliente> todos = query.getResultList();

        return todos;

    }

    /**
     * Busca una lista de clientes por el nombre completo Esta armado para que se
     * pueda generar un SQL Injection y mostrar commo NO debe programarse.
     * 
     * @param nombre
     * @return
     */
    public List<Cliente> buscarPor(String nombre) {
        
            Session session = sessionFactory.openSession();
    
            // SQL Injection vulnerability exposed.
            // Deberia traer solo aquella del nombre y con esto demostrarmos que trae todas
            // si pasamos
            // como nombre: "' or '1'='1"
            // Forma 1: NO hacer JAMAS
            Query query = session.createNativeQuery("SELECT * FROM cliente where nombre = '" + nombre + "'", Cliente.class);
    
            // Forma2: usando SQL con parametros
            Query querySQLConParametros = session.createNativeQuery("SELECT * FROM cliente where nombre = ? ",
                    Cliente.class);
            querySQLConParametros.setParameter(1, nombre);
    
            // Forma3: usando JPQL con parametros con NOMBRE
            Query queryJPQLConParametros = session.createQuery("SELECT c FROM Cliente c where c.nombre = :nombreFiltro",
                    Cliente.class);
            queryJPQLConParametros.setParameter("nombreFiltro", nombre);
    
            List<Cliente> clientes = queryJPQLConParametros.getResultList();
    
            return clientes;
    
        }

    }


