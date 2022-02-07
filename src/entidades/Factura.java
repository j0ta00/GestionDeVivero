package entidades;

import java.time.LocalDate;

public class Factura{
private int id,dniCliente,Usuario;
private LocalDate fecha;
private double importe;

    public Factura(int id, int dniCliente, int usuario, LocalDate fecha, double importe) {
        this.id = id;
        this.dniCliente = dniCliente;
        Usuario = usuario;
        this.fecha = fecha;
        this.importe = importe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }

    public int getUsuario() {
        return Usuario;
    }

    public void setUsuario(int usuario) {
        Usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
