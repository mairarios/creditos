package ar.com.ada.creditos.entities.reportes;

import java.math.BigDecimal;

import javax.persistence.*;


@Entity
public class PrestamoPorCliente {


@Id
@Column(name = "cliente_id")
private int clienteId;
private String nombre;
private BigDecimal maximo;
private BigDecimal total;
private int cantidad;

public int getClienteId() {
    return clienteId;
}

public void setClienteId(int clienteId) {
    this.clienteId = clienteId;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public BigDecimal getMaximo() {
    return maximo;
}

public void setMaximo(BigDecimal maximo) {
    this.maximo = maximo;
}

public BigDecimal getTotal() {
    return total;
}

public void setTotal(BigDecimal total) {
    this.total = total;
}

public int getCantidad() {
    return cantidad;
}

public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
}


}