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
import ar.com.ada.creditos.entities.reportes.PrestamoPorCliente;
import ar.com.ada.creditos.entities.reportes.ReportePrestamo;

public class PrestamoManager {



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

    public void create(Prestamo prestamo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(prestamo);

        session.getTransaction().commit();
        session.close();
    }

    public Prestamo read(int prestamoId) {
        Session session = sessionFactory.openSession();

        Prestamo prestamo = session.get(Prestamo.class, prestamoId);

        session.close();

        return prestamo;
    }

  

    public void update(Prestamo prestamo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(prestamo);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Prestamo prestamo) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(prestamo);

        session.getTransaction().commit();
        session.close();
    }


    /**
     * Este metodo en la vida real no debe existir ya qeu puede haber miles de
     * usuarios
     * 
     * @return
     */
    public List<Prestamo> buscarTodosLosPrestamos() {

        Session session = sessionFactory.openSession();

        /// NUNCA HARCODEAR SQLs nativos en la aplicacion.
        // ESTO es solo para nivel educativo
        Query query = session.createNativeQuery("SELECT * FROM prestamo", Prestamo.class);

        List<Prestamo> todos = query.getResultList();

        return todos;

    }

    /**
     * Busca una lista de clientes por el nombre completo Esta armado para que se
     * pueda generar un SQL Injection y mostrar commo NO debe programarse.
     * 
     * @param nombre
     * @return
     */
    public List<Prestamo> buscarPor(String nombre) {

        Session session = sessionFactory.openSession();

        // SQL Injection vulnerability exposed.
        // Deberia traer solo aquella del nombre y con esto demostrarmos que trae todas
        // si pasamos
        // como nombre: "' or '1'='1"
        Query query = session.createNativeQuery("SELECT * FROM prestamo where nombre = '" + nombre + "'", Prestamo.class);

        List<Prestamo> prestamo = query.getResultList();

        return prestamo;

    }

    public List<PrestamoPorCliente> mostrarReportePrestamoPorCliente(int clienteId){
        Session session = sessionFactory.openSession();
        //SQL
        Query queryReportePorCliente = session.createNativeQuery("SELECT c.cliente_id, c.nombre, count(*) cantidad, max(p.importe) maximo, sum(p.importe) total FROM cliente c inner join prestamo p on c.cliente_id = p.cliente_id WHERE c.cliente_id = ? group by c.cliente_id, c.nombre" , PrestamoPorCliente.class);
        queryReportePorCliente.setParameter(1, clienteId);
        List<PrestamoPorCliente> reportePrestamoPorClientes =queryReportePorCliente.getResultList();

        return reportePrestamoPorClientes;
    }

    public List<ReportePrestamo> mostrarReporteDePrestamo(){
        Session session = sessionFactory.openSession();
        Query queryReporteDePrestamo = session.createNativeQuery("SELECT count(*) cantidad, sum(p.importe) total FROM prestamo p", ReportePrestamo.class);
        List<ReportePrestamo> reporteDePrestamos = queryReporteDePrestamo.getResultList();
        return reporteDePrestamos;
    
    }
    public void exitPrestamo() {
        sessionFactory.close();
    }

}
    
