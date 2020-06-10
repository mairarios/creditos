package ar.com.ada.creditos.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "cancelacion")
public class Cancelacion {

@Id
@Column(name = "cancelacion_id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int cancelacionId;
@ManyToOne
@JoinColumn(name = "prestamo_id", referencedColumnName = "prestamo_id")
private Prestamo prestamo;

@Column(name= "fecha_cancelacion")
private Date fechaCancelacion;
private BigDecimal importe;
@Column(name= "cuota")
private int cuota;

public int getCancelacionId() {
    return cancelacionId;
}

public void setCancelacionId(int cancelacionId) {
    this.cancelacionId = cancelacionId;
}

public Date getFechaCancelacion() {
    return fechaCancelacion;
}

public void setFechaCancelacion(Date fechaCancelacion) {
    this.fechaCancelacion = fechaCancelacion;
}

public BigDecimal getImporte() {
    return importe;
}

public void setImporte(BigDecimal importe) {
    this.importe = importe;
}

public int getCuota() {
    return cuota;
}

public void setCuota(int cuota) {
    this.cuota = cuota;
}

public Prestamo getPrestamo() {
    return prestamo;
}

public void setPrestamo(Prestamo prestamo) {
    this.prestamo = prestamo;
}



    
}