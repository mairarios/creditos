package ar.com.ada.creditos.entities.reportes;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
public class ReportePrestamo {

@Id
private int cantidad;
private BigDecimal importeTotal;

public int getCantidad() {
    return cantidad;
}

public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
}

public BigDecimal getImporteTotal() {
    return importeTotal;
}

public void setImporteTotal(BigDecimal importeTotal) {
    this.importeTotal = importeTotal;
}

    
}