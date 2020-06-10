package ar.com.ada.creditos.services;

import java.util.List;

import ar.com.ada.creditos.entities.reportes.PrestamoPorCliente;
import ar.com.ada.creditos.entities.reportes.ReportePrestamo;
import ar.com.ada.creditos.managers.PrestamoManager;

public class ReporteService {
   
PrestamoManager rManager;

public ReporteService(PrestamoManager rManager){
this.rManager = rManager;
}
    public void mostrarReportePrestamoPorCliente(int cliente) {
        List<PrestamoPorCliente> reporte = rManager.mostrarReportePrestamoPorCliente(cliente);
        for (PrestamoPorCliente r : reporte) {
            System.out.println("El cliente "+ r.getNombre()+ " ID "+ r.getClienteId()+ " tiene "+ r.getCantidad()+ "de prestamos."
            + r.getMaximo()+ " es el importe mas alto." + r.getTotal()+ " es el total de prestamos. ");
        }

    
    }

    public void  mostrarReporteTotalDePrestamos(){
        List<ReportePrestamo> reporteToPr = rManager.mostrarReporteDePrestamo();
        for (ReportePrestamo total : reporteToPr){

            System.out.println("Cantidad de prestamos en total: "+ total.getCantidad() + "la suma total de prestamos es "+ total.getImporteTotal());

        }
    }
    
}