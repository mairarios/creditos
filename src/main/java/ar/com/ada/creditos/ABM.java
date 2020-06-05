package ar.com.ada.creditos;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.annotations.SourceType;
import org.hibernate.exception.ConstraintViolationException;

import ar.com.ada.creditos.entities.*;
import ar.com.ada.creditos.excepciones.*;
import ar.com.ada.creditos.managers.*;

public class ABM {

    public static Scanner Teclado = new Scanner(System.in);

    protected ClienteManager ABMCliente = new ClienteManager();
    protected PrestamoManager ABMPrestamo = new PrestamoManager();

    public void iniciar() throws Exception {

        try {

            ABMCliente.setup();
            ABMPrestamo.setup();

            printOpciones();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            if (opcion == 1) {
                printOpcionCliente();
                int opcionC = Teclado.nextInt();
                Teclado.nextLine();
                while (opcionC > 0) {

                    switch (opcionC) {
                        case 1:

                            try {
                                alta();
                            } catch (ClienteDNIException exdni) {
                                System.out.println("Error en el DNI. Indique uno valido");
                            }
                            break;

                        case 2:
                            baja();
                            break;

                        case 3:
                            modifica();
                            break;

                        case 4:
                            listar();
                            break;

                        case 5:
                            listarPorNombre();
                            break;

                        default:
                            System.out.println("La opcion no es correcta.");
                            break;
                    }

                    printOpcionCliente();

                    opcionC = Teclado.nextInt();
                    Teclado.nextLine();
                }
            } else {
                printOpcionPrestamo();
                int opcionP = Teclado.nextInt();
                Teclado.nextLine();
                while (opcionP > 0) {

                    switch (opcionP) {
                        case 1:
                            agregarPrestamo();
                            break;

                        case 2:
                            listarPrestamo();
                            break;

                        case 3:
                            modificaPrestamo();
                            break;

                        case 5:
                            listarPorNombre();
                            break;

                        default:
                            System.out.println("La opcion no es correcta.");
                            break;
                    }

                    printOpcionPrestamo();

                    opcionP = Teclado.nextInt();
                    Teclado.nextLine();
                }

            }

            // Hago un safe exit del manager
            ABMCliente.exit();
            ABMPrestamo.exit();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del sistema, bye bye...");

        }

    }

    public void alta() throws Exception {
        Cliente cliente = new Cliente();
        System.out.println("Ingrese el nombre:");
        cliente.setNombre(Teclado.nextLine());
        System.out.println("Ingrese el DNI:");
        cliente.setDni(Teclado.nextInt());
        Teclado.nextLine();
        System.out.println("Ingrese el domicilio:");
        cliente.setDomicilio(Teclado.nextLine());

        System.out.println("Ingrese el Domicilio alternativo(OPCIONAL):");

        String domAlternativo = Teclado.nextLine();

        if (domAlternativo != null)
            cliente.setDomicilioAlternativo(domAlternativo);

        ABMCliente.create(cliente);

        /*
         * Si concateno el OBJETO directamente, me trae todo lo que este en el metodo
         * toString() mi recomendacion es NO usarlo para imprimir cosas en pantallas, si
         * no para loguear info Lo mejor es usar:
         * System.out.println("Cliente generada con exito.  " + cliente.getClienteId);
         */

        System.out.println("Cliente generada con exito.  " + cliente);

    }

    public void baja() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el ID de Cliente:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.read(id);

        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado.");

        } else {

            try {

                ABMCliente.delete(clienteEncontrado);
                System.out
                        .println("El registro del cliente " + clienteEncontrado.getClienteId() + " ha sido eliminado.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error al eliminar una cliente. Error: " + e.getCause());
            }

        }
    }

    public void bajaPorDNI() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el DNI de Cliente:");
        String dni = Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.readByDNI(dni);

        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado.");

        } else {
            ABMCliente.delete(clienteEncontrado);
            System.out.println("El registro del DNI " + clienteEncontrado.getDni() + " ha sido eliminado.");
        }
    }

    public void modifica() throws Exception {
        // System.out.println("Ingrese el nombre de la cliente a modificar:");
        // String n = Teclado.nextLine();

        System.out.println("Ingrese el ID de la cliente a modificar:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.read(id);

        if (clienteEncontrado != null) {

            // RECOMENDACION NO USAR toString(), esto solo es a nivel educativo.
            System.out.println(clienteEncontrado.toString() + " seleccionado para modificacion.");

            System.out.println(
                    "Elija qué dato de la cliente desea modificar: \n1: nombre, \n2: DNI, \n3: domicilio, \n4: domicilio alternativo");
            int selecper = Teclado.nextInt();

            switch (selecper) {
                case 1:
                    System.out.println("Ingrese el nuevo nombre:");
                    Teclado.nextLine();
                    clienteEncontrado.setNombre(Teclado.nextLine());

                    break;
                case 2:
                    System.out.println("Ingrese el nuevo DNI:");
                    Teclado.nextLine();
                    clienteEncontrado.setDni(Teclado.nextInt());
                    Teclado.nextLine();

                    break;
                case 3:
                    System.out.println("Ingrese el nuevo domicilio:");
                    Teclado.nextLine();
                    clienteEncontrado.setDomicilio(Teclado.nextLine());

                    break;
                case 4:
                    System.out.println("Ingrese el nuevo domicilio alternativo:");
                    Teclado.nextLine();
                    clienteEncontrado.setDomicilioAlternativo(Teclado.nextLine());

                    break;

                default:
                    break;
            }

            // Teclado.nextLine();

            ABMCliente.update(clienteEncontrado);

            System.out.println("El registro de " + clienteEncontrado.getNombre() + " ha sido modificado.");

        } else {
            System.out.println("Cliente no encontrado.");
        }

    }

    public void listar() {

        List<Cliente> todos = ABMCliente.buscarTodos();
        for (Cliente c : todos) {
            mostrarCliente(c);
        }
    }

    public void listarPorNombre() {

        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();

        List<Cliente> clientes = ABMCliente.buscarPor(nombre);
        for (Cliente cliente : clientes) {
            mostrarCliente(cliente);
        }
    }

    public void mostrarCliente(Cliente cliente) {

        System.out.print("Id: " + cliente.getClienteId() + " Nombre: " + cliente.getNombre() + " DNI: "
                + cliente.getDni() + " Domicilio: " + cliente.getDomicilio());

        if (cliente.getDomicilioAlternativo() != null)
            System.out.println(" Alternativo: " + cliente.getDomicilioAlternativo());
        else
            System.out.println();
    }

    public void agregarPrestamo() {
        System.out.println("Ingrese el ID del cliente al que le quiere agregar un prestamo");
        int clienteId = Teclado.nextInt();
        Cliente c1 = ABMCliente.read(clienteId);
        if (c1 == null) {
            System.out.println("El cliente no se encuentra");
            return;
        }
        Prestamo prestamo = new Prestamo();
        System.out.println("Ingrese el monto: ");
        prestamo.setImporte(Teclado.nextBigDecimal());
        prestamo.setCliente(c1);
        System.out.println("Ingrese las cuotas: ");
        prestamo.setCuotas(Teclado.nextInt());
        Teclado.nextLine();
        System.out.println("Introduzca la fecha con formato dd/mm/yyyy");
        Date date = null;
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        try {
            date = df.parse(Teclado.nextLine());
            prestamo.setFecha(date);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        prestamo.setFechaAlta(new Date());
        ABMPrestamo.create(prestamo);

        System.out.println("Prestamo cargado con exito");

    }

    public void listarPrestamo() {

        List<Prestamo> todos = ABMPrestamo.buscarTodosLosPrestamos();
        for (Prestamo p : todos) {
            mostrarPrestamo(p);
        }

    }

    public void mostrarPrestamo(Prestamo prestamo) {

        System.out.println("Id Prestamo: " + prestamo.getPrestamoId() + " Cliente: " + prestamo.getCliente().getNombre()
                + " Importe: " + prestamo.getImporte() + " Cantidad de cuotas: " + prestamo.getCuotas()
                + " Fecha de alta: " + prestamo.getFechaAlta());

    }

    public void modificaPrestamo() throws Exception {

        System.out.println("Ingrese el ID del prestamo para modificarlo:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Prestamo prestamoEncontrado = ABMPrestamo.read(id);

        if (prestamoEncontrado != null) {

            // RECOMENDACION NO USAR toString(), esto solo es a nivel educativo.
            System.out.println(prestamoEncontrado.getPrestamoId() + " seleccionado para modificacion.");

            System.out.println("Elija qué dato del prestamo desea modificar: \n1: monto, \n2: cuota, \n3: fecha");
            int selecper = Teclado.nextInt();

            switch (selecper) {
                case 1:
                    System.out.println("Ingrese el nuevo monto:");
                    Teclado.nextBigDecimal();
                    prestamoEncontrado.setImporte(Teclado.nextBigDecimal());
                    Teclado.nextLine();

                    break;
                case 2:
                    System.out.println("Ingrese las cuotas:");
                    Teclado.nextLine();
                    prestamoEncontrado.setCuotas(Teclado.nextInt());
                    Teclado.nextLine();

                    break;
                case 3:
                    System.out.println("Ingrese la nueva fecha:");
                    Date date = null;
                    DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                    try {
                        date = df.parse(Teclado.nextLine());
                        prestamoEncontrado.setFecha(date);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }

                    break;

                default:
                    break;
            }

            // Teclado.nextLine();

            ABMPrestamo.update(prestamoEncontrado);

            System.out.println("El registro de " + prestamoEncontrado.getPrestamoId() + " ha sido modificado.");

        } else {
            System.out.println("Prestamo no encontrado.");
        }

    }

    public static void printOpciones() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Menú Clientes");
        System.out.println("2. Menu Prestamos");
        System.out.println("");
        System.out.println("=======================================");
    }

    public static void printOpcionCliente() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para agregar un cliente.");
        System.out.println("2. Para eliminar un cliente.");
        System.out.println("3. Para modificar un cliente.");
        System.out.println("4. Para ver el listado.");
        System.out.println("5. Buscar un cliente por nombre especifico(SQL Injection)).");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }

    public static void printOpcionPrestamo() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Agregar un prestamo a un cliente");
        System.out.println("2. Mostrar todos los prestamos");
        System.out.println("3. Modificar un prestamo");
        System.out.println("4. Eliminar un prestamo");
        System.out.println("0. Salir");
        System.out.println("");
        System.out.println("=======================================");

    }
}
